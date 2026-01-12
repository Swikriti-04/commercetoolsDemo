package com.example.commercetoolsDemo.service;
import com.commercetools.api.models.cart.*;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.feign.AdminFeignClient;
import com.example.commercetoolsDemo.mapper.CartOrderMapper;
import com.example.commercetoolsDemo.model.CartResponse;
import com.example.commercetoolsDemo.model.OrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public CartResponse createCart(CartDraft request) {
        Cart cart = adminFeignClient.createCart(projectKey, request);
        return CartOrderMapper.toCartResponse(cart);
    }


    public CartResponse addLineItem(
            String cartId,
            Long version,
            LineItemDraft lineItemDraft
    ) {

        CartUpdateAction addLineItemAction;


        if (StringUtils.hasText(lineItemDraft.getSku())) {
            addLineItemAction =
                    CartUpdateAction.addLineItemBuilder()
                            .sku(lineItemDraft.getSku())
                            .quantity(lineItemDraft.getQuantity())
                            .build();
        }

        else {
            addLineItemAction =
                    CartUpdateAction.addLineItemBuilder()
                            .productId(lineItemDraft.getProductId())
                            .variantId(lineItemDraft.getVariantId())
                            .quantity(lineItemDraft.getQuantity())
                            .build();
        }

        CartUpdate update = CartUpdate.builder()
                .version(version)
                .actions(addLineItemAction)
                .build();

        Cart cart = adminFeignClient.updateCart(projectKey, cartId, update);
        return CartOrderMapper.toCartResponse(cart);
    }

    public CartResponse updateCart(String cartId, CartUpdate update) {
        Cart cart = adminFeignClient.updateCart(projectKey, cartId, update);
        return CartOrderMapper.toCartResponse(cart);
    }

    public OrderResponse createOrder(OrderFromCartDraft request) {
        Order order = adminFeignClient.createOrder(projectKey, request);
        return CartOrderMapper.toOrderResponse(order);
    }
}
