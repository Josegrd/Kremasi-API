package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.entity.LoanPayment;
import com.enigmacamp.enigmacoop.entity.Payment;

public interface PaymentService {
    Payment create(LoanPayment loanPayment);
}
