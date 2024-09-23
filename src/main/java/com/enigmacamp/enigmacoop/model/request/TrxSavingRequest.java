package com.enigmacamp.enigmacoop.model.request;

import com.enigmacamp.enigmacoop.constant.SavingType;
import jakarta.persistence.Entity;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrxSavingRequest {
    private Long amount;
    private String savingId;
    private SavingType savingType;
    private Date date;
}
