package com.gardio.kremasi.model.request;

import com.gardio.kremasi.constant.EInstallmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstallmentTypeRequest {
    private String id;
    private EInstallmentType installmentType;
}
