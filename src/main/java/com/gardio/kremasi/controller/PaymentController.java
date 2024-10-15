package com.gardio.kremasi.controller;

import com.gardio.kremasi.entity.LoanPayment;
import com.gardio.kremasi.entity.Payment;
import com.gardio.kremasi.model.request.PaymentRequestTest;
import com.gardio.kremasi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/simulate")
    public ResponseEntity<Payment> simulatePayment(@RequestBody PaymentRequestTest loanPayment){
        log.info("Simulate Payment", loanPayment);
        return ResponseEntity.ok(paymentService.create(loanPayment));
    }
}
