package com.gardio.kremasi.repository;

import com.gardio.kremasi.constant.EInstallmentType;
import com.gardio.kremasi.entity.InstallmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstallmentTypeRepository extends JpaRepository<InstallmentType, String> {
    Optional<InstallmentType> findByInstallmentType(EInstallmentType installmentType);
}
