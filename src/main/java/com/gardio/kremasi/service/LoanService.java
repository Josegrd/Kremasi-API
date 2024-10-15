package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.Loan;
import com.gardio.kremasi.model.request.LoanRequest;
import com.gardio.kremasi.model.request.SearchLoanRequest;
import com.gardio.kremasi.model.response.LoanResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {
    LoanResponse createLoan(LoanRequest loanRequest);
    List<Loan> getLoansByNasabahId(String id);
    Page<Loan> getAll(SearchLoanRequest searchLoanRequest);
    Loan getById(String id);
    Loan approveLoanById(String id);
    List<LoanResponse> getAllLoans();
    LoanResponse getLoanById(String id);
    List<LoanResponse> getLoansByNasabah(String id);
    LoanResponse approveLoanByIdLoan(String id);
}
