package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.entity.LoanDetail;
import com.gardio.kremasi.entity.LoanPayment;
import com.gardio.kremasi.entity.Payment;
import com.gardio.kremasi.entity.TransactionDetail;
import com.gardio.kremasi.model.request.PaymentDetailRequest;
import com.gardio.kremasi.model.request.PaymentRequest;
import com.gardio.kremasi.model.request.PaymentRequestTest;
import com.gardio.kremasi.repository.LoanDetailRepository;
import com.gardio.kremasi.repository.PaymentRepository;
import com.gardio.kremasi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestClient restClient;
    private final LoanDetailRepository loanDetailRepository;

    @Value("${midtrans.api.key}")
    private String SECRET_KEY;

    @Value("${midtrans.api.snap-url}")
    private String BASE_SNAP_URL;

    @Override
    public Payment create(PaymentRequestTest loanPayment) {
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
        payment = paymentRepository.saveAndFlush(payment);

        LoanDetail loanDetail = loanDetailRepository.findById(loanPayment.getLoanDetailId()).orElseThrow(() -> new IllegalArgumentException("Loan Detail not found"));
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .loanDetail(loanDetail)
                .amount(loanPayment.getAmount())
                .paymentDate(LocalDate.now())
                .build();
        loanDetail.getTransactionDetails().add(transactionDetail);

        loanDetail.setPaid(true);
        loanDetailRepository.saveAndFlush(loanDetail);
        return payment;
    }

}
