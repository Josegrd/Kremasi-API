package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,String>, JpaSpecificationExecutor<Loan> {
    List<Loan> findByNasabahId(String nasabahId); // load list loan berdasarkan id nasabah, nasabah one to many loan
    List<Loan> findAllByNasabahId(String nasabahId); // load list loan berdasarkan id nasabah, nasabah one to many loan
}
