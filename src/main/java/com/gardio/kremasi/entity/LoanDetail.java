package com.gardio.kremasi.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gardio.kremasi.constant.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    @JsonManagedReference
    private Loan loan;

    private LocalDate dueDate;
    private double amount;
    private boolean paid;

    @OneToMany(mappedBy = "loanDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionDetail> transactionDetails;
}
