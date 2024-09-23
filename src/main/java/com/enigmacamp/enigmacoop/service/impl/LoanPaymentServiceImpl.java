package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.entity.Loan;
import com.enigmacamp.enigmacoop.entity.LoanPayment;
import com.enigmacamp.enigmacoop.entity.Payment;
import com.enigmacamp.enigmacoop.model.request.LoanPaymentRequest;
import com.enigmacamp.enigmacoop.model.response.LoanPaymentResponse;
import com.enigmacamp.enigmacoop.model.response.PaymentResponse;
import com.enigmacamp.enigmacoop.repository.LoanPaymentRepository;
import com.enigmacamp.enigmacoop.service.LoanPaymentService;
import com.enigmacamp.enigmacoop.service.LoanService;
import com.enigmacamp.enigmacoop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanPaymentServiceImpl implements LoanPaymentService {
    private final LoanPaymentRepository loanPaymentRepository;
    private final PaymentService paymentService;
    private final LoanService loanService;

    @Override
    public LoanPaymentResponse create(LoanPaymentRequest loanPaymentRequest) {
        Loan findloan = loanService.getById(loanPaymentRequest.getLoanId());
        Long amount = (long) (findloan.getAmount() + (findloan.getAmount() * findloan.getInterestRate()));
        LoanPayment loanPayment = LoanPayment.builder()
                .amount(amount)
                .loan(findloan)
                .build();
        LoanPayment savedLoanPayment = loanPaymentRepository.saveAndFlush(loanPayment);
        Payment payment = paymentService.create(loanPayment);
        savedLoanPayment.setPayment(payment);
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(payment.getId())
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUrl())
                .transactionStatus(payment.getTransactionStatus())
                .build();
        return LoanPaymentResponse.builder()
                .id(savedLoanPayment.getId())
                .loanId(findloan.getId())
                .nasabahId(findloan.getNasabah().getId())
                .amount(amount)
                .date(savedLoanPayment.getDate())
                .paymentResponse(paymentResponse)
                .build();
    }

    @Override
    public List<LoanPaymentResponse> getAll() {
        return null;
    }
}
