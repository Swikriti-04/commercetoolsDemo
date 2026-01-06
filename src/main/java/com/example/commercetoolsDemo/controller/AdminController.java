package com.example.commercetoolsDemo.controller;

import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateCartRequest;
import com.example.commercetoolsDemo.dto.request.CreateCustomerRequest;
import com.example.commercetoolsDemo.dto.request.CreateOrderRequest;
import com.example.commercetoolsDemo.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(
            @RequestBody CreateCustomerRequest request) {

        return ResponseEntity.ok(adminService.createCustomer(request));
    }


    @PostMapping("/carts")
    public ResponseEntity<?> createCart(
            @RequestBody CreateCartRequest request) {

        return ResponseEntity.ok(adminService.createCart(request));
    }


    @PostMapping("/carts/{cartId}/line-items")
    public ResponseEntity<?> addLineItem(
            @PathVariable String cartId,
            @RequestBody CartUpdateRequest request) {

        return ResponseEntity.ok(
                adminService.addLineItem(cartId, request)
        );
    }


    @PostMapping("/carts/{cartId}/shipping-address")
    public ResponseEntity<?> setShippingAddress(
            @PathVariable String cartId,
            @RequestBody CartUpdateRequest request) {

        return ResponseEntity.ok(
                adminService.setShippingAddress(cartId, request)
        );
    }


    @PostMapping("/carts/{cartId}/shipping-method")
    public ResponseEntity<?> setShippingMethod(
            @PathVariable String cartId,
            @RequestBody CartUpdateRequest request) {

        return ResponseEntity.ok(
                adminService.setShippingMethod(cartId, request)
        );
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(
            @RequestBody CreateOrderRequest request) {

        return ResponseEntity.ok(adminService.createOrder(request));
    }
}
