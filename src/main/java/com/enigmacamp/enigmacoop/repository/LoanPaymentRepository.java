package com.enigmacamp.enigmacoop.repository;

import com.enigmacamp.enigmacoop.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment,String> {
}
