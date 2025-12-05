package com.monk.monkcommere.strategy.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.strategy.CouponStrategy;
import org.springframework.stereotype.Component;

@Component
public class BxGyStrategy implements CouponStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isApplicable(CartDto cart, Coupon coupon) {
        try {
            JsonNode json = mapper.readTree(coupon.getDetails());
            Long targetProductId = json.get("productId").asLong();
            int requiredQty = json.get("buyQty").asInt();

            return cart.getItems().stream()
                    .anyMatch(item -> item.getProductId().equals(targetProductId)
                            && item.getQuantity() >= requiredQty);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public double calculateDiscount(CartDto cart, Coupon coupon) {
        try {
            JsonNode json = mapper.readTree(coupon.getDetails());
            Long targetProductId = json.get("productId").asLong();
            int buyQty = json.get("buyQty").asInt();
            int getQty = json.get("getQty").asInt();

            return cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(targetProductId))
                    .findFirst()
                    .map(item -> {
                        int applicableCount = item.getQuantity() / (buyQty + getQty);
                        return applicableCount * (getQty * item.getPrice());
                    })
                    .orElse(0.0);

        } catch (Exception e) {
            return 0;
        }
    }
}

