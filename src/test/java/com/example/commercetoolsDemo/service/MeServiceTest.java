package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateOrderRequest;
import com.example.commercetoolsDemo.feign.MeFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(tokenService.getAdminAccessToken()).thenReturn("token");
    }

    @Test
    void getActiveCart_success() {
        Map<String, Object> cart = Map.of(
                "id", "cart-1",
                "version", 1L,
                "totalPrice", Map.of("centAmount", 1000),
                "lineItems", List.of()
        );

        when(meFeignClient.getActiveCart(eq("test-project"), anyString()))
                .thenReturn(cart);

        Map<String, Object> result = meService.getActiveCart();

        assertEquals("cart-1", result.get("cartId"));
        assertEquals(1000, result.get("totalPrice"));
    }

    @Test
    void addLineItem_success() {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .productId("prod-1")
                .quantity(2L)
                .version(1L)
                .build();

        when(meFeignClient.updateMyCart(anyString(), anyString(), anyString(), anyMap()))
                .thenReturn(Map.of(
                        "id", "cart-1",
                        "version", 2L,
                        "totalPrice", Map.of("centAmount", 2000),
                        "lineItems", List.of()
                ));

        Map<String, Object> result = meService.addLineItem(request);

        assertEquals(2L, result.get("version"));
    }

    @Test
    void createOrder_success() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .cartId("cart-1")
                .version(2L)
                .build();

        when(meFeignClient.createOrder(anyString(), anyString(), anyMap()))
                .thenReturn(Map.of(
                        "id", "order-1",
                        "orderState", "Confirmed"
                ));

        Map<String, Object> result = meService.createOrder(request);

        assertEquals("order-1", result.get("orderId"));
        assertEquals("Confirmed", result.get("orderState"));
    }
}
