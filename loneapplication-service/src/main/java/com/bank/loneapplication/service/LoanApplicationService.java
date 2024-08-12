package com.bank.loneapplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.bank.loneapplication.client.CustomerClient;
import com.bank.loneapplication.controller.LoneApplicationController;
import com.bank.loneapplication.dto.CustomerDTO;
import com.bank.loneapplication.dto.LoanApplicationDto;
import com.bank.loneapplication.dto.NotificationDto;
import com.bank.loneapplication.entity.Loan;
import com.bank.loneapplication.entity.LoanApplication;
import com.bank.loneapplication.enums.LoanLimit;
import com.bank.loneapplication.enums.LoanScoreResult;
import com.bank.loneapplication.enums.LoanStatus;
import com.bank.loneapplication.enums.LoanType;
import com.bank.loneapplication.exception.InvalidLoanApplicationException;
import com.bank.loneapplication.exception.LoanNotFoundException;
import com.bank.loneapplication.repository.LoanApplicationRepository;
import com.bank.loneapplication.repository.LoanRepository;

@Service
public class LoanApplicationService {

   @Autowired
    private LoanApplicationRepository loanApplicationRepository;
   
   @Autowired
   private CustomerClient customerClient;
 
   @Autowired
   private  LoanRepository loanRepository;
   
   public LoanApplication add(LoanApplication loneapp) {
  
       return loanApplicationRepository.save(loneapp);
   }

   @GetMapping
   public List<LoanApplication> findAll() {
     
       return loanApplicationRepository.findAll();
   }

   
  
   public List<LoanApplicationDto> findByNationalIdentityNumber(String nationalIdentityNumber) {

	   List<LoanApplication> l=loanApplicationRepository.findAll();
	   List<LoanApplicationDto> p=new ArrayList<LoanApplicationDto>();
	   List<LoanApplicationDto> p1=new ArrayList<LoanApplicationDto>();
	   for (LoanApplication loanApplication : l) {
		   LoanApplicationDto ld=new LoanApplicationDto();
		   ld.setId(loanApplication.getId());
		   ld.setNationalIdentityNumber(loanApplication.getNationalIdentityNumber());
		   ld.setNotification_id(loanApplication.getNotification_id());
		   p.add(ld);
	}
	   p1.addAll(p);
	   return p1.stream()
			   .filter(a -> a.getNationalIdentityNumber().equals(nationalIdentityNumber))
					   .toList();
   }
   
   private Loan createLoan(String loneType) {
	   System.out.println("loneType :"+loneType);
	   Loan newLoan=null;
	   if(loneType.equals("PERSONAL")) {
		   System.out.println("loneType :"+loneType);
		   newLoan= new Loan(LoanType.PERSONAL, 0.0, LoanScoreResult.NOT_RESULTED, LoanStatus.ACTIVE, new Date());
	   }else if(loneType.equals("HOME")) {
		   newLoan= new Loan(LoanType.HOME, 0.0, LoanScoreResult.NOT_RESULTED, LoanStatus.ACTIVE, new Date());
	   }else {
		   newLoan= new Loan(LoanType.EDUCATION, 0.0, LoanScoreResult.NOT_RESULTED, LoanStatus.ACTIVE, new Date());
	   }
       // newLoan= new Loan(LoanType.PERSONAL, 0.0, LoanScoreResult.NOT_RESULTED, LoanStatus.ACTIVE, new Date());
       loanRepository.save(newLoan);
       return newLoan;
   }

   public void createLoanApplication(String nationalIdentityNumber,String loneType) {
	   //Optional<MyEntity> optionalEntity = Optional.ofNullable(entity);
	   //Optional<CustomerDTO> customerByNationalIdentityNumber1 = customerClient.getCustomerByNationalIdentityNumber(nationalIdentityNumber);
       
	  // var optionalEntity = Optional.ofNullable(customerByNationalIdentityNumber1);
	   
	   CustomerDTO customerByNationalIdentityNumber = customerClient.getCustomerByNationalIdentityNumber(nationalIdentityNumber);
	   LoanApplication loanApplication = new LoanApplication();
	   loanApplication.setNationalIdentityNumber(customerByNationalIdentityNumber.getNationalIdentityNumber());
	   Loan l=createLoan(loneType);
	   loanApplication.setLoan(l);
	   //loanApplicationRepository.findById(null)
	   loanApplicationRepository.save(loanApplication);
      /* customerByNationalIdentityNumber.ifPresent(customer -> {
           LoanApplication loanApplication = new LoanApplication(optionalEntity.get().get().getNationalIdentityNumber(), createLoan());
           loanApplicationRepository.save(loanApplication);
       });*/
   }



