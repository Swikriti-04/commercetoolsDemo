package com.example.commercetoolsDemo.controller;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.service.MeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class MeController {

    private final MeService meService;

    public MeController(MeService meService) {
        this.meService = meService;
    }

    @GetMapping("/carts/active")
    public ResponseEntity<Cart> getActiveCart() {
        return ResponseEntity.ok(meService.getActiveCart());
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Cart> updateCart(
            @PathVariable String cartId,
            @RequestBody CartUpdate request) {

        return ResponseEntity.ok(
                meService.updateCart(cartId, request)
        );
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderFromCartDraft request) {

        return ResponseEntity.ok(
                meService.createOrder(request)
        );
    }
}
