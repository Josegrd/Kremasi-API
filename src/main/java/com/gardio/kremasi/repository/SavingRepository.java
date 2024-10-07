package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<Saving,String> {
    Saving getSavingByNasabahId(String id);
}
