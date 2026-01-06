package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.feign.AuthFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Service
public class TokenService {

    @Value("${ct.clientId}")
    private String clientId;

    @Value("${ct.clientSecret}")
    private String clientSecret;

    @Value("${ct.scopes}")
    private String scope;

    private final AuthFeignClient authFeignClient;

    private String accessToken;
    private Instant expiryTime;

    public TokenService(AuthFeignClient authFeignClient) {
        this.authFeignClient = authFeignClient;
    }

    public synchronized String getAdminAccessToken() {

        if (accessToken == null || Instant.now().isAfter(expiryTime)) {
            fetchToken();
        }
        return accessToken;
    }

    private void fetchToken() {

        String basicAuth = "Basic " + Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret)
                        .getBytes(StandardCharsets.UTF_8));

        Map<String, Object> response =
                authFeignClient.getToken(
                        basicAuth,
                        "client_credentials",
                        scope
                );

        this.accessToken = (String) response.get("access_token");
        Integer expiresIn = (Integer) response.get("expires_in");

        this.expiryTime = Instant.now().plusSeconds(expiresIn - 60);
    }
}
