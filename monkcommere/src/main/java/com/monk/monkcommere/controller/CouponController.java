package com.monk.monkcommere.controller;

import com.monk.monkcommere.dto.ApplyResponse;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CouponRequest;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.service.CouponService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing discount coupons.
 *
 * This controller exposes APIs to create, update, delete, and fetch coupons.
 * It also includes endpoints to determine coupon applicability and
 * apply coupons to a cart (cart-wise, product-wise, or BxGy).
 *
 */
@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    /**
     * Create a new coupon.
     *
     * @param request the coupon creation payload
     * @return the saved coupon
     */
    @PostMapping
    public Coupon createCoupon(@RequestBody CouponRequest request) {
        return service.createCoupon(request);
    }

    /**
     * Retrieve all coupons from the system.
     *
     * @return list of coupons
     */
    @GetMapping
    public List<Coupon> getAllCoupons() {
        return service.getAllCoupons();
    }

    /**
     * Retrieve a coupon by its ID.
     *
     * @param id coupon ID
     * @return coupon details
     */
    @GetMapping("/{id}")
    public Coupon getCouponById(@PathVariable Long id) {
        return service.getCouponById(id);
    }

    /**
     * Update an existing coupon.
     *
     * @param id  coupon ID
     * @param req updated coupon fields
     * @return updated coupon object
     */
    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable Long id, @RequestBody CouponRequest req) {
        return service.updateCoupon(id, req);
    }

    /**
     * Delete a coupon by its ID.
     *
     * @param id coupon ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    public String deleteCoupon(@PathVariable Long id) {
        service.deleteCoupon(id);
        return "Coupon deleted successfully with ID: " + id;
    }

    /**
     * Fetch all coupons that are applicable for the given cart.
     *
     * @param cart the cart details containing items and total amount
     * @return list of applicable coupons with their calculated discount
     */
    @PostMapping("/applicable-coupons")
    public List<ApplyResponse> getApplicableCoupons(@RequestBody CartDto cart) {
        return service.getApplicableCoupons(cart);
    }

    /**
     * Apply a coupon of ANY type (cart wise, product wise, bxgy) to the cart.
     *
     * @param id   coupon ID
     * @param cart cart request body
     * @return response containing discount and updated payable amount
     */
    @PostMapping("/apply-coupon/{id}")
    public ApplyResponse applyCoupon(
            @PathVariable Long id,
            @RequestBody CartDto cart
    ) {
        return service.applyCoupon(id, cart);
    }
}
