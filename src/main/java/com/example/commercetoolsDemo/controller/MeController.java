package com.example.commercetoolsDemo.controller;

import com.example.commercetoolsDemo.dto.request.CreateCartRequest;
import com.example.commercetoolsDemo.service.MeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {

    private final MeService meService;

    @GetMapping("/cart")
    public Object getMyCarts(
            @RequestHeader("Authorization") String token
    ) {
        return meService.getMyCarts(token);
    }

    @PostMapping("/cart")
    public Object createMyCart(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateCartRequest body
    ) {
        return meService.createMyCart(token, body);
    }

    @DeleteMapping("/cart/{id}")
    public Object deleteMyCart(
            @PathVariable String id,
            @RequestParam Long version,
            @RequestHeader("Authorization") String token
    ) {
        return meService.deleteMyCart(id, version, token);
    }
}
