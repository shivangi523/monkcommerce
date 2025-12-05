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

    // 1. Create coupon
    @PostMapping
    public Coupon createCoupon(@RequestBody CouponRequest request) {
        return service.createCoupon(request);
    }

    // 2. Get all coupons
    @GetMapping
    public List<Coupon> getAllCoupons() {
        return service.getAllCoupons();
    }

    // 3. Get coupon by id
    @GetMapping("/{id}")
    public Coupon getCouponById(@PathVariable Long id) {
        return service.getCouponById(id);
    }

    // 4. Update coupon
    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable Long id, @RequestBody CouponRequest req) {
        return service.updateCoupon(id, req);
    }

    // 5. Delete coupon
    @DeleteMapping("/{id}")
    public String deleteCoupon(@PathVariable Long id) {
        service.deleteCoupon(id);
        return "Coupon deleted successfully with ID: " + id;
    }

    // 6. Get all applicable coupons for a cart
    @PostMapping("/applicable-coupons")
    public List<ApplyResponse> getApplicableCoupons(@RequestBody CartDto cart) {
        return service.getApplicableCoupons(cart);
    }

    // 7. Apply ANY coupon type (cartwise / productwise / bxgy)
    @PostMapping("/apply-coupon/{id}")
    public ApplyResponse applyCoupon(
            @PathVariable Long id,
            @RequestBody CartDto cart
    ) {
        return service.applyCoupon(id, cart);
    }
}
