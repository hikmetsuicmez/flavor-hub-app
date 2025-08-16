package com.hikmetsuicmez.FoodApp.order.services;

import com.hikmetsuicmez.FoodApp.enums.OrderStatus;
import com.hikmetsuicmez.FoodApp.order.dtos.OrderDTO;
import com.hikmetsuicmez.FoodApp.order.dtos.OrderItemDTO;
import com.hikmetsuicmez.FoodApp.response.Response;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Response<?> placeOrderFromCart();
    Response<OrderDTO> getOrderById(Long orderId);
    Response<Page<OrderDTO>> getAllOrders(OrderStatus orderStatus, int page, int size);
    Response<List<OrderDTO>> getOrdersOfUser();
    Response<OrderItemDTO> getOrderItemById(Long orderItemId);
    Response<OrderDTO> updateOrderStatus(OrderDTO orderDTO);
    Response<Long> countUniqueCustomers();

}
