package com.hikmetsuicmez.FoodApp.email_notification.repository;

import com.hikmetsuicmez.FoodApp.email_notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
