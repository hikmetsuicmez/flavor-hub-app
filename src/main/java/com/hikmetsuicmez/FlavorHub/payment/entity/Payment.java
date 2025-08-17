package com.hikmetsuicmez.FlavorHub.payment.entity;

import com.hikmetsuicmez.FlavorHub.auth_users.entity.User;
import com.hikmetsuicmez.FlavorHub.enums.PaymentGateway;
import com.hikmetsuicmez.FlavorHub.enums.PaymentStatus;
import com.hikmetsuicmez.FlavorHub.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
@Builder
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order; // ID of the order associated with the payment

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // e.g., "PENDING", "COMPLETED", "FAILED"

    private String transactionId; // Unique identifier for the transaction

    @Enumerated(EnumType.STRING)
    private PaymentGateway paymentGateway;

    private String failureReason; // Reason for payment failure, if applicable

    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // User who made the payment

    // Additional fields can be added as needed, such as timestamps, currency, etc.
}
