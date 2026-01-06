package com.example.commercetoolsDemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequest {


    private Long version;


    private String productId;
    private Long quantity;
    private Integer variantId;

    private String streetName;
    private String city;
    private String state;
    private String postalCode;
    private String country;


    private String shippingMethodId;
}
