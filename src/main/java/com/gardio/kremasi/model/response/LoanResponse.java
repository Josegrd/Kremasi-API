package com.gardio.kremasi.model.response;

import com.gardio.kremasi.constant.LoanStatus;
import com.gardio.kremasi.entity.LoanDetail;
import com.gardio.kremasi.entity.LoanType;
import com.gardio.kremasi.entity.Nasabah;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponse{
    private String id;
    private Long amount;
    private Double interestRate;
    private LoanStatus status;
    private Nasabah nasabah;
    private List<LoanDetail> loanDetails;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date dueDate;
    private Long totalPayment;
    private Integer installmentCount;
    private LoanType loanType;
}
