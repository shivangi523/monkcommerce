package com.monk.monkcommere.dto;

/**
 * This DTO is used when a new coupon is created through the API (POST /coupons).
 *
 * It carries all the input fields the user must send while creating a coupon:
 * - coupon code  (like "CART30", "PROD10")
 * - coupon type  (cartwise, productwise, bxgy)
 * - details      (JSON string describing rules for that coupon)
 * - active  (whether the coupon is currently usable)
 *
 * The backend converts this DTO into a Coupon entity which is then saved in the database.
 */
public class CouponRequest {

    // Example: "CART30"
    private String code;

    // Example: "cartwise"
    private String type;

    /**
     * JSON string containing coupon rules.
     *
     * Examples:
     *  - Cart-wise:   {"minAmount":500, "discount":50}
     *  - Product-wise: {"product_id":101, "discount":20}
     *  - BxGy:        {"productId":101, "buyQty":3, "getQty":1}
     */
    private String details;

    // Whether this coupon is currently enabled or not
    private boolean active;

    // getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
