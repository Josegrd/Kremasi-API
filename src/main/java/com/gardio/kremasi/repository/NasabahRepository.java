package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.Nasabah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NasabahRepository extends JpaRepository<Nasabah, String> {
    Nasabah getNasabahByUserCredential_Username(String username);
    Optional<Nasabah> findByEmail(String email);
    Optional<Nasabah> findByNik(String nik);
    Optional<Nasabah> findByPhoneNumber(String phoneNumber);
    Nasabah getNasabahByUserCredential_Id(String userCredentialId);
    Optional<Nasabah> findIdByUserCredential_Id(String username);
}