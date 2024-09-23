package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.entity.Loan;
import com.enigmacamp.enigmacoop.model.request.LoanRequest;
import com.enigmacamp.enigmacoop.model.request.SearchLoanRequest;
import com.enigmacamp.enigmacoop.model.response.LoanResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {
    LoanResponse createLoan(LoanRequest loanRequest);
    List<Loan> getLoansByNasabahId(String id);
    Page<Loan> getAll(SearchLoanRequest searchLoanRequest);
    Loan getById(String id);
    Loan approveLoanById(String id);
}
