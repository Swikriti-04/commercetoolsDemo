package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.response.TokenResponse;
import com.example.commercetoolsDemo.feign.AuthFeignClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class TokenService {

    private final AuthFeignClient authFeignClient;
    private final Environment environment;

    public TokenService(AuthFeignClient authFeignClient, Environment environment) {
        this.authFeignClient = authFeignClient;
        this.environment = environment;
    }


    public String getAdminAccessToken() {
        TokenResponse response = authFeignClient.getToken(
                buildBasicAuthHeader(),
                "client_credentials",
                getRequiredProperty("ct.scopes")
        );
        return response.getAccess_token();
    }


    public String getCustomerAccessToken() {
        TokenResponse response = authFeignClient.getCustomerToken(
                buildBasicAuthHeader(),
                "password",
                getRequiredProperty("ct.customer.email"),
                getRequiredProperty("ct.customer.password"),
                getRequiredProperty("ct.scopes")
        );
        return response.getAccess_token();
    }

    private String buildBasicAuthHeader() {
        String credentials =
                getRequiredProperty("ct.clientId") + ":" +
                        getRequiredProperty("ct.clientSecret");

        return "Basic " + Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    private String getRequiredProperty(String key) {
        String value = environment.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value;
    }
}
