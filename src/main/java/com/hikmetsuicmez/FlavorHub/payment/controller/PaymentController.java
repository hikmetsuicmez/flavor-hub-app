package com.hikmetsuicmez.FlavorHub.payment.controller;

import com.hikmetsuicmez.FlavorHub.contants.ApiEndpoints;
import com.hikmetsuicmez.FlavorHub.docs.PaymentApiDocs;
import com.hikmetsuicmez.FlavorHub.payment.dtos.PaymentDTO;
import com.hikmetsuicmez.FlavorHub.payment.services.PaymentService;
import com.hikmetsuicmez.FlavorHub.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.Payment.BASE)
@RequiredArgsConstructor
public class PaymentController implements PaymentApiDocs {

    private final PaymentService paymentService;

    @PostMapping(ApiEndpoints.Payment.INITIALIZE_PAYMENT)
    public ResponseEntity<Response<?>> initializePayment(@RequestBody @Valid PaymentDTO paymentRequest) {
        return ResponseEntity.ok(paymentService.initializePayment(paymentRequest));
    }

    @PutMapping(ApiEndpoints.Payment.UPDATE_PAYMENT)
    public void updatePaymentForOrder(@RequestBody PaymentDTO paymentDTO) {
        paymentService.updatePaymentForOrder(paymentDTO);
    }

    @GetMapping(ApiEndpoints.Payment.GET_ALL_PAYMENTS)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<PaymentDTO>>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping(ApiEndpoints.Payment.GET_PAYMENT_BY_ID)
    public ResponseEntity<Response<PaymentDTO>> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }
}
