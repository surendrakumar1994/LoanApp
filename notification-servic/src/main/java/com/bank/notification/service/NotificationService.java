package com.bank.notification.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.notification.client.LoanApplicationClient;
import com.bank.notification.dto.LoanApplicationDto;
import com.bank.notification.dto.NotificationDto;
import com.bank.notification.entity.Notification;
import com.bank.notification.exception.NotificationNotFoundException;
import com.bank.notification.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private LoanApplicationClient loanApplicationClient;
	

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    private Optional<Notification> findNotificationById(Long id) {
        return Optional.ofNullable(notificationRepository.findById(id).orElseThrow(() ->
                new NotificationNotFoundException("Related notification with id: " + id + " not found")));
    }

    public Notification getNotificationById(Long notificationId) {
        return findNotificationById(notificationId).get();
    }
    
    public NotificationDto getNotificationLoneapp(Long notificationId) {
    	Notification n=findNotificationById(notificationId).get();
    	
    	NotificationDto d=new NotificationDto();
    	d.setId(n.getId());
    	d.setLoanApplication_id(n.getLoanApplication_id());
    	d.setText(n.getText());
        return d;
    }

    
    //private LoanApplicationClient loanApplicationClient;// updateLoanAppNotification 
    public void createNotification(Notification notification) {
    	LoanApplicationDto d=loanApplicationClient.getLoanApplicationByIds(notification.getLoanApplication_id());
    	if(d.getId()!=null) {
    		notification.setLoanApplication_id(d.getId());	
    	}
    	
        Notification n=notificationRepository.save(notification);
        NotificationDto dd=new NotificationDto();
        dd.setId(n.getId());
        dd.setLoanApplication_id(n.getLoanApplication_id());
        dd.setText(n.getText());
        loanApplicationClient.updateLoanAppNotification(dd);
    }
    //    public String sendMessageForResult(LoanApplication loanApplication) {
//        String resultMessage = "Your loan application is " + loanApplication.getLoanApplicationStatus() ;
//        if(loanApplication.getLo() == LoanResult.APPROVED) {
//            resultMessage += " and your credit limit is " + creditApplication.getCreditLimit() + " TL.";
//        }
//
//        return "Notification message is sent to " + creditApplication.getCustomer().getPhone() + " number with the message : " + resultMessage ;
//    }

}
