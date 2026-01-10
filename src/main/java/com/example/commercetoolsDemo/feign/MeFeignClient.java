package com.example.commercetoolsDemo.feign;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "me-client", url = "${ct.apiUrl}")
public interface MeFeignClient {

    @GetMapping("/{projectKey}/me/active-cart")
    Cart getActiveCart(
            @PathVariable String projectKey,
            @RequestHeader("Authorization") String authorization
    );

    @PostMapping("/{projectKey}/me/carts/{cartId}")
    Cart updateMyCart(
            @PathVariable String projectKey,
            @PathVariable String cartId,
            @RequestHeader("Authorization") String authorization,
            @RequestBody CartUpdate body
    );

    @PostMapping("/{projectKey}/me/orders")
    Order createOrder(
            @PathVariable String projectKey,
            @RequestHeader("Authorization") String authorization,
            @RequestBody OrderFromCartDraft body
    );
}
