package com.example.commercetoolsDemo.feign;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.config.AdminFeignConfig;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
@FeignClient(
        name = "adminClient",
        url = "${ct.apiUrl}",
        configuration = AdminFeignConfig.class
)
public interface AdminFeignClient {

    @PostMapping("/{projectKey}/customers")
    Customer createCustomer(
            @PathVariable String projectKey,
            @RequestBody CustomerDraft request
    );

    @PostMapping("/{projectKey}/carts")
    Cart createCart(
            @PathVariable String projectKey,
            @RequestBody CartDraft request
    );


    @PostMapping("/{projectKey}/carts/{cartId}")
    Cart updateCart(
            @PathVariable String projectKey,
            @PathVariable String cartId,
            @RequestBody CartUpdate request
    );

    @PostMapping("/{projectKey}/orders")
    Order createOrder(
            @PathVariable String projectKey,
            @RequestBody OrderFromCartDraft request
    );

}
