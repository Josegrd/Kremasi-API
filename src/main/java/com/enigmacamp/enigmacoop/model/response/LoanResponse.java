package com.enigmacamp.enigmacoop.model.response;

import com.enigmacamp.enigmacoop.constant.LoanStatus;
import com.enigmacamp.enigmacoop.entity.Loan;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date dueDate;
    private Long totalPayment;
}
