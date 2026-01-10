package com.example.commercetoolsDemo.controller;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartBuilder;
import com.commercetools.api.models.order.Order;
import com.example.commercetoolsDemo.service.MeService;
import com.example.commercetoolsDemo.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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


    @MockBean
    private TokenService tokenService;

    @Test
    void getActiveCart_success() throws Exception {

        when(tokenService.getCustomerAccessToken()).thenReturn("mock-token");

        Cart cart = CartBuilder.of()
                .id("cart-1")
                .build();

        when(meService.getActiveCart()).thenReturn(cart);

        mockMvc.perform(get("/me/carts/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("cart-1"));
    }

}
