package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.InstallmentType;
import com.gardio.kremasi.model.request.InstallmentTypeRequest;
import com.gardio.kremasi.model.response.InstallmentTypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstallmentTypeService {
    InstallmentTypeResponse createInstallmentType(InstallmentTypeRequest request);
    InstallmentTypeResponse getInstallmentTypeById(String id);
    List<InstallmentTypeResponse> getAllInstallmentType();
    InstallmentTypeResponse updateInstallmentType(InstallmentTypeRequest request);
    void deleteInstallmentType(String id);
    InstallmentType getById(String id);
    Page<InstallmentType> getAllInstallmentType(int page, int size);
    InstallmentType findByIdOrThrowNotFoundException(String id);

}
