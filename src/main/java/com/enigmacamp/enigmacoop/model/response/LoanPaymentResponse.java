package com.enigmacamp.enigmacoop.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanPaymentResponse {
    private String id;
    private String loanId;
    private String nasabahId;
    private Long amount;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date date;
    private PaymentResponse paymentResponse;
}
