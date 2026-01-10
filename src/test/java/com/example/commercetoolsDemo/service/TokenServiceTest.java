package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.response.TokenResponse;
import com.example.commercetoolsDemo.feign.AuthFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private AuthFeignClient authFeignClient;

    @Mock
    private Environment environment;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setup() {
        when(environment.getProperty("ct.clientId", ""))
                .thenReturn("id");
        when(environment.getProperty("ct.clientSecret", ""))
                .thenReturn("secret");
        when(environment.getProperty("ct.scope", ""))
                .thenReturn("scope");
    }

    @Test
    void fetchesNewToken_whenExpired() {

        TokenResponse response = new TokenResponse();
        response.setAccess_token("token");
        response.setExpires_in(3600);

        when(authFeignClient.getToken(
                anyString(),
                anyString(),
                anyString()
        )).thenReturn(response);

        String token = tokenService.getAdminAccessToken();

        assertEquals("token", token);
    }
}

