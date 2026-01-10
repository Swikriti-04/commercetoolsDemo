package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateCartRequest;
import com.example.commercetoolsDemo.dto.request.CreateOrderRequest;
import com.example.commercetoolsDemo.feign.AdminFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminFeignClient adminFeignClient;

    @Value("${ct.projectKey}")
    private String projectKey;

   

    public Object getCart(String cartId) {
        log.info("Fetching cart with id: {}", cartId);
        return adminFeignClient.getCart(projectKey, cartId);
    }

    public Object createCart(CreateCartRequest request) {
        log.info("Creating cart for customer");
        return adminFeignClient.createCart(projectKey, request);
    }

    public Object deleteCart(String cartId, Long version) {
        log.info("Deleting cart: {}, version: {}", cartId, version);
        return adminFeignClient.deleteCart(projectKey, cartId, version);
    }



    public Object addLineItem(String cartId, CartUpdateRequest request) {
        log.info("Adding line item to cart: {}", cartId);
        return adminFeignClient.updateCart(projectKey, cartId, request);
    }


    public Object addShippingAddress(String cartId, Long version, CartUpdateRequest.Address address) {

        log.info("Setting shipping address for cart: {}", cartId);

        CartUpdateRequest.Action action =
                CartUpdateRequest.Action.builder()
                        .action("setShippingAddress")
                        .address(address)
                        .build();

        return adminFeignClient.updateCart(
                projectKey,
                cartId,
                CartUpdateRequest.builder()
                        .version(version)
                        .actions(List.of(action))
                        .build()
        );
    }



    public Object getShippingMethods(String cartId) {
        log.info("Fetching shipping methods for cart: {}", cartId);
        return adminFeignClient.getShippingMethods(projectKey, cartId);
    }

    public Object setShippingMethod(String cartId, Long version, String shippingMethodId) {

        log.info("Setting shipping method for cart: {}", cartId);

        CartUpdateRequest.Action action =
                CartUpdateRequest.Action.builder()
                        .action("setShippingMethod")
                        .shippingMethod(
                                CartUpdateRequest.ShippingMethod.builder()
                                        .typeId("shipping-method")
                                        .id(shippingMethodId)
                                        .build()
                        )
                        .build();

        return adminFeignClient.updateCart(
                projectKey,
                cartId,
                CartUpdateRequest.builder()
                        .version(version)
                        .actions(List.of(action))
                        .build()
        );
    }


    public Object createOrder(String cartId, Long version) {

        log.info("Creating order from cart: {}", cartId);

        CreateOrderRequest request =
                CreateOrderRequest.builder()
                        .id(cartId)
                        .version(version)
                        .build();

        return adminFeignClient.createOrder(projectKey, request);
    }
}
