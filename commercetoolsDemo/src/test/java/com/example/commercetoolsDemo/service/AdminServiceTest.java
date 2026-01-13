package com.example.commercetoolsDemo.service;

import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.example.commercetoolsDemo.feign.AdminFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminFeignClient adminFeignClient;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(adminService, "projectKey", "test");
    }

    @Test
    void createCustomer_success() {
        Customer customer = mock(Customer.class);
        when(adminFeignClient.createCustomer(anyString(), any()))
                .thenReturn(customer);

        assertNotNull(adminService.createCustomer(mock(CustomerDraft.class)));
    }

    @Test
    void createOrder_success() {
        Order order = mock(Order.class);
        when(adminFeignClient.createOrder(anyString(), any()))
                .thenReturn(order);

        assertNotNull(adminService.createOrder(mock(OrderFromCartDraft.class)));
    }
}
