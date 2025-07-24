package com.hikmetsuicmez.FoodApp.order.repository;

import com.hikmetsuicmez.FoodApp.auth_users.entity.User;
import com.hikmetsuicmez.FoodApp.enums.OrderStatus;
import com.hikmetsuicmez.FoodApp.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    List<Order> findByUserOrderByOrderDateDesc(User user);

    @Query("SELECT COUNT(DISTINCT o.user.id) FROM Order o")
    long countDistinctUsers();
}
