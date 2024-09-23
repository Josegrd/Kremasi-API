package com.enigmacamp.enigmacoop.controller;

import com.enigmacamp.enigmacoop.constant.PathApi;
import com.enigmacamp.enigmacoop.entity.Saving;
import com.enigmacamp.enigmacoop.model.request.LoanPaymentRequest;
import com.enigmacamp.enigmacoop.model.response.LoanPaymentResponse;
import com.enigmacamp.enigmacoop.model.response.WebResponse;
import com.enigmacamp.enigmacoop.service.LoanPaymentService;
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
