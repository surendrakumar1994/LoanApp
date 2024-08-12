package com.bank.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
   
    private Long id;
    private String text;
    private Long loanApplication_id;
 

}
