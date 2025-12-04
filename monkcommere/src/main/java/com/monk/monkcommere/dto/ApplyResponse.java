package com.monk.monkcommere.dto;

// sends the discount results back to the user
public class ApplyResponse {

    private double cartTotal;
    private double discount;
    private double finalPayable;
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

