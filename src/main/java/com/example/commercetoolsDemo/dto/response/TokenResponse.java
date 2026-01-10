package com.example.commercetoolsDemo.dto.response;

import lombok.Data;

@Data
public class TokenResponse {
    private String access_token;
    private Integer expires_in;
}
