package com.hikmetsuicmez.FoodApp.payment.repository;

import com.hikmetsuicmez.FoodApp.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    
}
