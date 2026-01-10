package com.example.commercetoolsDemo.service;
import com.commercetools.api.models.cart.*;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.feign.AdminFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class AdminService {

    private final AdminFeignClient adminFeignClient;

    @Value("${ct.projectKey}")
    private String projectKey;

    public AdminService(AdminFeignClient adminFeignClient) {
        this.adminFeignClient = adminFeignClient;
    }

    public Customer createCustomer(CustomerDraft request) {
        return adminFeignClient.createCustomer(projectKey, request);
    }

    public Cart createCart(CartDraft request) {
        return adminFeignClient.createCart(projectKey, request);
    }

    public Cart updateCart(String cartId, CartUpdate update) {
        return adminFeignClient.updateCart(projectKey, cartId, update);
    }

    public Order createOrder(OrderFromCartDraft request) {
        return adminFeignClient.createOrder(projectKey, request);
    }
}
