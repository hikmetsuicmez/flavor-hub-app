package com.hikmetsuicmez.FlavorHub.payment.services;

import com.hikmetsuicmez.FlavorHub.payment.dtos.PaymentDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;

import java.util.List;

public interface PaymentService {

    Response<?> initializePayment(PaymentDTO paymentDTO);
    void updatePaymentForOrder(PaymentDTO paymentDTO);
    Response<List<PaymentDTO>> getAllPayments();
    Response<PaymentDTO> getPaymentById(Long paymentId);

}
