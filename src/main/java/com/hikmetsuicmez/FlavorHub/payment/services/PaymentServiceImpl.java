package com.hikmetsuicmez.FlavorHub.payment.services;

import com.hikmetsuicmez.FlavorHub.email_notification.dtos.NotificationDTO;
import com.hikmetsuicmez.FlavorHub.email_notification.services.NotificationService;
import com.hikmetsuicmez.FlavorHub.enums.OrderStatus;
import com.hikmetsuicmez.FlavorHub.enums.PaymentGateway;
import com.hikmetsuicmez.FlavorHub.enums.PaymentStatus;
import com.hikmetsuicmez.FlavorHub.exceptions.BadRequestException;
import com.hikmetsuicmez.FlavorHub.exceptions.NotFoundException;
import com.hikmetsuicmez.FlavorHub.order.entity.Order;
import com.hikmetsuicmez.FlavorHub.order.repository.OrderRepository;
import com.hikmetsuicmez.FlavorHub.payment.dtos.PaymentDTO;
import com.hikmetsuicmez.FlavorHub.payment.entity.Payment;
import com.hikmetsuicmez.FlavorHub.payment.repository.PaymentRepository;
import com.hikmetsuicmez.FlavorHub.response.Response;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;
    private final TemplateEngine templateEngine;
    private final ModelMapper modelMapper;

    @Value("${stripe.api.secret.key}")
    private String secreteKey;

    @Value("${frontend.base.url}")
    private String frontendBaseUrl;

    @Override
    public Response<?> initializePayment(PaymentDTO paymentRequest) {

        log.info("INSIDE initializePayment()");
        Stripe.apiKey = secreteKey;

        Long orderId = paymentRequest.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));

        if (order.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new BadRequestException("Payment Already Made For This Order");
        }

        if (paymentRequest.getAmount() == null) {
            throw new BadRequestException("Amount you are passing in is null");
        }

        if (order.getTotalAmount().compareTo(paymentRequest.getAmount()) != 0) {
            throw new BadRequestException("Payment Amount Does Not Tally. Please Contact Out Customer Support Agent");
        }

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue()) // converting to cent in USD
                    .setCurrency("try")
                    .putMetadata("orderId", String.valueOf(orderId))
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            String uniqueTransactionId = intent.getClientSecret();

            return Response.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("success")
                    .data(uniqueTransactionId)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error Creating payment unique transaction ID");
        }
    }

    @Override
    public void updatePaymentForOrder(PaymentDTO paymentDTO) {
        log.info("INSIDE updatePaymentForOrder()");

        Long orderId = paymentDTO.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order Not Found"));

        Payment payment = new Payment();
        payment.setPaymentGateway(PaymentGateway.STRIPE);
        payment.setAmount(paymentDTO.getAmount());
        payment.setTransactionId(paymentDTO.getTransactionId());
        payment.setPaymentStatus(paymentDTO.isSuccess() ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setOrder(order);

        if (!paymentDTO.isSuccess()) {
            payment.setFailureReason(paymentDTO.getFailureReason());
        }

        paymentRepository.save(payment);

        Context context = new Context(Locale.getDefault());
        context.setVariable("customerName", order.getUser().getName());
        context.setVariable("orderId", order.getId());
        context.setVariable("currentYear", Year.now().getValue());
        context.setVariable("amount", paymentDTO.getAmount());

        if (paymentDTO.isSuccess()) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
            order.setOrderStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);

            // Başarılı ödeme için e-posta içeriği
            context.setVariable("transactionId", paymentDTO.getTransactionId());
            context.setVariable("paymentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")));
            context.setVariable("frontendBaseUrl", this.frontendBaseUrl);

            String emailContent = templateEngine.process("payment-success", context);

            NotificationDTO notificationDTO = NotificationDTO.builder()
                            .recipient(order.getUser().getEmail())
                            .subject("Payment Successful - Order #" + order.getId())
                            .body(emailContent)
                            .isHtml(true)
                            .build();

            notificationService.sendEmail(notificationDTO);
        }else {

            order.setPaymentStatus(PaymentStatus.FAILED);
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            // Başarısız ödeme için e-posta içeriği
            context.setVariable("failureReason", paymentDTO.getFailureReason());

            String emailContent = templateEngine.process("payment-failed", context);

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .recipient(order.getUser().getEmail())
                    .subject("Payment Failed - Order #" + order.getId())
                    .body(emailContent)
                    .isHtml(true)
                    .build();

            notificationService.sendEmail(notificationDTO);
        }
    }

    @Override
    public Response<List<PaymentDTO>> getAllPayments() {
        log.info("INSIDE getAllPayments()");
        List<Payment> payments = paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<PaymentDTO> paymentDTOList = payments
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .toList();

        paymentDTOList.forEach(item -> {
            item.setOrder(null);
            item.setUser(null);
        });

        return Response.<List<PaymentDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("payments retrieved successfully")
                .data(paymentDTOList)
                .build();
    }

    @Override
    public Response<PaymentDTO> getPaymentById(Long paymentId) {
        log.info("INSIDE getPaymentById()");

    Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new NotFoundException("Payment Not Found"));

    PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);

    paymentDTO.getUser().setRoles(null);
    paymentDTO.getOrder().setUser(null);
    paymentDTO.getOrder().getOrderItems().forEach(item -> {
        item.getMenu().setReviews(null);
    });

    return Response.<PaymentDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .message("payment retrieved successfully by id")
            .data(paymentDTO)
            .build();
    }
}
