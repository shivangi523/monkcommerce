package com.monk.monkcommere.dto;

/**
 * Represents a single product inside the user's cart.
 *
 * This class holds the basic details needed for calculating discounts:
 * productId, price of the product, and how many units the user added.
 *
 * Example:
 * If a user adds 2 quantities of a product priced at ₹300 each,
 * then this object will store:
 * productId = 101,
 * price = 300,
 * quantity = 2
 *

 */
public class CartItemDto {

    /** Unique ID of the product (example: 101). */
    private Long productId;

    /** Price of a single unit of the product. */
    private double price;

    /** Quantity of this product added in the cart. */
    private int quantity;

    // Getters & Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
