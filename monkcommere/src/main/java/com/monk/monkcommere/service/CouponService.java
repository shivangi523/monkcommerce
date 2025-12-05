package com.monk.monkcommere.service;

import com.monk.monkcommere.dto.ApplyResponse;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CouponRequest;
import com.monk.monkcommere.model.Coupon;
import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponRequest req);

    List<Coupon> getAllCoupons();

    Coupon getCouponById(Long id);

    boolean checkApplicable(Long id, CartDto cart);

//    ApplyResponse applyCartWise(Long id, CartDto cart);
//
//    ApplyResponse applyProductWise(Long id, CartDto cart);

     boolean checkApplicableBxGy(Long id, CartDto cart);

//    ApplyResponse applyBxGy(Long id, CartDto cart);

    Coupon updateCoupon(Long id, CouponRequest req);

    void deleteCoupon(Long id);

    List<ApplyResponse> getApplicableCoupons(CartDto cart);

    ApplyResponse applyCoupon(Long id, CartDto cart); //applycartwise,roductwise k jagah yahi pe aaega , pahle alag bana li lakin document me unified mentioned hai




}




