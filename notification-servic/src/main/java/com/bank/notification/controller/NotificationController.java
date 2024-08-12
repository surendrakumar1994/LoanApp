package com.bank.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.notification.entity.Notification;
import com.bank.notification.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/get/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId) {
        return new ResponseEntity(notificationService.getNotificationById(notificationId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity addCustomer(@RequestBody Notification notification) {
        notificationService.createNotification(notification);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
