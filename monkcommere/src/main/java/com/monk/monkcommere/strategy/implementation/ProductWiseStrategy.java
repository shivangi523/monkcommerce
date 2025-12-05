package com.monk.monkcommere.strategy.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CartItemDto;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.strategy.CouponStrategy;
import org.springframework.stereotype.Component;

@Component
public class ProductWiseStrategy implements CouponStrategy {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isApplicable(CartDto cart, Coupon coupon) {
        try {
            JsonNode details = objectMapper.readTree(coupon.getDetails());
            long productId = details.get("product_id").asLong();

            // check ifthis product exists in cart
            return cart.getItems().stream()
                    .anyMatch(item -> item.getProductId() == productId);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public double calculateDiscount(CartDto cart, Coupon coupon) {
        try {
            JsonNode details = objectMapper.readTree(coupon.getDetails());

            long productId = details.get("product_id").asLong();
            double discountPercent = details.get("discount").asDouble();

            double discountAmount = 0.0;

            for (CartItemDto item : cart.getItems()) {
                if (item.getProductId() == productId) {
                    double itemTotal = item.getPrice() * item.getQuantity();

                    // % discount
                    discountAmount += (itemTotal * discountPercent) / 100.0;
                }
            }

            return discountAmount;

        } catch (Exception e) {
            return 0.0;
        }
    }
}
