package com.gardio.kremasi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestTest {
    private String id;
    private Long amount;
    private String loanDetailId;
}
