package com.bank.loneapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.loneapplication.client.CustomerClient;
import com.bank.loneapplication.dto.LoanApplicationDto;
import com.bank.loneapplication.dto.NotificationDto;
import com.bank.loneapplication.entity.Loan;
import com.bank.loneapplication.entity.LoanApplication;
import com.bank.loneapplication.repository.LoanApplicationRepository;
import com.bank.loneapplication.service.LoanApplicationService;

@RestController
@RequestMapping("/loneapp")
public class LoneApplicationController {
	
	@Autowired
    private CustomerClient customerClient;
	
	@Autowired
	private LoanApplicationService loanApplicationService;
	@Autowired
	private LoanApplicationRepository loneapprepo;
	
	@PostMapping("/addLone")
    public LoanApplication add(@RequestBody LoanApplication lone) {
     
        return loanApplicationService.add(lone);
    }

	@PostMapping("/add-lone-customer")  
    public ResponseEntity addCustomer(@RequestBody LoanApplicationDto lo) {
		System.out.println("lone controller : "+lo);
		LoanApplication c=new LoanApplication();
		c.setNationalIdentityNumber(lo.getNationalIdentityNumber());
		loanApplicationService.add(c);
        
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping   
    public List<LoanApplication> findAll() {
        
        return loanApplicationService.findAll();
    }
        
        @GetMapping("/customer/{nationalIdentityNumber}")  
        public List<LoanApplicationDto> findByNationalIdentityNumber(@PathVariable("nationalIdentityNumber") String nationalIdentityNumber) {
            return loanApplicationService.findByNationalIdentityNumber(nationalIdentityNumber);
        }
        
        @PostMapping("/create/{nationalIdentityNumber}/{loneType}")  
        public ResponseEntity<Integer> createLoanApplication(@PathVariable("nationalIdentityNumber") String nationalIdentityNumber,
        		@PathVariable("loneType") String loneType) {
        	System.out.println("loneType :"+loneType);
            loanApplicationService.createLoanApplication(nationalIdentityNumber,loneType);
            return new ResponseEntity<Integer>(HttpStatus.CREATED);
        }

        @GetMapping("/get/{loanApplicationId}")
        public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable Long loanApplicationId) {
            return new ResponseEntity<>(loanApplicationService.getLoanApplicationById(loanApplicationId), HttpStatus.OK);
        }


        @GetMapping(value = "/result/{nationalIdentityNumber}")
        public ResponseEntity<Loan> getLoanApplicationResult(@PathVariable String nationalIdentityNumber) {
            return new ResponseEntity(loanApplicationService.getLoanApplicationResult(nationalIdentityNumber), HttpStatus.OK);
        }
        
        @GetMapping(value = "/loneapplication/{loneId}")  
        public ResponseEntity<LoanApplicationDto> getLoanApplicationByIds(@PathVariable("loneId") Long loneId) {
            return new ResponseEntity(loanApplicationService.getLoanApplicationByIds(loneId), HttpStatus.OK);
        }
        
        @PostMapping(value = "/loneapplication/notificationupdate/{loneId}") 
        public ResponseEntity<LoanApplicationDto> updateLoanAppNotification(NotificationDto loanAppDto) {
            return new ResponseEntity(loanApplicationService.updateLoanAppNotification(loanAppDto), HttpStatus.OK);
        }

    }


