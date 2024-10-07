package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.Nasabah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NasabahRepository extends JpaRepository<Nasabah, String> {
    Nasabah getNasabahByUserCredential_Username(String username);
}