package com.example.commercetoolsDemo.feign;

import com.example.commercetoolsDemo.dto.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "auth-client", url = "${ct.authUrl}")
public interface AuthFeignClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenResponse getToken(
            @RequestHeader("Authorization") String basicAuth,
            @RequestParam("grant_type") String grantType,
            @RequestParam("scope") String scope
    );

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenResponse getCustomerToken(
            @RequestHeader("Authorization") String basicAuth,
            @RequestParam("grant_type") String grantType,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("scope") String scope
    );
}
