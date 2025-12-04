package com.monk.monkcommere.strategy.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.strategy.CouponStrategy;
import org.springframework.stereotype.Component;

@Component
public class CartWiseStrategy implements CouponStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isApplicable(CartDto cart, Coupon coupon) {
        try {
            // {"minAmount":500,"discount":50}
            JsonNode json = mapper.readTree(coupon.getDetails());

            double minAmount = json.get("minAmount").asDouble();
            double cartTotal = cart.getTotal();

            return cartTotal >= minAmount;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public double calculateDiscount(CartDto cart, Coupon coupon) {
        try {
            JsonNode json = mapper.readTree(coupon.getDetails());
            double discount = json.get("discount").asDouble();

            if (isApplicable(cart, coupon)) {
                return discount;
            }
        }
        catch (Exception e) {
            return 0;
        }
        return 0;
    }
}

