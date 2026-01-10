package com.example.commercetoolsDemo.service;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartBuilder;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderBuilder;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.feign.MeFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeServiceTest {

    @Mock
    private MeFeignClient meFeignClient;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private MeService meService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(meService, "projectKey", "test-project");
    }

    @Test
    void getActiveCart_success() {

        when(tokenService.getCustomerAccessToken())
                .thenReturn("mock-token");

        Cart cart = CartBuilder.of()
                .id("cart-1")
                .build();

        when(meFeignClient.getActiveCart(anyString(), anyString()))
                .thenReturn(cart);

        Cart result = meService.getActiveCart();

        assertEquals("cart-1", result.getId());
    }

    @Test
    void createOrder_success() {

        when(tokenService.getCustomerAccessToken())
                .thenReturn("mock-token");

        Order order = OrderBuilder.of()
                .id("order-1")
                .build();

        when(meFeignClient.createOrder(anyString(), anyString(), any()))
                .thenReturn(order);

        Order result = meService.createOrder(any());

        assertEquals("order-1", result.getId());
    }
}
