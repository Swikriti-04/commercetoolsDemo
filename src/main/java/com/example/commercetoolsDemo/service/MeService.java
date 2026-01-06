package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateOrderRequest;
import com.example.commercetoolsDemo.feign.MeFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeService {

    private final MeFeignClient meFeignClient;
    private final TokenService tokenService;

    @Value("${ct.projectKey")
    private String projectKey;

    public MeService(MeFeignClient meFeignClient, TokenService tokenService) {
        this.meFeignClient = meFeignClient;
        this.tokenService = tokenService;
    }

    public Map<String, Object> getActiveCart() {
        String token = tokenService.getAdminAccessToken();

        Map<String, Object> cart =
                meFeignClient.getActiveCart(projectKey, "Bearer " + token);

        return mapCartResponse(cart);
    }

    public Map<String, Object> addLineItem(CartUpdateRequest request) {
        return updateMyCart(request, "addLineItem");
    }

    public Map<String, Object> setShippingAddress(CartUpdateRequest request) {
        return updateMyCart(request, "setShippingAddress");
    }

    public Map<String, Object> createOrder(CreateOrderRequest request) {

        String token = tokenService.getAdminAccessToken();

        Map<String, Object> body = Map.of(
                "id", request.getCartId(),
                "version", request.getVersion()
        );

        Map<String, Object> response =
                meFeignClient.createOrder(projectKey, "Bearer " + token, body);

        return Map.of(
                "orderId", response.get("id"),
                "orderState", response.get("orderState")
        );
    }

    // ---------- PRIVATE HELPERS ----------

    private Map<String, Object> updateMyCart(
            CartUpdateRequest request,
            String actionType) {

        String token = tokenService.getAdminAccessToken();

        Map<String, Object> action = new HashMap<>();
        action.put("action", actionType);

        if ("addLineItem".equals(actionType)) {
            action.put("productId", request.getProductId());
            action.put("quantity", request.getQuantity());
        }

        Map<String, Object> body = Map.of(
                "version", request.getVersion(),
                "actions", List.of(action)
        );

        Map<String, Object> cart =
                meFeignClient.updateMyCart(
                        projectKey,
                        request.getProductId(),
                        "Bearer " + token,
                        body
                );

        return mapCartResponse(cart);
    }

    private Map<String, Object> mapCartResponse(Map<String, Object> cart) {

        return Map.of(
                "cartId", cart.get("id"),
                "version", cart.get("version"),
                "totalPrice", ((Map<?, ?>) cart.get("totalPrice")).get("centAmount"),
                "lineItems", cart.get("lineItems")
        );
    }
}
