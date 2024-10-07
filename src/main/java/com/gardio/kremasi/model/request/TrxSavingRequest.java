package com.gardio.kremasi.model.request;

import com.gardio.kremasi.constant.SavingType;
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
