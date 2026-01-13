package com.example.commercetoolsDemo.controller;

import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.model.CartResponse;
import com.example.commercetoolsDemo.model.OrderResponse;
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
    public ResponseEntity<CartResponse> getActiveCart() {
        return ResponseEntity.ok(meService.getActiveCart());
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<CartResponse> updateCart(
            @PathVariable String cartId,
            @RequestBody CartUpdate request) {
        return ResponseEntity.ok(meService.updateCart(cartId, request));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderFromCartDraft request) {
        return ResponseEntity.ok(meService.createOrder(request));
    }
}
