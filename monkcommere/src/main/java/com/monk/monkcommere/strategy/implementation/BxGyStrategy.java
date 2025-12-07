package com.monk.monkcommere.strategy.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.strategy.CouponStrategy;
import org.springframework.stereotype.Component;

/**
 * Strategy implementation for handling "Buy X, Get Y" (BxGy) type coupons.
 *
 * This coupon works on a specific product. If the user buys a minimum quantity (buyQty),
 * they become eligible to get some quantity of the same product for free (getQty).
 *
 * example coupon JSON stored in the database:
 *
 * {
 *     "productId": 101,
 *     "buyQty": 3,
 *     "getQty": 1
 * }
 *
 * If a user purchases 6 units of product 101 and the rule is "Buy 3, Get 1",
 * the user gets 2 free units. The discount is calculated based on
 * the price of the free items.
 *
 * The logic in this class does two things:
 *
 * 1.Check applicability:Verifies that the required quantity of the target product exists in the cart.
 * 2.Calculate discount:Computes how many free units the user gets and calculates the total discount.

 */
@Component
public class BxGyStrategy implements CouponStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isApplicable(CartDto cart, Coupon coupon) {
        try {
            JsonNode json = mapper.readTree(coupon.getDetails());
            Long targetProductId = json.get("productId").asLong();
            int requiredQty = json.get("buyQty").asInt();

            // Coupon is applicable only if the cart contains enough quantity of the target product
            return cart.getItems().stream()
                    .anyMatch(item ->
                            item.getProductId().equals(targetProductId)
                                    && item.getQuantity() >= requiredQty
                    );

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

            // Find the product in the cart and calculate free items count
            return cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(targetProductId))
                    .findFirst()
                    .map(item -> {

                        // Determine how many full buy+free groups fit in purchased quantity
                        int applicableCycles = item.getQuantity() / (buyQty + getQty);

                        // Total discount = free units * price per unit
                        return applicableCycles * (getQty * item.getPrice());
                    })
                    .orElse(0.0);

        } catch (Exception e) {
            return 0;
        }
    }
}
