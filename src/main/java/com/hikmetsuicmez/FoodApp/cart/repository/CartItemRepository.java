package com.hikmetsuicmez.FoodApp.cart.repository;

import com.hikmetsuicmez.FoodApp.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