   public Loan getLoanApplicationResult(String nationalIdentityNumber) {
       LoanApplication finalizedApplication = finalizeLoanApplication(nationalIdentityNumber);

       if (finalizedApplication.getLoan().getLoanScoreResult().equals(LoanScoreResult.NOT_RESULTED)) {
           throw new InvalidLoanApplicationException("!");
       } else if (finalizedApplication.getLoan().getLoanScoreResult().equals(LoanScoreResult.REJECTED)) {
           return finalizedApplication.getLoan();
       }
       return finalizedApplication.getLoan();
   }

   

   private Loan loanLimitCalculator(LoanApplication loanApplication) {
	  // Optional<CustomerDTO> customerByNationalIdentityNumber = customerClient.findByCustomer(loanApplication.getNationalIdentityNumber());
	   CustomerDTO customerByNationalIdentityNumber = customerClient.getCustomerByNationalIdentityNumber(loanApplication.getNationalIdentityNumber());
		  
	   var loanScore=0;
	   var income =0.0;
	   if(customerByNationalIdentityNumber!=null)
	   {
		    loanScore = customerByNationalIdentityNumber.getLoanScore();
		    income = customerByNationalIdentityNumber.getMonthlyIncome();
	   }
       var loan = loanApplication.getLoan();
       var loanCustomer = loanApplication.getNationalIdentityNumber();
      // var loanScore = loanCustomer.getLoanScore();
       //var income = loanCustomer.getMonthlyIncome();
       var loanMultiplier = loan.getCreditMultiplier();

       var loanLimitCheck = (income >= LoanLimit.HIGHER.getLoanLimitAmount());
       var loanScoreCheck = (loanScore >= LoanScoreResult.APPROVED.getLoanScoreLimit());

       if (loanScoreCheck) {
           LoanLimit.SPECIAL.setLoanLimitAmount(income * loanMultiplier);
           loan.setLoanLimit(LoanLimit.SPECIAL.getLoanLimitAmount());
       } else if (loanLimitCheck) {
           loan.setLoanLimit(LoanLimit.HIGHER.getLoanLimitAmount());
       } else {
           loan.setLoanLimit(LoanLimit.LOWER.getLoanLimitAmount());
       }
       return loan;
   }

   private void verifyLoan(LoanApplication loanApplication) {
	  // Optional<CustomerDTO> customerByNationalIdentityNumber = customerClient.findByCustomer(loanApplication.getNationalIdentityNumber());
	   CustomerDTO customerByNationalIdentityNumber = customerClient.getCustomerByNationalIdentityNumber(loanApplication.getNationalIdentityNumber());
		 
       //var loanCustomer = loanApplication.getCustomer();

       var loanToUpdate = getNotResultedLoanApplicationOfCustomer(loanApplication);
       if (loanToUpdate == null) return;
       //log.info("Getting loan application for result");

       var loanScore = customerByNationalIdentityNumber.getLoanScore();
       var loanScoreForApproval = (loanScore >= LoanScoreResult.REJECTED.getLoanScoreLimit());

       if (loanScoreForApproval) {
           loanToUpdate.setLoanScoreResult(LoanScoreResult.APPROVED);
           loanApplication.setLoan(loanToUpdate);
           loanToUpdate = loanLimitCalculator(loanApplication);
           //loanApplication.setLoan(loanToUpdate);
       } else {
           loanToUpdate.setLoanScoreResult(LoanScoreResult.REJECTED);
           loanToUpdate.setLoanStatus(LoanStatus.INACTIVE);
       }
       loanRepository.save(loanToUpdate);
       //log.info("resulted the application");
       //TODO: modify sms
       //log.info("Sent sms result");
   }

