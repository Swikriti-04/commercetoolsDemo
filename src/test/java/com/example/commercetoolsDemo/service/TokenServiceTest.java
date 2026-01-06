package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.feign.AuthFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @Mock
    private AuthFeignClient authFeignClient;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(tokenService, "clientId", "client");
        ReflectionTestUtils.setField(tokenService, "clientSecret", "secret");
        ReflectionTestUtils.setField(tokenService, "scope", "manage_project");
    }

    @Test
    void getAdminAccessToken_fetchesNewToken_whenExpired() {
        when(authFeignClient.getToken(anyString(), anyString(), anyString()))
                .thenReturn(Map.of(
                        "access_token", "new-token",
                        "expires_in", 3600
                ));

        String token = tokenService.getAdminAccessToken();

        assertEquals("new-token", token);
        verify(authFeignClient, times(1))
                .getToken(anyString(), eq("client_credentials"), anyString());
    }

    @Test
    void getAdminAccessToken_returnsCachedToken() {
        ReflectionTestUtils.setField(tokenService, "accessToken", "cached-token");
        ReflectionTestUtils.setField(tokenService, "expiryTime",
                Instant.now().plusSeconds(600));

        String token = tokenService.getAdminAccessToken();

        assertEquals("cached-token", token);
        verifyNoInteractions(authFeignClient);
    }
}
