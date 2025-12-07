package com.monk.monkcommere.strategy;

import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.model.Coupon;

/**
 * Common for all coupon strategies.
 *
 * Each coupon type (cart-wise, product-wise, bxgy) follows this interface so that
 * the service layer can work with them in a uniform way.
 *
 * The idea behind using a strategy pattern is simple:
 * every coupon has different rules, so instead of putting all logic in one place,
 * each coupon type gets its own class that knows how to:
 *  - check if the coupon should be applied
 *  - calculate the discount amount
 *
 * This keeps the code clean and makes it easy to add new coupon types later.
 */
public interface CouponStrategy {

    /**
     * Checks whether the coupon is valid for the given cart.
     * Each strategy decides its own rules.
     *
     * @param cart   incoming cart data (total, items, etc.)
     * @param coupon coupon that needs to be validated
     * @return true if applicable, false otherwise
     */
    boolean isApplicable(CartDto cart, Coupon coupon);

    /**
     * Calculates the discount for the cart based on coupon rules.
     *
     * @param cart   incoming cart data
     * @param coupon applied coupon
     * @return discount amount (0 if not applicable)
     */
    double calculateDiscount(CartDto cart, Coupon coupon);
}
