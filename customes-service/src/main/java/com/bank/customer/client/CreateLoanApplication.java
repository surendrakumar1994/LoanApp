package com.bank.customer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.bank.customer.dto.LoanApplicationDto;

@FeignClient(name="LONEAPPLICATION-SERVICE")
public interface CreateLoanApplication {

	@PostMapping("/loneapp/add-lone-customer")
	public LoanApplicationDto createLoanApplication(LoanApplicationDto rating);
}
