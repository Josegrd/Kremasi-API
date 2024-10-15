package com.gardio.kremasi.repository;

import com.gardio.kremasi.entity.LoanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanDetailRepository extends JpaRepository<LoanDetail, String> {

    List<LoanDetail> findAllByLoanId(String loanId);
}
