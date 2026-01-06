package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.request.*;
import com.example.commercetoolsDemo.feign.AdminFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    private final AdminFeignClient adminFeignClient;

    @Value("${ct.projectKey}")
    private String projectKey;


    public AdminService(AdminFeignClient adminFeignClient) {
        this.adminFeignClient = adminFeignClient;
    }


    public Map<String, Object> createCustomer(CreateCustomerRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("email", request.getEmail());
        body.put("password", request.getPassword());
        body.put("firstName", request.getFirstName());
        body.put("lastName", request.getLastName());

        Map<String, Object> response =
                adminFeignClient.createCustomer(projectKey, body);

        return Map.of(
                "customerId", response.get("id"),
                "email", response.get("email")
        );
    }


    public Map<String, Object> createCart(CreateCartRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("customerId", request.getCustomerId());
        body.put("currency", request.getCurrency());
        body.put("country", request.getCountry());

        Map<String, Object> response =
                adminFeignClient.createCart(projectKey, body);

        return mapCartResponse(response);
    }


    public Map<String, Object> addLineItem(String cartId, CartUpdateRequest request) {
        return updateCart(cartId, request, "addLineItem");
    }


    public Map<String, Object> setShippingAddress(String cartId, CartUpdateRequest request) {
        return updateCart(cartId, request, "setShippingAddress");
    }

    public Map<String, Object> setShippingMethod(String cartId, CartUpdateRequest request) {
        return updateCart(cartId, request, "setShippingMethod");
    }


    public Map<String, Object> createOrder(CreateOrderRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("id", request.getCartId());
        body.put("version", request.getCartVersion());

        Map<String, Object> response =
                adminFeignClient.createOrder(projectKey, body);

        return Map.of(
                "orderId", response.get("id"),
                "orderNumber", response.get("orderNumber"),
                "orderState", response.get("orderState")
        );
    }

    private Map<String, Object> updateCart(
            String cartId,
            CartUpdateRequest request,
            String actionType) {

        Map<String, Object> action = new HashMap<>();
        action.put("action", actionType);

        switch (actionType) {
            case "addLineItem" -> {
                action.put("productId", request.getProductId());
                action.put("quantity", request.getQuantity());
            }
            case "setShippingAddress" -> {
                action.put("address", Map.of(
                        "streetName", request.getStreetName(),
                        "city", request.getCity(),
                        "state", request.getState(),
                        "postalCode", request.getPostalCode(),
                        "country", request.getCountry()
                ));
            }
            case "setShippingMethod" -> {
                action.put("shippingMethod", Map.of(
                        "id", request.getShippingMethodId(),
                        "typeId", "shipping-method"
                ));
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("version", request.getVersion());
        body.put("actions", List.of(action));

        Map<String, Object> response =
                adminFeignClient.updateCart(projectKey, cartId, body);

        return mapCartResponse(response);
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
