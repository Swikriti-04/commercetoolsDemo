package com.example.commercetoolsDemo.feign;

import com.example.commercetoolsDemo.config.AdminFeignConfig;
import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateCartRequest;
import com.example.commercetoolsDemo.dto.request.CreateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "adminClient",
        url = "${ct.apiUrl}",
        configuration = AdminFeignConfig.class
)
public interface AdminFeignClient {

    // 1️⃣ Get Cart
    @GetMapping("/{projectKey}/carts/{id}")
    Object getCart(
            @PathVariable String projectKey,
            @PathVariable String id
    );

    // 2️⃣ Create Cart
    @PostMapping(
            value = "/{projectKey}/carts",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Object createCart(
            @PathVariable String projectKey,
            @RequestBody CreateCartRequest body
    );

    // 3️⃣ Delete Cart
    @DeleteMapping("/{projectKey}/carts/{id}")
    Object deleteCart(
            @PathVariable String projectKey,
            @PathVariable String id,
            @RequestParam("version") Long version
    );

    // 4️⃣ Update Cart (generic)
    @PostMapping(
            value = "/{projectKey}/carts/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Object updateCart(
            @PathVariable String projectKey,
            @PathVariable String id,
            @RequestBody CartUpdateRequest body
    );

    // 5️⃣ Get Shipping Methods for Cart
    @GetMapping("/{projectKey}/shipping-methods")
    Object getShippingMethods(
            @PathVariable String projectKey,
            @RequestParam("cartId") String cartId
    );

    // 6️⃣ Create Order
    @PostMapping(
            value = "/{projectKey}/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Object createOrder(
            @PathVariable String projectKey,
            @RequestBody CreateOrderRequest request
    );
}
