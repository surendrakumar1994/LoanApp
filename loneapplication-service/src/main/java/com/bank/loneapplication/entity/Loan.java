package com.bank.loneapplication.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.bank.loneapplication.enums.LoanScoreResult;
import com.bank.loneapplication.enums.LoanStatus;
import com.bank.loneapplication.enums.LoanType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private Double loanLimit;

    @Enumerated(EnumType.STRING)
    private LoanScoreResult loanScoreResult;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date loanDate;

    private final Integer creditMultiplier = 4;


    public Loan(LoanType loanType, Double loanLimit, LoanScoreResult loanScoreResult, LoanStatus loanStatus, Date loanDate) {
        this.loanType = loanType;
        this.loanLimit = loanLimit;
        this.loanScoreResult = loanScoreResult;
        this.loanStatus = loanStatus;
        this.loanDate = loanDate;
    }

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "loan")
    private LoanApplication loanApplication;

}
