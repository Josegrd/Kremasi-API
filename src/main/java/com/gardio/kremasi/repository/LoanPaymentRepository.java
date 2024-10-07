package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment,String> {
}
