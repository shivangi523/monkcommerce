package com.monk.monkcommere.service;

import com.monk.monkcommere.dto.ApplyResponse;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CouponRequest;
import com.monk.monkcommere.model.Coupon;

import java.util.List;

/**
 * Service interface defining operations for managing and applying coupons.
 * This service supports multiple coupon types such as:
 *
 *     1. Cart wise discounts
 *     2. Product wise discounts
 *     3. BxGy (Buy X Get Y) offers
 *
 * It provides CRUD operations for coupons and for checking
 * coupon applicability and applying discounts on cart.
 */
public interface CouponService {

    /**
     * Creates a new coupon in the system.
     *
     * @param req the coupon request containing type, code, and discount details
     * @return the saved {@link Coupon} entity
     */
    Coupon createCoupon(CouponRequest req);

    /**
     * Retrieves all coupons stored in the system.
     *
     * @return list of all coupons
     */
    List<Coupon> getAllCoupons();

    /**
     * Fetches a coupon by its ID.
     *
     * @param id the coupon ID
     * @return the corresponding {@link Coupon}
     * @throws com.monk.monkcommere.exceptions.NotFoundException if coupon does not exist
     */
    Coupon getCouponById(Long id);

    /**
     * Checks whether a coupon (cartwise/productwise/bxgy) is applicable to the given cart.
     *
     * @param id   the coupon ID
     * @param cart the cart data
     * @return true if applicable, false otherwise
     */
    boolean checkApplicable(Long id, CartDto cart);

    /**
     * Specifically checks if a BxGy coupon can be applied to the cart.
     *
     * @param id   the coupon ID
     * @param cart the cart data
     * @return true if applicable, false otherwise
     */
    boolean checkApplicableBxGy(Long id, CartDto cart);

    /**
     * Updates an existing coupon with new values.
     *
     * @param id  the coupon ID to update
     * @param req the updated coupon request payload
     * @return the updated {@link Coupon}
     */
    Coupon updateCoupon(Long id, CouponRequest req);

    /**
     * Deletes a coupon from the system.
     *
     * @param id the coupon ID to delete
     */
    void deleteCoupon(Long id);

    /**
     * Retrieves all coupons that are applicable to the provided cart along with their discount values.
     *
     * @param cart the cart information
     * @return a list of responses showing applicable coupons and computed discounts
     */
    List<ApplyResponse> getApplicableCoupons(CartDto cart);

    /**
     * Applies any coupon type (cart-wise, product-wise, bxgy) to the cart.
     *
     * @param id   the coupon ID
     * @param cart the user cart
     * @return final cart totals including discount and payable amount
     */
    ApplyResponse applyCoupon(Long id, CartDto cart);
}
