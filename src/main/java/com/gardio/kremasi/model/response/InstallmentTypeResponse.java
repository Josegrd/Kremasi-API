package com.gardio.kremasi.model.response;

import com.gardio.kremasi.constant.EInstallmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstallmentTypeResponse {
    private String id;
    private EInstallmentType installmentType;
}
