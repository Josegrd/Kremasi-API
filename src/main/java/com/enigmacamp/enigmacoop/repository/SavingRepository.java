package com.enigmacamp.enigmacoop.repository;

import com.enigmacamp.enigmacoop.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<Saving,String> {
    Saving getSavingByNasabahId(String id);
}
