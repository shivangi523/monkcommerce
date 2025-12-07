package com.monk.monkcommere.dto;

/**
 * DTO used to describe discount rules for a Product-wise coupon.
 *
 * This object represents the discount that should be applied
 * to a specific product when a product-wise coupon is used.
 *
 * example:
 * {
 *   "productId": 101,
 *   "discount": 20
 * }
 *
 * Apply 20% discount on product with ID 101.</p>
 */
public class ProductWiseDetailsDto {

    /** The ID of the product on which discount should be applied. */
    private Long productId;

    /** Percentage discount to apply (e.g., 20 for 20% off). */
    private double discountPercentage;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
