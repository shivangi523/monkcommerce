package com.monk.monkcommere.service.impl;

import com.monk.monkcommere.dto.ApplyResponse;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CouponRequest;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.repository.CouponRepository;
import com.monk.monkcommere.service.CouponService;
import com.monk.monkcommere.strategy.CouponStrategy;
import com.monk.monkcommere.strategy.implementation.CartWiseStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository repo;
    private final CartWiseStrategy cartWiseStrategy; // Only Cart-wise for now

    public CouponServiceImpl(CouponRepository repo, CartWiseStrategy cartWiseStrategy) {
        this.repo = repo;
        this.cartWiseStrategy = cartWiseStrategy;
    }

    @Override
    public Coupon createCoupon(CouponRequest req) {
        Coupon coupon = new Coupon();
        coupon.setCode(req.getCode());
        coupon.setType(req.getType());
        coupon.setDetails(req.getDetails());
        coupon.setActive(true);

        return repo.save(coupon);   // return actual saved coupon
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return repo.findAll();
    }

    @Override
    public boolean checkApplicable(Long id, CartDto cart) {
        Coupon coupon = repo.findById(id).orElseThrow();
        return cartWiseStrategy.isApplicable(cart, coupon);
    }

    @Override
    public ApplyResponse applyCartWise(Long id, CartDto cart) {
        Coupon coupon = repo.findById(id).orElseThrow();

        double discount = cartWiseStrategy.calculateDiscount(cart, coupon);

        ApplyResponse res = new ApplyResponse();
        res.setCartTotal(cart.getTotal());
        res.setDiscount(discount);
        res.setFinalPayable(cart.getTotal() - discount);
        res.setMessage(discount > 0 ? "Coupon applied" : "Not applicable");

        return res;
    }

    @Override
    public Coupon getCouponById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
    }
}


