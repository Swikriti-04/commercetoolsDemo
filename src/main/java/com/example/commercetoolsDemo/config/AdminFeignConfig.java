package com.example.commercetoolsDemo.config;

import com.example.commercetoolsDemo.service.TokenService;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminFeignConfig {

    private final TokenService tokenService;

    public AdminFeignConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public RequestInterceptor adminRequestInterceptor() {
        return requestTemplate -> {
            String accessToken = tokenService.getAdminAccessToken();
            System.out.println("USING TOKEN = " + accessToken);

            requestTemplate.header("Authorization", "Bearer " + accessToken);
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
