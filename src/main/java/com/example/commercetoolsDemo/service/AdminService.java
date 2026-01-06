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

        Map<String, Object> result = new HashMap<>();
        result.put("customerId", response.get("id"));
        result.put("email", response.get("email"));
        return result;
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

        Map<String, Object> cartRef = new HashMap<>();
        cartRef.put("typeId", "cart");
        cartRef.put("id", request.getCartId());

        Map<String, Object> body = new HashMap<>();
        body.put("cart", cartRef);
        body.put("version", request.getVersion());

        Map<String, Object> response =
                adminFeignClient.createOrder(projectKey, body);

        // ✅ NULL-SAFE RESPONSE
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", response.get("id"));
        result.put("orderState", response.get("orderState"));

        if (response.get("orderNumber") != null) {
            result.put("orderNumber", response.get("orderNumber"));
        }

        return result;
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
                action.put("variantId", request.getVariantId());
                action.put("quantity", request.getQuantity());
            }

            case "setShippingAddress" -> {
                Map<String, Object> address = new HashMap<>();
                address.put("streetName", request.getStreetName());
                address.put("city", request.getCity());
                address.put("state", request.getState());
                address.put("postalCode", request.getPostalCode());
                address.put("country", request.getCountry());
                action.put("address", address);
            }

            case "setShippingMethod" -> {
                Map<String, Object> shippingMethod = new HashMap<>();
                shippingMethod.put("id", request.getShippingMethodId());
                shippingMethod.put("typeId", "shipping-method");
                action.put("shippingMethod", shippingMethod);
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

        Map<String, Object> result = new HashMap<>();
        result.put("cartId", cart.get("id"));
        result.put("version", cart.get("version"));

        if (cart.get("totalPrice") instanceof Map<?, ?> totalPrice) {
            result.put("totalPrice", totalPrice.get("centAmount"));
        }

        result.put("lineItems", cart.get("lineItems"));
        return result;
    }
}
