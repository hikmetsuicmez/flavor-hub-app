package com.hikmetsuicmez.FoodApp.cart.services;

import com.hikmetsuicmez.FoodApp.cart.dtos.CartDTO;
import com.hikmetsuicmez.FoodApp.response.Response;

public interface CartService {

    Response<?> addItemToCart(CartDTO cartDTO);
    Response<?> incrementItem(Long menuId);
    Response<?> decrementItem(Long menuId);
    Response<?> removeItem(Long cartItemId);
    Response<CartDTO> getShoppingCart();
    Response<?> clearShoppingCart();
}
