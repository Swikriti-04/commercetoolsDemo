package com.example.commercetoolsDemo.feign;

import com.example.commercetoolsDemo.config.AdminFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
        name = "admin-client",
        url = "${ct.apiUrl}",
         configuration = AdminFeignConfig.class)
public interface AdminFeignClient {


    @PostMapping("/{projectKey}/customers")
    Map<String, Object> createCustomer(
            @PathVariable("projectKey") String projectKey,
            @RequestBody Map<String, Object> body
    );


    @PostMapping("/{projectKey}/carts")
    Map<String, Object> createCart(
            @PathVariable("projectKey") String projectKey,
            @RequestBody Map<String, Object> body
    );

    @PostMapping("/{projectKey}/carts/{cartId}")
    Map<String, Object> updateCart(
            @PathVariable("projectKey") String projectKey,
            @PathVariable("cartId") String cartId,
            @RequestBody Map<String, Object> body
    );


    @PostMapping("/{projectKey}/orders")
    Map<String, Object> createOrder(
            @PathVariable("projectKey") String projectKey,
            @RequestBody Map<String, Object> body
    );
}
