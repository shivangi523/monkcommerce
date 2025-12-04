package com.monk.monkcommere.dto;

import java.util.List;

// the entire cart(entire cart products)
public class CartDto {

    private List<CartItemDto> items;

    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }

    // Useful helper: total cart amount = sum(price * quantity)
    public double getTotal() {
        return items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }
}

