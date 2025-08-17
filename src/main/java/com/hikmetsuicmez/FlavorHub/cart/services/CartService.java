package com.hikmetsuicmez.FlavorHub.cart.services;

import com.hikmetsuicmez.FlavorHub.cart.dtos.CartDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;

public interface CartService {

    Response<?> addItemToCart(CartDTO cartDTO);
    Response<?> incrementItem(Long menuId);
    Response<?> decrementItem(Long menuId);
    Response<?> removeItem(Long cartItemId);
    Response<CartDTO> getShoppingCart();
    Response<?> clearShoppingCart();
}
