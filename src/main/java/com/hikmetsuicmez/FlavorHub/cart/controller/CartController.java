package com.hikmetsuicmez.FlavorHub.cart.controller;

import com.hikmetsuicmez.FlavorHub.cart.dtos.CartDTO;
import com.hikmetsuicmez.FlavorHub.cart.services.CartService;
import com.hikmetsuicmez.FlavorHub.contants.ApiEndpoints;
import com.hikmetsuicmez.FlavorHub.docs.CartApiDocs;
import com.hikmetsuicmez.FlavorHub.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoints.Cart.BASE)
@RequiredArgsConstructor
public class CartController implements CartApiDocs {

    private final CartService cartService;

    @PostMapping(ApiEndpoints.Cart.ADD_ITEM)
    public ResponseEntity<Response<?>> addItemToCart(@RequestBody @Valid CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.addItemToCart(cartDTO));
    }

    @PutMapping(ApiEndpoints.Cart.INCREMENT_ITEM)
    public ResponseEntity<Response<?>> incrementItem(@PathVariable Long menuId) {
        return ResponseEntity.ok(cartService.incrementItem(menuId));
    }

    @PutMapping(ApiEndpoints.Cart.DECREMENT_ITEM)
    public ResponseEntity<Response<?>> decrementItem(@PathVariable Long menuId) {
        return ResponseEntity.ok(cartService.decrementItem(menuId));
    }

    @DeleteMapping(ApiEndpoints.Cart.REMOVE_ITEM)
    public ResponseEntity<Response<?>> removeItem(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeItem(cartItemId));
    }

    @GetMapping(ApiEndpoints.Cart.GET_CART)
    public ResponseEntity<Response<?>> getShoppingCart() {
        return ResponseEntity.ok(cartService.getShoppingCart());
    }

    @DeleteMapping(ApiEndpoints.Cart.CLEAR_CART)
    public ResponseEntity<Response<?>> clearShoppingCart() {
        return ResponseEntity.ok(cartService.clearShoppingCart());
    }

}
