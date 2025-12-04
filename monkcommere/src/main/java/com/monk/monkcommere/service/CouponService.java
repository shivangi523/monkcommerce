package com.monk.monkcommere.service;

import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CouponRequest;
import com.monk.monkcommere.dto.ApplyResponse;
import com.monk.monkcommere.model.Coupon;

import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponRequest req);  // POST coupons

    List<Coupon> getAllCoupons();
    // GET coupons

    boolean checkApplicable(Long id, CartDto cart);  // POST coupon by id

    ApplyResponse applyCartWise(Long id, CartDto cart);  // POST coupon by id

    Coupon getCouponById(Long id);
}


