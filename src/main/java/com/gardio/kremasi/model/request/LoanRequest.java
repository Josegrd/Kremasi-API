package com.gardio.kremasi.model.request;

import com.gardio.kremasi.entity.LoanDetail;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {
    private String nasabahId;
    private String loanTypeId;
    private String installmentTypeId;
    private Long amount;
    private List<LoanDetail> loanDetails = new ArrayList<>();
}
