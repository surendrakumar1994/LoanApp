package com.bank.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bank.customer.client.CreateLoanApplication;
import com.bank.customer.dto.CustomerDto;
import com.bank.customer.dto.LoanApplicationDto;
import com.bank.customer.ecxeption.CustomerNotFoundException;
import com.bank.customer.entity.Customer;
import com.bank.customer.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
    private CustomerRepository customerRepository;

	@Autowired
	private CreateLoanApplication createLoanApplication;
	
   public void addCustomer(Customer customer) {
	   
	  // LoanApplicationDto l= loanApplicationClient.findByNationalIdentityNumber(customer.getNationalIdentityNumber());
	   customerRepository.save(customer);
	   LoanApplicationDto lo=new LoanApplicationDto();
   	   lo.setNationalIdentityNumber(customer.getNationalIdentityNumber());
   	
   	   createLoanApplication.createLoanApplication(lo);
	   System.out.println("--------------------------:");
   }
   
   public List<Customer> getAllCustomers(){
	   return customerRepository.findAll();
   }
   private Optional<Customer> findCustomerByNationalIdentityNumber(String nationalIdentityNumber) {
       return Optional.ofNullable(customerRepository.findByNationalIdentityNumber(nationalIdentityNumber).orElseThrow(() ->
               new CustomerNotFoundException("Related customer with National Identity Number: " + nationalIdentityNumber + " not found")));
   }
   public CustomerDto getCustomerByNationalIdentityNumber(String nationalIdentityNumber) {
	   Customer c=findCustomerByNationalIdentityNumber(nationalIdentityNumber).get();
	   CustomerDto d=new CustomerDto();
	   
	   d.setNationalIdentityNumber(c.getNationalIdentityNumber());
	   d.setFirstName(c.getFirstName());
	   d.setLastName(c.getLastName());
	   d.setMonthlyIncome(c.getMonthlyIncome());
	   d.setGender(c.getGender());
	   d.setGender(c.getGender());
	   d.setAge(c.getAge());
	   d.setPhone(c.getPhone());
	   d.setEmail(c.getEmail());
	   d.setLoanScore(c.getLoanScore());
       return d;

   }
}
