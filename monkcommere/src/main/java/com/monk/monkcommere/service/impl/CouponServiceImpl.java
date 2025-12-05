package com.monk.monkcommere.service.impl;

import com.monk.monkcommere.dto.ApplyResponse;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CouponRequest;
import com.monk.monkcommere.exceptions.NotFoundException;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.repository.CouponRepository;
import com.monk.monkcommere.service.CouponService;
import com.monk.monkcommere.strategy.implementation.CartWiseStrategy;
import com.monk.monkcommere.strategy.implementation.ProductWiseStrategy;
import com.monk.monkcommere.strategy.implementation.BxGyStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository repo;
    private final CartWiseStrategy cartWiseStrategy;
    private final ProductWiseStrategy productWiseStrategy;
    private final BxGyStrategy bxGyStrategy;

    public CouponServiceImpl(
            CouponRepository repo,
            CartWiseStrategy cartWiseStrategy,
            ProductWiseStrategy productWiseStrategy,
            BxGyStrategy bxGyStrategy
    ) {
        this.repo = repo;
        this.cartWiseStrategy = cartWiseStrategy;
        this.productWiseStrategy = productWiseStrategy;
        this.bxGyStrategy = bxGyStrategy;
    }

    @Override
    public Coupon createCoupon(CouponRequest req) {
        Coupon coupon = new Coupon();
        coupon.setCode(req.getCode());
        coupon.setType(req.getType());
        coupon.setDetails(req.getDetails());
        coupon.setActive(true);
        return repo.save(coupon);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return repo.findAll();
    }

    @Override
    public Coupon getCouponById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
    }

    @Override
    public boolean checkApplicable(Long id, CartDto cart) {
        Coupon coupon = getCouponById(id);

        switch (coupon.getType().toLowerCase()) {
            case "cartwise":
                return cartWiseStrategy.isApplicable(cart, coupon);
            case "productwise":
                return productWiseStrategy.isApplicable(cart, coupon);
            case "bxgy":
                return bxGyStrategy.isApplicable(cart, coupon);
            default:
                return false;
        }
    }

    @Override
    public boolean checkApplicableBxGy(Long id, CartDto cart) {
        Coupon coupon = getCouponById(id);
        return bxGyStrategy.isApplicable(cart, coupon);
    }



    @Override
    public Coupon updateCoupon(Long id, CouponRequest req) {
        Coupon existing = getCouponById(id); // fetch old coupon

        // update allowed fields
        existing.setCode(req.getCode());
        existing.setType(req.getType());
        existing.setDetails(req.getDetails());
        existing.setActive(req.isActive());

        return repo.save(existing);
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon coupon = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Coupon not found with ID: " + id));

        repo.delete(coupon);
    }

    @Override
    public List<ApplyResponse> getApplicableCoupons(CartDto cart) {

        List<Coupon> coupons = repo.findAll();
        List<ApplyResponse> applicable = new ArrayList<>();

        for (Coupon coupon : coupons) {

            double discount = 0;

            // pick strategy manually
            if (coupon.getType().equalsIgnoreCase("cartwise")) {
                if (cartWiseStrategy.isApplicable(cart, coupon)) {
                    discount = cartWiseStrategy.calculateDiscount(cart, coupon);
                }
            }
            else if (coupon.getType().equalsIgnoreCase("productwise")) {
                if (productWiseStrategy.isApplicable(cart, coupon)) {
                    discount = productWiseStrategy.calculateDiscount(cart, coupon);
                }
            }
            else if (coupon.getType().equalsIgnoreCase("bxgy")) {
                if (bxGyStrategy.isApplicable(cart, coupon)) {
                    discount = bxGyStrategy.calculateDiscount(cart, coupon);
                }
            }


            // If discount > 0 => coupon is applicable
            if (discount > 0) {
                ApplyResponse res = new ApplyResponse();
                res.setCartTotal(cart.getTotal());
                res.setDiscount(discount);
                res.setFinalPayable(cart.getTotal() - discount);
                res.setMessage("Applicable coupon: " + coupon.getCode());
                applicable.add(res);
            }
        }

        return applicable;
    }

    @Override
    public ApplyResponse applyCoupon(Long id, CartDto cart) {

        Coupon coupon = getCouponById(id);

        double discount = 0;

        switch (coupon.getType().toLowerCase()) {

            case "cartwise":
                if (cartWiseStrategy.isApplicable(cart, coupon)) {
                    discount = cartWiseStrategy.calculateDiscount(cart, coupon);
                }
                break;

            case "productwise":
                if (productWiseStrategy.isApplicable(cart, coupon)) {
                    discount = productWiseStrategy.calculateDiscount(cart, coupon);
                }
                break;

            case "bxgy":
                if (bxGyStrategy.isApplicable(cart, coupon)) {
                    discount = bxGyStrategy.calculateDiscount(cart, coupon);
                }
                break;

            default:
                throw new RuntimeException("Unknown coupon type: " + coupon.getType());
        }

        ApplyResponse res = new ApplyResponse();
        res.setCartTotal(cart.getTotal());
        res.setDiscount(discount);
        res.setFinalPayable(cart.getTotal() - discount);
        res.setMessage(discount > 0
                ? "Applied " + coupon.getType() + " coupon"
                : "Coupon not applicable");

        return res;
    }




}
