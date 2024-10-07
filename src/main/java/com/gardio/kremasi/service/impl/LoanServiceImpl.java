package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.constant.LoanStatus;
import com.gardio.kremasi.entity.*;
import com.gardio.kremasi.model.request.LoanRequest;
import com.gardio.kremasi.model.request.SearchLoanRequest;
import com.gardio.kremasi.model.response.LoanResponse;
import com.gardio.kremasi.repository.LoanRepository;
import com.gardio.kremasi.service.*;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final NasabahService nasabahService;
    private final SavingService savingService;
    private final LoanTypeService loanTypeService;
    private final InstallmentTypeService installmentTypeService;
    int installmentCount = 0;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanResponse createLoan(LoanRequest loanRequest) {
        Nasabah findNasabah = nasabahService.getById(loanRequest.getNasabahId());
        Calendar calendar = getCalendarLoanCreate();
        InstallmentType installmentType = installmentTypeService.findByIdOrThrowNotFoundException(loanRequest.getInstallmentTypeId());
        LoanType loanType = loanTypeService.findByIdOrThrowNotFoundException(loanRequest.getLoanTypeId());

        switch (installmentType.getInstallmentType()) {
            case TWELVE_MONTH:
                installmentCount = 12;
                break;
            case SIX_MONTH:
                installmentCount = 6;
                break;
            case THREE_MONTH:
                installmentCount = 3;
                break;
            case ONE_MONTH:
                installmentCount = 1;
                break;
            case NINE_MONTH:
                installmentCount = 9;
                break;
            default:
                installmentCount = 0;
                break;
        }

        Loan newLoan = Loan.builder()
                .amount(loanRequest.getAmount())
                .loanType(loanType)
                .installmentCount(installmentCount)
                .dueDate(calendar.getTime())
                .interestRate(0.05)
                .status(LoanStatus.PENDING)
                .nasabah(findNasabah)
                .loanDetails(new ArrayList<>())
                .installmentCount(installmentCount)
                .build();
        Loan savedLoad = loanRepository.saveAndFlush(newLoan);


//        List<LoanDetail> loanDetails = loanRequest.getLoanDetails().stream()
//                .map(loanDetailRequest -> LoanDetail.builder()
//                        .nominal(loanDetailRequest.getNominal())
//                        .transactionDate(loanDetailRequest.getTransactionDate())
//                        .loan(savedLoad)
//                        .build())
//                .toList();
//        savedLoad.setLoanDetails(loanDetails);
//        loanRepository.saveAndFlush(savedLoad);

        return LoanResponse.builder()
                .id(savedLoad.getId())
                .amount(savedLoad.getAmount())
                .installmentCount(savedLoad.getInstallmentCount())
                .loanType(savedLoad.getLoanType())
                .dueDate(savedLoad.getDueDate())
                .startDate(savedLoad.getStartDate())
                .interestRate(savedLoad.getInterestRate())
                .nasabah(savedLoad.getNasabah())
                .status(savedLoad.getStatus())
                .loanDetails(savedLoad.getLoanDetails())
                .build();



//        Double totalRepaymentAmount = loanRequest.getAmount() + (loanRequest.getAmount() * newLoan.getInterestRate());
//        return LoanResponse.builder()
//                .id(savedLoad.getId())
//                .amount(savedLoad.getAmount())
//                .dueDate(savedLoad.getDueDate())
//                .startDate(savedLoad.getStartDate())
//                .interestRate(savedLoad.getInterestRate())
//                .nasabah(savedLoad.getNasabah())
//                .status(savedLoad.getStatus())
//                .totalPayment(totalRepaymentAmount.longValue())
//                .build();
    }

    private static Calendar getCalendarLoanCreate() {
        // 1 bulan dari request peminjaman
        Calendar calendar = Calendar.getInstance();
        int daysToNextMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
        // due date / jatuh tempo akan selalu pada tanggal 5 setelah hitungan 1 bulan tepatnya di jam 23.00
        // Jika jarak ke tanggal 5 hanya 15 hari atau kurang, maka akan tambah menuju bulan depannya
        // untuk bunga akan selalu 5% dari semua pinjaman
        if (daysToNextMonth <= 15) {
            calendar.add(Calendar.MONTH,2);
        }else{
            calendar.add(Calendar.MONTH,1);
        }
        calendar.set(Calendar.DAY_OF_MONTH,5);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar;
    }

    @Override
    public List<Loan> getLoansByNasabahId(String id) {
        return loanRepository.findByNasabahId(id);
    }

    @Override
    public Page<Loan> getAll(SearchLoanRequest searchLoanRequest) {
        if (searchLoanRequest.getPage() <= 0){
            searchLoanRequest.setPage(1);
        }
        Specification<Loan> loanSpecification = amountBetweenSepecification(
                searchLoanRequest.getMinAmount(),
                searchLoanRequest.getMaxAmount());
        Pageable pageable = PageRequest.of(searchLoanRequest.getPage()-1,searchLoanRequest.getSize());
        return loanRepository.findAll(loanSpecification,pageable);

    }

    private Specification<Loan> amountBetweenSepecification(Long minAmount, Long maxAmount){
        return (root, query, criteriaBuilder) -> {
            Predicate minAmountPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("amount"),minAmount);
            Predicate maxAmountPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("amount"),maxAmount);
            return criteriaBuilder.and(minAmountPredicate,maxAmountPredicate);
        };
    }

    @Override
    public Loan getById(String id) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if (optionalLoan.isPresent()) return  optionalLoan.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan Not Found");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Loan approveLoanById(String id) {
        Loan findLoan = this.getById(id);
        if (findLoan.getStatus() != LoanStatus.PENDING){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status Not Valid to Approved");
        }
        findLoan.setStatus(LoanStatus.APPROVED);
        loanRepository.save(findLoan);
        Saving findSaving = savingService.getSavingByNasabahId(findLoan.getNasabah().getId());
        findSaving.setBalance(findSaving.getBalance() + findLoan.getAmount());
        savingService.createSaving(findSaving);
        return findLoan;
    }

    private Loan convertToLoan(LoanRequest loanRequest){
        return Loan.builder()
                .amount(loanRequest.getAmount())
                .interestRate(0.05)
                .status(LoanStatus.PENDING)
                .build();
    }
}
