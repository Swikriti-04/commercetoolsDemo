package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.request.*;
import com.example.commercetoolsDemo.feign.AdminFeignClient;
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
class AdminServiceTest {

    @Mock
    private AdminFeignClient adminFeignClient;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(adminService, "projectKey", "test-project");
    }

    @Test
    void createCustomer_success() {
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .email("test@test.com")
                .password("pass")
                .firstName("Test")
                .lastName("User")
                .build();

        Map<String, Object> feignResponse = Map.of(
                "id", "cust-123",
                "email", "test@test.com"
        );

        when(adminFeignClient.createCustomer(eq("test-project"), anyMap()))
                .thenReturn(feignResponse);

        Map<String, Object> result = adminService.createCustomer(request);

        assertEquals("cust-123", result.get("customerId"));
        assertEquals("test@test.com", result.get("email"));
        verify(adminFeignClient).createCustomer(eq("test-project"), anyMap());
    }

    @Test
    void createCart_success() {
        CreateCartRequest request = CreateCartRequest.builder()
                .customerId("cust-1")
                .currency("INR")
                .country("IN")
                .build();

        Map<String, Object> cartResponse = Map.of(
                "id", "cart-1",
                "version", 1L,
                "totalPrice", Map.of("centAmount", 5000),
                "lineItems", List.of()
        );

        when(adminFeignClient.createCart(eq("test-project"), anyMap()))
                .thenReturn(cartResponse);

        Map<String, Object> result = adminService.createCart(request);

        assertEquals("cart-1", result.get("cartId"));
        assertEquals(1L, result.get("version"));
        assertEquals(5000, result.get("totalPrice"));
    }

    @Test
    void addLineItem_success() {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .version(2L)
                .productId("prod-1")
                .variantId(1)
                .quantity(2L)
                .build();

        Map<String, Object> cartResponse = Map.of(
                "id", "cart-1",
                "version", 3L,
                "lineItems", List.of()
        );

        when(adminFeignClient.updateCart(eq("test-project"), eq("cart-1"), anyMap()))
                .thenReturn(cartResponse);

        Map<String, Object> result =
                adminService.addLineItem("cart-1", request);

        assertEquals("cart-1", result.get("cartId"));
        assertEquals(3L, result.get("version"));
    }

    @Test
    void setShippingAddress_success() {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .version(3L)
                .streetName("MG Road")
                .city("Bangalore")
                .state("KA")
                .postalCode("560001")
                .country("IN")
                .build();

        when(adminFeignClient.updateCart(eq("test-project"), anyString(), anyMap()))
                .thenReturn(Map.of("id", "cart-1", "version", 4L));

        Map<String, Object> result =
                adminService.setShippingAddress("cart-1", request);

        assertEquals("cart-1", result.get("cartId"));
        assertEquals(4L, result.get("version"));
    }

    @Test
    void setShippingMethod_success() {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .version(4L)
                .shippingMethodId("ship-1")
                .build();

        when(adminFeignClient.updateCart(eq("test-project"), anyString(), anyMap()))
                .thenReturn(Map.of("id", "cart-1", "version", 5L));

        Map<String, Object> result =
                adminService.setShippingMethod("cart-1", request);

        assertEquals(5L, result.get("version"));
    }

    @Test
    void createOrder_withOrderNumber() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .cartId("cart-1")
                .version(5L)
                .build();

        when(adminFeignClient.createOrder(eq("test-project"), anyMap()))
                .thenReturn(Map.of(
                        "id", "order-1",
                        "orderState", "Confirmed",
                        "orderNumber", "ORD-123"
                ));

        Map<String, Object> result = adminService.createOrder(request);

        assertEquals("order-1", result.get("orderId"));
        assertEquals("Confirmed", result.get("orderState"));
        assertEquals("ORD-123", result.get("orderNumber"));
    }
}
