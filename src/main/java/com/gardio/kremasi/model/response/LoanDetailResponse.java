package com.gardio.kremasi.model.response;

import com.gardio.kremasi.constant.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDetailResponse {

    private String id;
    private Date transactionDate;
    private Long nominal;
    private LoanStatus loanStatus;
}
