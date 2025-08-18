package com.hikmetsuicmez.FlavorHub.payment.repository;

import com.hikmetsuicmez.FlavorHub.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    
}
