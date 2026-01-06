package com.example.commercetoolsDemo.controller;

import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateOrderRequest;
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
    public ResponseEntity<?> getActiveCart() {
        return ResponseEntity.ok(meService.getActiveCart());
    }


    @PostMapping("/carts/line-items")
    public ResponseEntity<?> addLineItem(
            @RequestBody CartUpdateRequest request) {

        return ResponseEntity.ok(meService.addLineItem(request));
    }

    @PostMapping("/carts/shipping-address")
    public ResponseEntity<?> setShippingAddress(
            @RequestBody CartUpdateRequest request) {

        return ResponseEntity.ok(meService.setShippingAddress(request));
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(
            @RequestBody CreateOrderRequest request) {

        return ResponseEntity.ok(meService.createOrder(request));
    }
}
