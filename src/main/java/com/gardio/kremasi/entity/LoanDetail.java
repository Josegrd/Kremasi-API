package com.gardio.kremasi.entity;


import com.gardio.kremasi.constant.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_detail")
public class LoanDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Date transactionDate;

    private Long nominal;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "loan_id")
    private Loan loan;
    private LoanStatus loanStatus;
}
