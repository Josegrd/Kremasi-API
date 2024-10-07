package com.gardio.kremasi.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanPaymentRequest {
    private String loanId;
}
