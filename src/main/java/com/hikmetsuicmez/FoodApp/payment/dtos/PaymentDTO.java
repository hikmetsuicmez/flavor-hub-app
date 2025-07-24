package com.hikmetsuicmez.FoodApp.payment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hikmetsuicmez.FoodApp.auth_users.dtos.UserDTO;
import com.hikmetsuicmez.FoodApp.enums.PaymentGateway;
import com.hikmetsuicmez.FoodApp.enums.PaymentStatus;
import com.hikmetsuicmez.FoodApp.order.dtos.OrderDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {

    private Long id;

    private Long orderId;

    private BigDecimal amount;

    private PaymentStatus paymentStatus; // e.g., "PENDING", "COMPLETED", "FAILED"

    private String transactionId; // Unique identifier for the transaction

    private PaymentGateway paymentGateway; // e.g., "PAYPAL", "STRIPE", etc.

    private String failureReason; // Reason for payment failure, if applicable

    private boolean success;

    private LocalDateTime paymentDate;

    private OrderDTO order;
    private UserDTO user;
}

