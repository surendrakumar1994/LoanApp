package com.bank.loneapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.loneapplication.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {


}
