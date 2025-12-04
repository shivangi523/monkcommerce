package com.monk.monkcommere.dto;

// Represents one product in a cart
public class CartItemDto {

    private Long productId;  // eg:101
    private double price;    // eg:200
    private int quantity;    // eg: 1 or anythiing

    // getters & setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

