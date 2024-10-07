package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.LoanPayment;
import com.gardio.kremasi.entity.Payment;

public interface PaymentService {
    Payment create(LoanPayment loanPayment);
}
