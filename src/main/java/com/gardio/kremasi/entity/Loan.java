package com.gardio.kremasi.entity;

import com.gardio.kremasi.constant.LoanStatus;
import com.gardio.kremasi.constant.PathDb;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = PathDb.LOAN)
public class Loan extends AuditEntity{

    private Long amount;
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nasabah_id")
    private Nasabah nasabah;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LoanDetail> loanDetails = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false,updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date dueDate;
    @PrePersist
    protected void onCreate(){
        startDate = new java.util.Date();
    }

    private Integer installmentCount;

    @ManyToOne
    private LoanType loanType;
}