   private Loan getNotResultedLoanApplicationOfCustomer(LoanApplication customer) {
	   var a=customer.getLoan().getLoanScoreResult().equals(LoanScoreResult.NOT_RESULTED);
//Optional<LoanApplication> optionalUser = Optional.ofNullable(a);
      // var optionalLoan = customer.stream()
                     //  .filter(c -> c.getLoan().getLoanScoreResult().equals(LoanScoreResult.NOT_RESULTED))
                      // .findFirst();
       //Optional.ofNullable(a).isPresent() ? Optional.ofNullable(a)
       return Optional.ofNullable(a).isPresent() ? customer.getLoan() : null;

   }

   private LoanApplication finalizeLoanApplication(String nationalIdentityNumber) {
//LoanApplication finalizedApplication = finalizeLoanApplication(nationalIdentityNumber);
	   //Optional<CustomerDTO> customerByNationalIdentityNumber = customerClient.findByCustomer(nationalIdentityNumber);
	   CustomerDTO customerByNationalIdentityNumber = customerClient.getCustomerByNationalIdentityNumber(nationalIdentityNumber);
		
       if (customerByNationalIdentityNumber!=null) {
           //LoanApplication loanApplication1 = customerByNationalIdentityNumber.get().getLoanApplications().stream().findFirst().get();
    	   
    	   
    	   
    	   var loanApplication = customerByNationalIdentityNumber.getLoanApplicationsDto().stream()
    	    		.findFirst().get();
    	   LoanApplication p=new LoanApplication();
    	   p.setNationalIdentityNumber(loanApplication.getNationalIdentityNumber());
           verifyLoan(p);
       }

       List<LoanApplicationDto> aa=customerByNationalIdentityNumber.getLoanApplicationsDto();
       List<LoanApplication> listlon= new ArrayList<LoanApplication>();
       List<LoanApplication> listlon2= new ArrayList<LoanApplication>();
       for (LoanApplicationDto lo : aa) {
    	   LoanApplication lon=new LoanApplication();
    	   lon.setNationalIdentityNumber(lo.getNationalIdentityNumber());
    	   listlon.add(lon);
	}
       listlon2.addAll(listlon);
       return listlon2.stream()
               .filter(loanApplication -> loanApplication.getNationalIdentityNumber() == loanApplication.getNationalIdentityNumber())
               //.filter(loanApplication -> loanApplication.getLoan().getLoanStatus() == LoanStatus.ACTIVE)
               .findAny()
               .orElseThrow(() -> new InvalidLoanApplicationException("."));
   }




   protected Optional<LoanApplication> findLoanApplicationById(Long id) {
       return Optional.ofNullable(loanApplicationRepository.findById(id).orElseThrow(() ->
               new LoanNotFoundException("Related loan with id: " + id + " not found")));
   }
   
   public LoanApplicationDto updateLoanAppNotification(NotificationDto loanDto) {
	   Optional<LoanApplication> lons=loanApplicationRepository.findById(loanDto.getId());
	   if(lons.isPresent()) {
		   lons.get().setNotification_id(loanDto.getId());
	   }
	   LoanApplication savLone=loanApplicationRepository.save(lons.get());
	   LoanApplicationDto d=new LoanApplicationDto();
	   d.setId(savLone.getId());
	   d.setNationalIdentityNumber(savLone.getNationalIdentityNumber());
	   d.setNotification_id(savLone.getNotification_id());
	   return d;
       
   }

   
   public LoanApplication getLoanApplicationById(Long loanApplicationId) {
       return findLoanApplicationById(loanApplicationId).get();
   }
   
   public LoanApplicationDto getLoanApplicationByIds(Long loanApplicationId) {
	   LoanApplication app=findLoanApplicationById(loanApplicationId).get();
	   LoanApplicationDto d=new LoanApplicationDto();
	   d.setId(app.getId());
	   d.setNationalIdentityNumber(app.getNationalIdentityNumber());
       return d;
   }
   
   
   
  
    

}
