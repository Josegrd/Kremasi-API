package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.constant.LoanStatus;
import com.enigmacamp.enigmacoop.entity.Loan;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import com.enigmacamp.enigmacoop.entity.Saving;
import com.enigmacamp.enigmacoop.model.request.LoanRequest;
import com.enigmacamp.enigmacoop.model.request.SearchLoanRequest;
import com.enigmacamp.enigmacoop.model.response.LoanResponse;
import com.enigmacamp.enigmacoop.repository.LoanRepository;
import com.enigmacamp.enigmacoop.repository.SavingRepository;
import com.enigmacamp.enigmacoop.service.LoanService;
import com.enigmacamp.enigmacoop.service.NasabahService;
import com.enigmacamp.enigmacoop.service.SavingService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final NasabahService nasabahService;
    private final SavingService savingService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanResponse createLoan(LoanRequest loanRequest) {
        Nasabah findNasabah = nasabahService.getById(loanRequest.getNasabahId());
        Calendar calendar = getCalendarLoanCreate();
        Loan newLoan = Loan.builder()
                .amount(loanRequest.getAmount())
                .dueDate(calendar.getTime())
                .interestRate(0.05)
                .status(LoanStatus.PENDING)
                .nasabah(findNasabah)
                .build();
        Loan savedLoad = loanRepository.saveAndFlush(newLoan);
        Double totalRepaymentAmount = loanRequest.getAmount() + (loanRequest.getAmount() * newLoan.getInterestRate());
        return LoanResponse.builder()
                .id(savedLoad.getId())
                .amount(savedLoad.getAmount())
                .dueDate(savedLoad.getDueDate())
                .startDate(savedLoad.getStartDate())
                .interestRate(savedLoad.getInterestRate())
                .nasabah(savedLoad.getNasabah())
                .status(savedLoad.getStatus())
                .totalPayment(totalRepaymentAmount.longValue())
                .build();
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

}
