package com.enigmacamp.enigmacoop.entity;

import com.enigmacamp.enigmacoop.constant.PathDb;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = PathDb.PAYMENT)
@Builder
public class Payment extends AuditEntity{

    @Column(name = "token")
    private String token;
    @Column(name = "redirect_url")
    private String redirectUrl;
    @Column(name = "transaction_status")
    private String transactionStatus;
    @OneToOne(mappedBy = "payment")
    private LoanPayment loanPayment;
}
