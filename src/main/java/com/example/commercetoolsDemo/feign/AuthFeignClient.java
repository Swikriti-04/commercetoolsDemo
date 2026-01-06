package com.example.commercetoolsDemo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "auth-client",
        url = "${ct.authUrl}"
)
public interface AuthFeignClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> getToken(
            @RequestHeader("Authorization") String basicAuth,
            @RequestParam("grant_type") String grantType,
            @RequestParam("scope") String scope
    );
}
