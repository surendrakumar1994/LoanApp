package com.bank.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @Pattern(regexp = "[1-9][0-9]{10}")
    private String nationalIdentityNumber;

    @NotBlank(message = "name can not be null")
    private String firstName;

    @NotBlank(message = "surname can not be null")
    private String lastName;

    @NotBlank(message = "phone can not be null")
    private String phone;

    @Email
    private String email;

    @NotNull(message = "monthly income can not be null")
    @Min(1)
    private Double monthlyIncome;

    private String gender;

    @Min(18)
    private Integer age;

    private Integer loanScore;

    private Long loanApplications_id;
    //private List<LoanApplicationDto> loanApplicationsDto;

}
