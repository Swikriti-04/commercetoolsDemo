package com.example.commercetoolsDemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequest {

    private String customerId;
    private String currency;
    private String country;
}
