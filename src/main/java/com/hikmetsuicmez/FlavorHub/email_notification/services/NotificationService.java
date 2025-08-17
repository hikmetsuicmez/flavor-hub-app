package com.hikmetsuicmez.FlavorHub.email_notification.services;

import com.hikmetsuicmez.FlavorHub.email_notification.dtos.NotificationDTO;

public interface NotificationService {

    void sendEmail(NotificationDTO notificationDTO);
}
