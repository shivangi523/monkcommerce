package com.monk.monkcommere.strategy;

import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.model.Coupon;

public interface CouponStrategy {

    // checking if coupon can be applied or not
    boolean isApplicable(CartDto cart, Coupon coupon);

    // calculate discount
    double calculateDiscount(CartDto cart, Coupon coupon);
}

