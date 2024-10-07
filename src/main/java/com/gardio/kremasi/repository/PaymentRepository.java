package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,String> {
}
