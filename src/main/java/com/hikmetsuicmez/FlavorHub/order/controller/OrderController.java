package com.hikmetsuicmez.FlavorHub.order.controller;

import com.hikmetsuicmez.FlavorHub.contants.ApiEndpoints;
import com.hikmetsuicmez.FlavorHub.docs.OrderApiDocs;
import com.hikmetsuicmez.FlavorHub.enums.OrderStatus;
import com.hikmetsuicmez.FlavorHub.order.dtos.OrderDTO;
import com.hikmetsuicmez.FlavorHub.order.dtos.OrderItemDTO;
import com.hikmetsuicmez.FlavorHub.order.services.OrderService;
import com.hikmetsuicmez.FlavorHub.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.Order.BASE)
public class OrderController implements OrderApiDocs {

    private final OrderService orderService;

    @PostMapping(ApiEndpoints.Order.CHECKOUT)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Response<?>> checkout() {
        return ResponseEntity.ok(orderService.placeOrderFromCart());
    }

    @GetMapping(ApiEndpoints.Order.GET_BY_ID)
    public ResponseEntity<Response<OrderDTO>> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping(ApiEndpoints.Order.GET_MY_ORDERS)
    public ResponseEntity<Response<List<OrderDTO>>> getMyOrders() {
        return ResponseEntity.ok(orderService.getOrdersOfUser());
    }

    @GetMapping(ApiEndpoints.Order.GET_ORDER_ITEM_BY_ID)
    public ResponseEntity<Response<OrderItemDTO>> getOrderItemById(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderService.getOrderItemById(orderItemId));
    }

    @GetMapping(ApiEndpoints.Order.GET_ALL_ORDERS)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Page<OrderDTO>>> getAllOrders(
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
            ) {
        return ResponseEntity.ok(orderService.getAllOrders(orderStatus, page, size));
    }

    @PutMapping(ApiEndpoints.Order.UPDATE_ORDER_STATUS)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<OrderDTO>> updateOrderStatus(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderDTO));
    }

    @GetMapping(ApiEndpoints.Order.GET_UNIQUE_CUSTOMERS_COUNT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Long>> getUniqueCustomersCount() {
        return ResponseEntity.ok(orderService.countUniqueCustomers());
    }
}
