package com.gardio.kremasi.controller;

import com.gardio.kremasi.constant.PathApi;
import com.gardio.kremasi.model.request.LoanPaymentRequest;
import com.gardio.kremasi.model.response.LoanPaymentResponse;
import com.gardio.kremasi.model.response.WebResponse;
import com.gardio.kremasi.service.LoanPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(PathApi.BasePath.LOAN_PAYMENT)
@RestController
@RequiredArgsConstructor
public class LoanPaymentController {
    private final LoanPaymentService loanPaymentService;
    @PostMapping
    public ResponseEntity<WebResponse<LoanPaymentResponse>> createSaving(
            @RequestBody LoanPaymentRequest loanPaymentRequest){
        LoanPaymentResponse paymentResponse = loanPaymentService.create(loanPaymentRequest);
        WebResponse<LoanPaymentResponse> response = WebResponse.<LoanPaymentResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success create payment")
                .data(paymentResponse)
                .build();
        return ResponseEntity.ok(response);
    }

}
