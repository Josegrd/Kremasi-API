package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, String> {

    Optional<LoanType> findById(String id);
}
