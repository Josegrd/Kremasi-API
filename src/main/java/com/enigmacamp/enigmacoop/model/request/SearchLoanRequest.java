package com.enigmacamp.enigmacoop.model.request;

import com.enigmacamp.enigmacoop.constant.LoanStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchLoanRequest {
    private Integer page;
    private Integer size;
    private Long minAmount;
    private Long maxAmount;
}
