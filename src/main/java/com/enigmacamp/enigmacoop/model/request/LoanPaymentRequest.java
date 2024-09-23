package com.enigmacamp.enigmacoop.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanPaymentRequest {
    private String loanId;
}
