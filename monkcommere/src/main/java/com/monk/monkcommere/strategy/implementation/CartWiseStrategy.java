package com.monk.monkcommere.strategy.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.strategy.CouponStrategy;
import org.springframework.stereotype.Component;

/**
 * Strategy implementation for handling Cart-wise coupons.
 *
 * Cart-wise coupons apply a flat discount to the entire cart when the
 * total cart amount crosses a minimum threshold.
 *
 * Example coupon details JSON:
 *  {
 *      "minAmount": 500,
 *      "discount": 80
 *  }
 *
 * How this strategy works:
 * - We first check if the cart total is greater than or equal to "minAmount".
 * - If yes, we return the fixed "discount" value.
 * - If not applicable or if the coupon JSON is invalid, discount = 0.
 *
 * This class only contains the logic for this specific coupon type.
 * By keeping it separate from other strategies, the project becomes
 * easier to maintain and extend when new coupon types are added.
 */
@Component
public class CartWiseStrategy implements CouponStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Checks whether the cart satisfies the minimum amount required
     * to apply this coupon.
     *
     * @param cart   The cart sent in the request.
     * @param coupon The coupon containing JSON rules.
     * @return true if cartTotal >= minAmount, otherwise false.
     */
    @Override
    public boolean isApplicable(CartDto cart, Coupon coupon) {
        try {
            // Parse the JSON rules from coupon.details
            JsonNode json = mapper.readTree(coupon.getDetails());

            double minAmount = json.get("minAmount").asDouble();
            double cartTotal = cart.getTotal();

            return cartTotal >= minAmount;

        } catch (Exception e) {
            // If JSON is invalid, coupon is not applicable
            return false;
        }
    }

    /**
     * Calculates the discount amount defined for this coupon.
     * The discount is applied only if the coupon is applicable.
     *
     * @param cart   The cart for which discount is to be calculated.
     * @param coupon The coupon configuration (with discount rules).
     * @return discount amount if applicable, otherwise 0.
     */
    @Override
    public double calculateDiscount(CartDto cart, Coupon coupon) {
        try {
            JsonNode json = mapper.readTree(coupon.getDetails());
            double discount = json.get("discount").asDouble();

            if (isApplicable(cart, coupon)) {
                return discount;
            }

        } catch (Exception e) {
            // Invalid JSON — zero discount
            return 0;
        }

        return 0;
    }
}
