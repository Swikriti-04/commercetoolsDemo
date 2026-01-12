package com.example.commercetoolsDemo.mapper;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.order.Order;
import com.example.commercetoolsDemo.model.CartResponse;
import com.example.commercetoolsDemo.model.OrderResponse;
import com.example.commercetoolsDemo.model.Price;

import java.util.List;
import java.util.stream.Collectors;

public class CartOrderMapper {

    private CartOrderMapper() {

    }

    public static CartResponse toCartResponse(Cart cart) {
        CartResponse response = new CartResponse();

        response.setId(cart.getId());
        response.setVersion(cart.getVersion());


        if (cart.getTotalPrice() != null) {
            Price price = new Price();
            price.setCentAmount(cart.getTotalPrice().getCentAmount());
            response.setTotalPrice(price);
        }

        if (cart.getLineItems() != null) {
            List<Object> items =
                    cart.getLineItems()
                            .stream()
                            .map(CartOrderMapper::toSimpleLineItem)
                            .collect(Collectors.toList());

            response.setLineItems(items);
        }

        return response;
    }

    private static Object toSimpleLineItem(LineItem item) {
        return new SimpleLineItem(
                item.getId(),
                item.getProductId(),
                item.getQuantity()
        );
    }



    public static OrderResponse toOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());

        if (order.getOrderState() != null) {
            response.setOrderState(order.getOrderState().name());
        }

        return response;
    }


    private static class SimpleLineItem {
        public String id;
        public String productId;
        public Long quantity;

        public SimpleLineItem(String id, String productId, Long quantity) {
            this.id = id;
            this.productId = productId;
            this.quantity = quantity;
        }
    }
}
