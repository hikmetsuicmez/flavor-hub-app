package com.hikmetsuicmez.FoodApp.order.services;

import com.hikmetsuicmez.FoodApp.auth_users.entity.User;
import com.hikmetsuicmez.FoodApp.auth_users.services.UserService;
import com.hikmetsuicmez.FoodApp.cart.entity.Cart;
import com.hikmetsuicmez.FoodApp.cart.entity.CartItem;
import com.hikmetsuicmez.FoodApp.cart.repository.CartRepository;
import com.hikmetsuicmez.FoodApp.cart.services.CartService;
import com.hikmetsuicmez.FoodApp.email_notification.dtos.NotificationDTO;
import com.hikmetsuicmez.FoodApp.email_notification.services.NotificationService;
import com.hikmetsuicmez.FoodApp.enums.OrderStatus;
import com.hikmetsuicmez.FoodApp.enums.PaymentStatus;
import com.hikmetsuicmez.FoodApp.exceptions.BadRequestException;
import com.hikmetsuicmez.FoodApp.exceptions.NotFoundException;
import com.hikmetsuicmez.FoodApp.menu.dtos.MenuDTO;
import com.hikmetsuicmez.FoodApp.order.dtos.OrderDTO;
import com.hikmetsuicmez.FoodApp.order.dtos.OrderItemDTO;
import com.hikmetsuicmez.FoodApp.order.entity.Order;
import com.hikmetsuicmez.FoodApp.order.entity.OrderItem;
import com.hikmetsuicmez.FoodApp.order.repository.OrderItemRepository;
import com.hikmetsuicmez.FoodApp.order.repository.OrderRepository;
import com.hikmetsuicmez.FoodApp.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final TemplateEngine templateEngine;
    private final CartService cartService;
    private final CartRepository cartRepository;


    @Value("${base.payment.link}")
    private String basePaymentLink;

    @Transactional
    @Override
    // Sepetten sipariş oluşturma işlemi
    public Response<?> placeOrderFromCart() {
        log.info("Inside placeOrderFromCart()");

        User customer = userService.getCurrentLoggedInUser();

        log.info("user passed: {}", customer.getName());

        String deliveryAddress = customer.getAddress();

        log.info("Delivery address passed: {}", deliveryAddress);

        if (deliveryAddress == null || deliveryAddress.isEmpty()) {
            throw new NotFoundException("Delivery address Not Found for the user: " + customer.getName());
        }

        Cart cart = cartRepository.findByUser_Id(customer.getId())
                .orElseThrow(() -> new NotFoundException("Cart not found for user: " + customer.getName()));

        log.info("Cart passed: {}", cart.getId());

        List<CartItem> cartItems = cart.getCartItems();

        log.info("Cart items passed: {}", cartItems.size());

        if (cartItems == null || cartItems.isEmpty()) throw new BadRequestException("Cart is empty");

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        log.info("Total amount passed: {}", totalAmount);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .menu(cartItem.getMenu())
                    .quantity(cartItem.getQuantity())
                    .pricePerUnit(cartItem.getPricePerUnit())
                    .subTotal(cartItem.getSubTotal())
                    .build();
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getSubTotal());
        }
        log.info("Order items passed: {}", orderItems.size());

        Order order = Order.builder()
                .user(customer)
                .orderItems(orderItems)
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.INITIALIZED)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        log.info("Order build passed: {}", order.getId());

        Order savedOrder = orderRepository.save(order); // save order
        log.info("Order saved passed: {}", savedOrder.getId());

        orderItems.forEach(orderItem -> orderItem.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems); // save order item

        log.info("Order items saved passed: {}", orderItems.size());

        // Clear the user's cart after the order is places
        cartService.clearShoppingCart();
        log.info("Shopping Cart cleared passed: {}", cart.getId());

        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);

        log.info("model mapper mapped saveOrder to OrderDTO");

        // Send email notification
        sendOrderConfirmationEmail(customer, orderDTO);

        log.info("building response to send");

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Your order has been received! We've sent a secure payment link tou your email. Please proceed for payment to confirm your order.")
                .build();
    }

    @Override
    // Sipariş ID'sine göre siparişi getirme işlemi
    public Response<OrderDTO> getOrderById(Long orderId) {
        log.info("Inside getOrderById()");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        return Response.<OrderDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Order retrieved successfully")
                .data(orderDTO)
                .build();
    }

    @Override
    // Sipariş durumuna göre tüm siparişleri getirme işlemi
    public Response<Page<OrderDTO>> getAllOrders(OrderStatus orderStatus, int page, int size) {
        log.info("Inside getAllOrders()");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Order> orderPage;
        if (orderStatus != null) {
            orderPage = orderRepository.findByOrderStatus(orderStatus, pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }
        Page<OrderDTO> orderDTOPage = orderPage.map(order -> {
            OrderDTO dto = modelMapper.map(order, OrderDTO.class);
            dto.getOrderItems().forEach(orderItemDTO -> orderItemDTO.getMenu().setReviews(null));
            return dto;
        });

        return Response.<Page<OrderDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Orders retrieved successfully")
                .data(orderDTOPage)
                .build();
    }

    @Override
    // Kullanıcıya ait tüm siparişleri getirme işlemi
    public Response<List<OrderDTO>> getOrdersOfUser() {
        log.info("Inside getOrdersOfUser()");

        User customer = userService.getCurrentLoggedInUser();
        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(customer);

        List<OrderDTO> orderDTOS = orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();

        orderDTOS.forEach(orderItem -> {
            orderItem.setUser(null);
            orderItem.getOrderItems().forEach(item -> item.getMenu().setReviews(null));
        });

        return Response.<List<OrderDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Orders of user retrieved successfully")
                .data(orderDTOS)
                .build();
    }

    @Override
    // Sipariş öğesi ID'sine göre sipariş öğesini getirme işlemi
    public Response<OrderItemDTO> getOrderItemById(Long orderItemId) {
        log.info("Inside getOrderItemById()");

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found with ID: " + orderItemId));

        OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);

        orderItemDTO.setMenu(modelMapper.map(orderItem.getMenu(), MenuDTO.class));

        return Response.<OrderItemDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Order item retrieved successfully")
                .data(orderItemDTO)
                .build();
    }

    @Override
    // Sipariş durumunu güncelleme işlemi
    public Response<OrderDTO> updateOrderStatus(OrderDTO orderDTO) {
        log.info("Inside updateOrderStatus()");

        Order order = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderDTO.getId()));

        OrderStatus orderStatus = orderDTO.getOrderStatus();
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return Response.<OrderDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Order status updated successfully")
                .build();
    }

    @Override
    // Benzersiz müşteri sayısını sayma işlemi
    public Response<Long> countUniqueCustomers() {
        log.info("Inside countUniqueCustomers()");
        long uniqueCustomersCount = orderRepository.countDistinctUsers();
        return Response.<Long>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Unique customers count retrieved successfully")
                .data(uniqueCustomersCount)
                .build();
    }

    private void sendOrderConfirmationEmail(User customer, OrderDTO orderDTO) {
        log.info("Sending order confirmation email to {}", customer.getEmail());

        String subject = "Your Order Confirmation - Order #" + orderDTO.getId();
        // create a Thymeleaf context and set variables. import the context from Thymeleaf
        Context context =  new Context(Locale.getDefault());

        context.setVariable("customerName", customer.getName());
        context.setVariable("orderId", String.valueOf(orderDTO.getId()));
        context.setVariable("orderDate", orderDTO.getOrderDate());
        context.setVariable("totalAmount", orderDTO.getTotalAmount());

        // Format delivery address
        String deliveryAddress = orderDTO.getUser().getAddress();
        context.setVariable("deliveryAddress", deliveryAddress);

        context.setVariable("currentYear", Year.now());

        // Build the order items HTML using StringBuilder
        StringBuilder orderItemsHtml = new StringBuilder();

        for (OrderItemDTO item : orderDTO.getOrderItems()) {
            orderItemsHtml.append("<div class=\"order-item\">")
                    .append("<p>").append(item.getMenu().getName()).append(" x").append(item.getQuantity()).append("</p>")
                    .append("<p> $").append(item.getSubTotal()).append("</p>")
                    .append("</div>");
        }


        context.setVariable("orderItemsHtml", orderItemsHtml.toString());
        context.setVariable("totalItems", orderDTO.getOrderItems().size());

        String paymentLink = basePaymentLink + orderDTO.getId() + "&amount=" + orderDTO.getTotalAmount();
        context.setVariable("paymentLink", paymentLink);

        String emailContent = templateEngine.process("order-confirmation", context);

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(customer.getEmail())
                .subject(subject)
                .body(emailContent)
                .isHtml(true)
                .build();

        notificationService.sendEmail(notificationDTO);

    }
}
