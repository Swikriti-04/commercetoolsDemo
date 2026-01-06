package com.example.commercetoolsDemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequest {


    private Long version;


    private String productId;
    private Long quantity;


    private String streetName;
    private String city;
    private String state;
    private String postalCode;
    private String country;


    private String shippingMethodId;
}
