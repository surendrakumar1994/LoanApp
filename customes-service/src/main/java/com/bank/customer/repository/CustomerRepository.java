package com.bank.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{
    Optional<Customer> findByNationalIdentityNumber(String nationalIdentityNumber);



}
