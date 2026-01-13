package com.example.commercetoolsDemo.config;
import com.example.commercetoolsDemo.service.TokenService;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
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
            log.debug("USING TOKEN = {}", accessToken);

            requestTemplate.header("Authorization", "Bearer " + accessToken);
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
