package com.monk.monkcommere.dto;

/**
 * A response DTO used to return the result after a coupon is applied.
 *
 *This object mainly helps the frontend understand:
 *
 * What was the original cart total
 * How much discount was given
 * final amount the customer has to pay
 * A small message describing the result
 *

 */
public class ApplyResponse {

    /** Total price of the cart before applying the coupon */
    private double cartTotal;

    /** Discount amount calculated based on the coupon rules */
    private double discount;

    /** Final amount payable after subtracting the discount */
    private double finalPayable;

    /** Additional message (e.g., "Coupon applied", "Not applicable", etc.) */
    private String message;

    public double getCartTotal() { return cartTotal; }
    public void setCartTotal(double cartTotal) { this.cartTotal = cartTotal; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getFinalPayable() { return finalPayable; }
    public void setFinalPayable(double finalPayable) { this.finalPayable = finalPayable; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
