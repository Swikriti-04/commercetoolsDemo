package com.example.commercetoolsDemo.controller;

import com.example.commercetoolsDemo.dto.request.CartUpdateRequest;
import com.example.commercetoolsDemo.dto.request.CreateOrderRequest;
import com.example.commercetoolsDemo.service.MeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeController.class)
@AutoConfigureMockMvc(addFilters = false)
class MeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeService meService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void getActiveCart_success() throws Exception {
        when(meService.getActiveCart())
                .thenReturn(Map.of(
                        "cartId", "cart-1",
                        "version", 1,
                        "totalPrice", 1000,
                        "lineItems", List.of()
                ));

        mockMvc.perform(get("/me/carts/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value("cart-1"))
                .andExpect(jsonPath("$.totalPrice").value(1000));
    }



    @Test
    void addLineItem_success() throws Exception {
        CartUpdateRequest request = CartUpdateRequest.builder()
                .productId("prod-1")
                .quantity(2L)
                .version(1L)
                .build();

        when(meService.addLineItem(any()))
                .thenReturn(Map.of(
                        "cartId", "cart-1",
                        "version", 1L,
                        "totalPrice", 1000,
                        "lineItems", List.of()
                ));


        mockMvc.perform(post("/me/carts/line-items")
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
                .country("IN")
                .version(2L)
                .build();

        when(meService.setShippingAddress(any()))
                .thenReturn(Map.of(
                        "cartId", "cart-1",
                        "version", 1L,
                        "totalPrice", 1000,
                        "lineItems", List.of()
                ));


        mockMvc.perform(post("/me/carts/shipping-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").isNumber());
    }


    @Test
    void createOrder_success() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .cartId("cart-1")
                .version(3L)
                .build();

        when(meService.createOrder(any()))
                .thenReturn(Map.of(
                        "orderId", "order-1",
                        "orderState", "Confirmed"
                ));

        mockMvc.perform(post("/me/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("order-1"))
                .andExpect(jsonPath("$.orderState").value("Confirmed"));
    }
}
