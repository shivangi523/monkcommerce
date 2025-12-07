package com.monk.monkcommere.dto;

import java.util.List;

/**
 * DTO representing the entire shopping cart.
 *
 * This class contains a list of all items the user has added to the cart.
 * It also provides a helper method to calculate the total cart amount
 * based on item price and quantity.
 *
 * Example:
 * items = [
 *   { productId: 1, price: 200, quantity: 2 },
 *   { productId: 2, price: 150, quantity: 1 }
 * ]
 *
 * getTotal() → (200×2) + (150×1) = 550
 */
public class CartDto {

    /** List of individual items in the cart */
    private List<CartItemDto> items;

    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }

    /**
     * Calculates the total amount of the cart.
     * Formula: sum of (price × quantity) for each item.
     *
     * @return total cart value before any discount
     */
    public double getTotal() {
        return items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }
}
