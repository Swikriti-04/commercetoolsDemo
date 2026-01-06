package com.example.commercetoolsDemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
