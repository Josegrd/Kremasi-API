package com.enigmacamp.enigmacoop.model.request;

import com.enigmacamp.enigmacoop.constant.LoanStatus;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {
    private String nasabahId;
    private Long amount;
}
