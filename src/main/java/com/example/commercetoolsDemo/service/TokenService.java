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
        String basicAuth = buildBasicAuthHeader();

        TokenResponse response = authFeignClient.getToken(
                basicAuth,
                "client_credentials",
                getProperty("ct.scope")
        );

        return response.getAccess_token();
    }

    public String getCustomerAccessToken() {
        String basicAuth = buildBasicAuthHeader();

        TokenResponse response = authFeignClient.getCustomerToken(
                basicAuth,
                "password",
                getProperty("ct.customer.email"),
                getProperty("ct.customer.password"),
                getProperty("ct.scope")
        );

        return response.getAccess_token();
    }

    private String buildBasicAuthHeader() {
        String credentials =
                getProperty("ct.clientId") + ":" + getProperty("ct.clientSecret");

        return "Basic " + Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    private String getProperty(String key) {
        return environment.getProperty(key, "");
    }
}
