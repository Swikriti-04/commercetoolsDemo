package com.example.commercetoolsDemo.controller;

import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateCartRequest;
import com.example.commercetoolsDemo.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ===================== CART =====================

    // 1️⃣ Get Cart
    @GetMapping("/cart/{id}")
    public ResponseEntity<Object> getCart(@PathVariable String id) {
        return ResponseEntity.ok(adminService.getCart(id));
    }

    // 2️⃣ Create Cart
    @PostMapping("/cart")
    public ResponseEntity<Object> createCart(@RequestBody CreateCartRequest body) {
        return ResponseEntity.ok(adminService.createCart(body));
    }

    // 3️⃣ Delete Cart
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Object> deleteCart(
            @PathVariable String id,
            @RequestParam Long version
    ) {
        return ResponseEntity.ok(adminService.deleteCart(id, version));
    }

    // ===================== LINE ITEM =====================

    // 4️⃣ Add Line Item
    @PostMapping("/cart/{id}/line-item")
    public ResponseEntity<Object> addLineItem(
            @PathVariable String id,
            @RequestBody CartUpdateRequest request
    ) {
        return ResponseEntity.ok(adminService.addLineItem(id, request));
    }

    // ===================== ADDRESS =====================

    // 5️⃣ Set Shipping Address (FULL ADDRESS OBJECT)
    @PostMapping("/cart/{id}/shipping-address")
    public ResponseEntity<Object> setShippingAddress(
            @PathVariable String id,
            @RequestParam Long version,
            @RequestBody CartUpdateRequest.Address address
    ) {
        return ResponseEntity.ok(
                adminService.addShippingAddress(id, version, address)
        );
    }

    // ===================== SHIPPING METHOD =====================

    // 6️⃣ Get Shipping Methods for Cart
    @GetMapping("/cart/{id}/shipping-methods")
    public ResponseEntity<Object> getShippingMethods(@PathVariable String id) {
        return ResponseEntity.ok(adminService.getShippingMethods(id));
    }

    // 7️⃣ Set Shipping Method (MANDATORY before order)
    @PostMapping("/cart/{id}/shipping-method")
    public ResponseEntity<Object> setShippingMethod(
            @PathVariable String id,
            @RequestParam Long version,
            @RequestParam String shippingMethodId
    ) {
        return ResponseEntity.ok(
                adminService.setShippingMethod(id, version, shippingMethodId)
        );
    }

    // ===================== ORDER =====================

    // 8️⃣ Create Order
    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(
            @RequestParam String cartId,
            @RequestParam Long version
    ) {
        return ResponseEntity.ok(
                adminService.createOrder(cartId, version)
        );
    }
}
