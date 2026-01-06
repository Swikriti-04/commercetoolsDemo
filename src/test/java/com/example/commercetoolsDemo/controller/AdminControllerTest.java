package com.example.commercetoolsDemo.controller;

import com.example.commercetoolsDemo.dto.request.*;
import com.example.commercetoolsDemo.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createCustomer_success() throws Exception {
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .email("test@test.com")
                .password("pass")
                .firstName("Test")
                .lastName("User")
                .build();

        when(adminService.createCustomer(any()))
                .thenReturn(Map.of(
                        "customerId", "cust-1",
                        "email", "test@test.com"
                ));

        mockMvc.perform(post("/admin/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("cust-1"))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    // ---------- CREATE CART ----------

    @Test
    void createCart_success() throws Exception {
        CreateCartRequest request = CreateCartRequest.builder()
                .customerId("cust-1")
                .currency("INR")
                .country("IN")
                .build();

        when(adminService.createCart(any()))
                .thenReturn(Map.of(
                        "cartId", "cart-1",
                        "version", 1
                ));

        mockMvc.perform(post("/admin/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value("cart-1"))
                .andExpect(jsonPath("$.version").isNumber());
    }


    @Test
    void addLineItem_success() throws Exception {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .productId("prod-1")
                .variantId(1)
                .quantity(1L)
                .version(1L)
                .build();

        when(adminService.addLineItem(eq("cart-1"), any()))
                .thenReturn(Map.of(
                        "cartId", "cart-1",
                        "version", 2
                ));

        mockMvc.perform(post("/admin/carts/cart-1/line-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").isNumber());
    }



    @Test
    void setShippingAddress_success() throws Exception {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .streetName("MG Road")
                .city("Bangalore")
                .state("KA")
                .postalCode("560001")
                .country("IN")
                .version(2L)
                .build();

        when(adminService.setShippingAddress(eq("cart-1"), any()))
                .thenReturn(Map.of(
                        "cartId", "cart-1",
                        "version", 3
                ));

        mockMvc.perform(post("/admin/carts/cart-1/shipping-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").isNumber());
    }



    @Test
    void setShippingMethod_success() throws Exception {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .shippingMethodId("ship-1")
                .version(3L)
                .build();

        when(adminService.setShippingMethod(eq("cart-1"), any()))
                .thenReturn(Map.of(
                        "cartId", "cart-1",
                        "version", 4
                ));

        mockMvc.perform(post("/admin/carts/cart-1/shipping-method")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").isNumber());
    }



    @Test
    void createOrder_success() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .cartId("cart-1")
                .version(4L)
                .build();

        when(adminService.createOrder(any()))
                .thenReturn(Map.of(
                        "orderId", "order-1",
                        "orderState", "Confirmed"
                ));

        mockMvc.perform(post("/admin/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("order-1"))
                .andExpect(jsonPath("$.orderState").value("Confirmed"));
    }
}
