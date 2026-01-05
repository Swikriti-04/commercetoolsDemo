package com.example.commercetoolsDemo.feign;

import com.example.commercetoolsDemo.dto.request.CreateCartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "meClient",
        url = "${ct.apiUrl}"
)
public interface MeFeignClient {

    @GetMapping("/{projectKey}/me/carts")
    Object getMyCarts(
            @PathVariable String projectKey,
            @RequestHeader("Authorization") String token
    );

    @PostMapping(
            value = "/{projectKey}/me/carts",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Object createMyCart(
            @PathVariable String projectKey,
            @RequestHeader("Authorization") String token,
            @RequestBody CreateCartRequest body
    );

    @DeleteMapping("/{projectKey}/me/carts/{id}")
    Object deleteMyCart(
            @PathVariable String projectKey,
            @PathVariable String id,
            @RequestParam("version") Long version,
            @RequestHeader("Authorization") String token
    );
}
