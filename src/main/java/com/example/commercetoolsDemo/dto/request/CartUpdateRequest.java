package com.example.commercetoolsDemo.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartUpdateRequest {

    private Long version;
    private List<Action> actions;

    // ===================== ACTION =====================
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Action {

        private String action;

        // 🔹 addLineItem
        private String productId;
        private String sku;
        private Integer variantId;
        private Long quantity;

        // 🔹 setShippingAddress (direct address)
        private Address address;

        // 🔹 setShippingAddress / setBillingAddress (by id)
        private String addressId;
        private String addressKey;

        // 🔹 setShippingMethod (REQUIRED before order)
        private ShippingMethod shippingMethod;
    }

    // ===================== ADDRESS =====================
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Address {

        private String key;
        private String country;       // REQUIRED (ISO 3166-1)
        private String state;
        private String city;
        private String streetName;
        private String streetNumber;
        private String postalCode;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String building;
        private String apartment;
    }

    // ===================== SHIPPING METHOD =====================
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ShippingMethod {

        private String typeId; // must be "shipping-method"
        private String id;     // shippingMethodId
    }
}
