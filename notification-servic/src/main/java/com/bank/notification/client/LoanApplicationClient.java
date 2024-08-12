package com.bank.notification.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bank.notification.dto.LoanApplicationDto;
import com.bank.notification.dto.NotificationDto;

//@HttpExchange
@FeignClient(name="LONEAPPLICATION-SERVICE")
public interface LoanApplicationClient {
	
	   
	 @GetMapping(value = "/loneapp/loneapplication/{loneId}")
     public LoanApplicationDto getLoanApplicationByIds(@PathVariable("loneId") Long loneId) ;
	 
	 @PostMapping(value = "/loneapp/loneapplication/notificationupdate/{loneId}")
     public LoanApplicationDto updateLoanAppNotification(NotificationDto loanAppDto);
}







