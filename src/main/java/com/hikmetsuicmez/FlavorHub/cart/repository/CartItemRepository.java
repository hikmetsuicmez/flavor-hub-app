package com.hikmetsuicmez.FlavorHub.cart.repository;

import com.hikmetsuicmez.FlavorHub.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
