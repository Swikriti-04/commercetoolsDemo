package com.example.commercetoolsDemo.service;

import com.example.commercetoolsDemo.dto.request.CreateCartRequest;
import com.example.commercetoolsDemo.feign.MeFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeService {

    private final MeFeignClient meFeignClient;

    @Value("${ct.projectKey}")
    private String projectKey;

    public Object getMyCarts(String token) {
        return meFeignClient.getMyCarts(projectKey, token);
    }

    public Object createMyCart(String token, CreateCartRequest body) {
        return meFeignClient.createMyCart(projectKey, token, body);
    }

    public Object deleteMyCart(String id, Long version, String token) {
        return meFeignClient.deleteMyCart(projectKey, id, version, token);
    }
}
