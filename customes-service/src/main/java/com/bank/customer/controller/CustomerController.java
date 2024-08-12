package com.bank.customer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.customer.client.CreateLoanApplication;
import com.bank.customer.dto.CustomerDto;
import com.bank.customer.dto.LoanApplicationDto;
import com.bank.customer.entity.Customer;
import com.bank.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CreateLoanApplication createLoanApplication;
	@Autowired
	private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity(customerService.getAllCustomers(), HttpStatus.OK);
    }
    
    @PostMapping("/submit-loan-application")
    public String submitLoanApplication(@RequestBody Customer customer) {
    	customerService.addCustomer(customer);
    	
        return "Save successfully....";
    }

  //


    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody Customer customerDTO) {
        customerService.addCustomer(customerDTO);
        
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    
   
    @GetMapping("/with-employees")
    public List<CustomerDto> findAllWithEmployees() {
        
    	
        List<Customer> customers
                = customerService.getAllCustomers();
        List<CustomerDto> c1=new ArrayList<CustomerDto>();
        List<CustomerDto> c2=new ArrayList<CustomerDto>();
        
        for (Customer ca : customers) {
        	CustomerDto c=new CustomerDto();
			c.setNationalIdentityNumber(ca.getNationalIdentityNumber());
			c.setFirstName(ca.getFirstName());
			c.setLastName(ca.getLastName());
			c.setPhone(ca.getPhone());
			c.setEmail(ca.getEmail());
			c.setMonthlyIncome(ca.getMonthlyIncome());
			c.setGender(ca.getGender());
			c.setAge(ca.getAge());
			c.setLoanScore(ca.getLoanScore());
			c1.add(c);
		}
        c2.addAll(c1);
        //c2.forEach(department ->
        //department.setLoanApplicationsDto(
        	//	createLoanApplication.findByNationalIdentityNumber(department.getNationalIdentityNumber())));
        return  c2;
    }




    @GetMapping("/get/{nationalIdentityNumber}")
    public ResponseEntity<CustomerDto> getCustomerByNationalIdentityNumber(@PathVariable String nationalIdentityNumber) {
        return new ResponseEntity(customerService.getCustomerByNationalIdentityNumber(nationalIdentityNumber), HttpStatus.OK);
    }

  /*  @PutMapping("/update/{nationalIdentityNumber}")
    public ResponseEntity updateCustomer(@PathVariable String nationalIdentityNumber, @RequestBody CustomerDto customerDTO) {
        customerService.updateCustomer(nationalIdentityNumber, customerDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/update/{nationalIdentityNumber}")
    public ResponseEntity updateCustomerPartially(@PathVariable String nationalIdentityNumber, @RequestBody Map<Object, Object> objectMap) {
        customerService.updateCustomerPartially(nationalIdentityNumber, objectMap);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{nationalIdentityNumber}")
    public ResponseEntity deleteCustomer(@PathVariable String nationalIdentityNumber) {
        customerService.deleteCustomer(nationalIdentityNumber);
        return new ResponseEntity(HttpStatus.OK);
    }
*/
    

}
