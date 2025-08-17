package com.hikmetsuicmez.FlavorHub.cart.controller;

import com.hikmetsuicmez.FlavorHub.cart.dtos.CartDTO;
import com.hikmetsuicmez.FlavorHub.cart.services.CartService;
import com.hikmetsuicmez.FlavorHub.docs.CartApiDocs;
import com.hikmetsuicmez.FlavorHub.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController implements CartApiDocs {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<Response<?>> addItemToCart(@RequestBody @Valid CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.addItemToCart(cartDTO));
    }

    @PutMapping("/items/increment/{menuId}")
    public ResponseEntity<Response<?>> incrementItem(@PathVariable Long menuId) {
        return ResponseEntity.ok(cartService.incrementItem(menuId));
    }

    @PutMapping("/items/decrement/{menuId}")
    public ResponseEntity<Response<?>> decrementItem(@PathVariable Long menuId) {
        return ResponseEntity.ok(cartService.decrementItem(menuId));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Response<?>> removeItem(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeItem(cartItemId));
    }

    @GetMapping
    public ResponseEntity<Response<?>> getShoppingCart() {
        return ResponseEntity.ok(cartService.getShoppingCart());
    }

    @DeleteMapping
    public ResponseEntity<Response<?>> clearShoppingCart() {
        return ResponseEntity.ok(cartService.clearShoppingCart());
    }


}
