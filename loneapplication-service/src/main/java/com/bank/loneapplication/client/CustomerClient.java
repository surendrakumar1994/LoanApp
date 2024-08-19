package com.bank.loneapplication.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.loneapplication.dto.CustomerDTO2;



//@HttpExchange
@FeignClient(name="CUSTOMER-SERVICE")
public interface CustomerClient {
	
	    //@GetExchange("loneapp/customer/{nationalIdentityNumber}")
	    //public CustomerDTO findByCustomer(@PathVariable("nationalIdentityNumber") String nationalIdentityNumber);


	//@GetMapping("/customer/get/{nationalIdentityNumber}")
    //public Optional<CustomerDTO> findByCustomer(@PathVariable("nationalIdentityNumber") String nationalIdentityNumber);

	 @GetMapping("/customer/get/{nationalIdentityNumber}")
	    public CustomerDTO2 getCustomerByNationalIdentityNumber(@PathVariable("nationalIdentityNumber") String nationalIdentityNumber);
	     
}




//public LoanApplication(Long id,Customer customer, Loan loan,Notification notification,Loan loan) 



