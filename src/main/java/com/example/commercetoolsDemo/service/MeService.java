package com.example.commercetoolsDemo.service;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartUpdate;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.feign.MeFeignClient;
import com.example.commercetoolsDemo.mapper.CartOrderMapper;
import com.example.commercetoolsDemo.model.CartResponse;
import com.example.commercetoolsDemo.model.OrderResponse;
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

    public CartResponse getActiveCart() {
        Cart cart = meFeignClient.getActiveCart(
                projectKey,
                "Bearer " + tokenService.getCustomerAccessToken()
        );
        return CartOrderMapper.toCartResponse(cart);
    }

    public CartResponse updateCart(String cartId, CartUpdate request) {
        Cart cart = meFeignClient.updateMyCart(
                projectKey,
                cartId,
                "Bearer " + tokenService.getCustomerAccessToken(),
                request
        );
        return CartOrderMapper.toCartResponse(cart);
    }

    public OrderResponse createOrder(OrderFromCartDraft request) {
        Order order = meFeignClient.createOrder(
                projectKey,
                "Bearer " + tokenService.getCustomerAccessToken(),
                request
        );
        return CartOrderMapper.toOrderResponse(order);
    }
}
