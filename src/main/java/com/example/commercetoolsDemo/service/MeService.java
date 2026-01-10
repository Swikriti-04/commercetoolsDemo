package com.example.commercetoolsDemo.service;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.feign.MeFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MeService {

    private final MeFeignClient meFeignClient;
    private final TokenService tokenService;

    @Value("${ct.projectKey}")
    private String projectKey;

    public MeService(MeFeignClient meFeignClient, TokenService tokenService) {
        this.meFeignClient = meFeignClient;
        this.tokenService = tokenService;
    }

    public Cart getActiveCart() {
        return meFeignClient.getActiveCart(
                projectKey,
                "Bearer " + tokenService.getCustomerAccessToken()
        );
    }

    public Cart updateCart(String cartId, CartUpdate request) {
        return meFeignClient.updateMyCart(
                projectKey,
                cartId,
                "Bearer " + tokenService.getCustomerAccessToken(),
                request
        );
    }

    public Order createOrder(OrderFromCartDraft request) {
        return meFeignClient.createOrder(
                projectKey,
                "Bearer " + tokenService.getCustomerAccessToken(),
                request
        );
    }
}
