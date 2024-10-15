package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.entity.Loan;
import com.gardio.kremasi.entity.LoanPayment;
import com.gardio.kremasi.entity.Payment;
import com.gardio.kremasi.model.request.LoanPaymentRequest;
import com.gardio.kremasi.model.response.LoanPaymentResponse;
import com.gardio.kremasi.model.response.PaymentResponse;
import com.gardio.kremasi.repository.LoanPaymentRepository;
import com.gardio.kremasi.service.LoanPaymentService;
import com.gardio.kremasi.service.LoanService;
import com.gardio.kremasi.service.PaymentService;
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
//        Payment payment = paymentService.create(loanPayment);
//        savedLoanPayment.setPayment(payment);
//        PaymentResponse paymentResponse = PaymentResponse.builder()
//                .id(payment.getId())
//                .token(payment.getToken())
//                .redirectUrl(payment.getRedirectUrl())
//                .transactionStatus(payment.getTransactionStatus())
//                .build();
//        return LoanPaymentResponse.builder()
//                .id(savedLoanPayment.getId())
//                .loanId(findloan.getId())
//                .nasabahId(findloan.getNasabah().getId())
//                .amount(amount)
//                .date(savedLoanPayment.getDate())
//                .paymentResponse(paymentResponse)
//                .build();
        return null;
    }

    @Override
    public List<LoanPaymentResponse> getAll() {
        return null;
    }
}
