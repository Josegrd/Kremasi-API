package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.entity.LoanType;
import com.gardio.kremasi.model.request.LoanTypeRequest;
import com.gardio.kremasi.model.response.LoanTypeResponse;
import com.gardio.kremasi.repository.LoanTypeRepository;
import com.gardio.kremasi.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;

    @Override
    public LoanTypeResponse createLoanType(LoanTypeRequest request) {
        try{
            LoanType loanType = LoanType.builder()
                    .type(request.getType())
                    .build();
            loanTypeRepository.save(loanType);
            return convertToResponse(loanType);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public LoanTypeResponse getLoanById(String id) {
        return convertToResponse(findByIdOrThrowNotFoundException(id));
    }

    @Override
    public List<LoanTypeResponse> getAll() {
        return loanTypeRepository.findAll().stream()
                .filter(loanType -> loanType instanceof LoanType)  // Memastikan bahwa objek benar-benar LoanType
                .map(this::convertToResponse)
                .toList();
    }

    public Page<LoanType> getAllLoanType(int page, int size) {
        if(page <= 0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        return loanTypeRepository.findAll(pageable);
    }

    @Override
    public LoanTypeResponse updateLoanType(LoanTypeRequest request) {
        try{
            LoanType loanType = findByIdOrThrowNotFoundException(request.getId());
            loanType.setType(request.getType());
            loanTypeRepository.save(loanType);
            return convertToResponse(loanType);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void deleteLoanType(String id) {
        loanTypeRepository.deleteById(id);
    }

    @Override
    public LoanType getById(String id) {
        return loanTypeRepository.findById(id).orElse(null);
    }


    public LoanType findByIdOrThrowNotFoundException(String id) {
        return loanTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan type not found"));
    }

    private LoanTypeResponse convertToResponse(LoanType loanType) {
        return LoanTypeResponse.builder()
                .id(loanType.getId())
                .type(loanType.getType())
                .build();
    }
}
