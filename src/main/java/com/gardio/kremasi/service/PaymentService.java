package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.LoanPayment;
import com.gardio.kremasi.entity.Payment;
import com.gardio.kremasi.model.request.PaymentRequestTest;

public interface PaymentService {
    Payment create(PaymentRequestTest loanPayment);
}
