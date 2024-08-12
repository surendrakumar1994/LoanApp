package com.bank.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
