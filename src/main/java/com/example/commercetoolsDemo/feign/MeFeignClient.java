package com.example.commercetoolsDemo.feign;

import com.example.commercetoolsDemo.config.AdminFeignConfig;
import com.example.commercetoolsDemo.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
        name = "MeFeignClient",
        url = "${ct.apiUrl}"
)
public interface MeFeignClient {

    @GetMapping("/{projectKey}/me/active-cart")
    Map<String, Object> getActiveCart(
            @PathVariable("projectKey") String projectKey,
            @RequestHeader("Authorization") String authorization
    );


    @PostMapping("/{projectKey}/me/carts/{cartId}")
    Map<String, Object> updateMyCart(
            @PathVariable("projectKey") String projectKey,
            @PathVariable("cartId") String cartId,
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, Object> body
    );

    @PostMapping("/{projectKey}/me/orders")
    Map<String, Object> createOrder(
            @PathVariable("projectKey") String projectKey,
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, Object> body
    );
}
