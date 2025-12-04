package com.monk.monkcommere.controller;

import com.monk.monkcommere.dto.ApplyResponse;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CouponRequest;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.service.CouponService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService service;


    public CouponController(CouponService service) {
        this.service = service;
    }

    // STEP 1: Create a new coupon
    // POST coupons
    @PostMapping
    public Coupon createCoupon(@RequestBody CouponRequest req) {
        return service.createCoupon(req);
    }

    // STEP 2: Get all coupons
    // GET /coupons
    @GetMapping
    public List<Coupon> getAllCoupons() {
        return service.getAllCoupons();
    }

    // STEP 3: Get a single coupon by ID
    // GET coupons/{id}
    @GetMapping("/{id}")
    public Coupon getCoupon(@PathVariable Long id) {
        return service.getCouponById(id);
    }

    // STEP 4: Check if coupon is applicable on given cart
    // POST /coupons/{id}/applicable
    @PostMapping("/{id}/applicable")
    public boolean checkApplicable(
            @PathVariable Long id,
            @RequestBody CartDto cart
    ) {
        return service.checkApplicable(id, cart);
    }

    // STEP 5: Apply coupon (CART-WISE currently)
    // POST /coupons/{id}/apply
    @PostMapping("/{id}/apply")
    public ApplyResponse applyCoupon(
            @PathVariable Long id,
            @RequestBody CartDto cart
    ) {
        return service.applyCartWise(id, cart);
    }
}

