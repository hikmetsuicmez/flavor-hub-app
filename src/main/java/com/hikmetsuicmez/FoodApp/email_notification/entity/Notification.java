package com.hikmetsuicmez.FoodApp.email_notification.entity;

import com.hikmetsuicmez.FoodApp.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @NotBlank(message = "recipient is required")
    private String recipient;

    @Lob
    private String body;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private boolean isHtml;


}
