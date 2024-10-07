package com.gardio.kremasi.model.request;

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
