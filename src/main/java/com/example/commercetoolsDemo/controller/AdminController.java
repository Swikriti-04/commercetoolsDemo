package com.example.commercetoolsDemo.controller;

import com.commercetools.api.models.cart.*;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.model.*;
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
            @RequestBody CustomerDraft request) {

        return ResponseEntity.ok(adminService.createCustomer(request));
    }

    @PostMapping("/carts")
    public ResponseEntity<Cart> createCart(
            @RequestBody CartDraft request) {
        return ResponseEntity.ok(adminService.createCart(request));
    }


    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Cart> updateCart(
            @PathVariable String cartId,
            @RequestBody CartUpdate update) {
        return ResponseEntity.ok(adminService.updateCart(cartId, update));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderFromCartDraft request) {
        return ResponseEntity.ok(adminService.createOrder(request));
    }
}
