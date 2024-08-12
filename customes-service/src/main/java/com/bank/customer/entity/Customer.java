package com.bank.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@Entity
public class Customer {

	@Id
    @Column(name = "national_identity_number",length = 11,updatable = false, nullable = false)
    @NotBlank(message = "national identity number can not be blank")
    @Pattern(regexp = "[1-9][0-9]{10}")
    private String nationalIdentityNumber;

    @NotBlank(message = "name can not be null")
    private String firstName;

    @NotBlank(message = "surname can not be null")
    private String lastName;

    @NotNull(message = "monthly income can not be null")
    @Min(1)
    private Double monthlyIncome;

    private String gender;

    @Min(18)
    private  Integer age;

    @NotBlank(message = "phone can not be null")
    private String phone;

    @Email
    private String email;

    private Integer loanScore = 500;

}







