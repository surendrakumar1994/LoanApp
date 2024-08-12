package com.bank.loneapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_application")
public class LoanApplication {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String nationalIdentityNumber;
    private Long notification_id;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="loan_id")
    private Loan loan;

    public LoanApplication(String nationalIdentityNumber, Loan loan) {
        this.nationalIdentityNumber = nationalIdentityNumber;
        this.loan = loan;
    }

    

}
