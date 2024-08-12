package com.bank.loneapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoanApplicationDto {
	

    private Long id;

    private String nationalIdentityNumber;
    private Long notification_id;
   

}
