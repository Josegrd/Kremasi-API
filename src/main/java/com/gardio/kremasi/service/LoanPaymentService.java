package com.gardio.kremasi.service;

import com.gardio.kremasi.model.request.LoanPaymentRequest;
import com.gardio.kremasi.model.response.LoanPaymentResponse;

import java.util.List;

public interface LoanPaymentService {
    LoanPaymentResponse create(LoanPaymentRequest loanPaymentRequest);
    List<LoanPaymentResponse> getAll();
}
