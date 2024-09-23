package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.model.request.LoanPaymentRequest;
import com.enigmacamp.enigmacoop.model.response.LoanPaymentResponse;

import java.util.List;

public interface LoanPaymentService {
    LoanPaymentResponse create(LoanPaymentRequest loanPaymentRequest);
    List<LoanPaymentResponse> getAll();
}
