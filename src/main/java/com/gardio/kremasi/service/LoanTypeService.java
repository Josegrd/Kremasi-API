package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.LoanType;
import com.gardio.kremasi.model.request.LoanTypeRequest;
import com.gardio.kremasi.model.response.LoanTypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanTypeService {
    LoanTypeResponse createLoanType(LoanTypeRequest request);
    LoanTypeResponse getLoanById(String id);
    List<LoanTypeResponse> getAll();
    LoanTypeResponse updateLoanType(LoanTypeRequest request);
    void deleteLoanType(String id);
    LoanType getById(String id);
    Page<LoanType> getAllLoanType(int page, int size);
    LoanType findByIdOrThrowNotFoundException(String id);
}
