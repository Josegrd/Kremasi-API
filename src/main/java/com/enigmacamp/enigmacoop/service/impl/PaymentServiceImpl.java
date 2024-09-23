package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.entity.LoanPayment;
import com.enigmacamp.enigmacoop.entity.Payment;
import com.enigmacamp.enigmacoop.model.request.PaymentDetailRequest;
import com.enigmacamp.enigmacoop.model.request.PaymentRequest;
import com.enigmacamp.enigmacoop.repository.PaymentRepository;
import com.enigmacamp.enigmacoop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestClient restClient;

    @Value("${midtrans.api.key}")
    private String SECRET_KEY;

    @Value("${midtrans.api.snap-url}")
    private String BASE_SNAP_URL;

    @Override
    public Payment create(LoanPayment loanPayment) {
        PaymentDetailRequest paymentDetailRequest = PaymentDetailRequest.builder()
                .orderId(loanPayment.getId())
                .amount(loanPayment.getAmount())
                .build();
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentDetailRequest(paymentDetailRequest)
                .paymentMethod(List.of(
                        "shopeepay",
                        "gopay",
                        "indomaret"
                ))
                .build();
        ResponseEntity<Map<String,String>> response = restClient.post()
                .uri(BASE_SNAP_URL)
                .body(paymentRequest)
                .header(HttpHeaders.AUTHORIZATION,"Basic " + SECRET_KEY)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Map<String,String> body = response.getBody();
        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .transactionStatus("ordered")
                .build();
        return paymentRepository.saveAndFlush(payment);
    }
}
