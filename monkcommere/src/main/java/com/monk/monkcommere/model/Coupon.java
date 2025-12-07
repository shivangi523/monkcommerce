package com.monk.monkcommere.model;

import jakarta.persistence.*;

/**
 * Represents a coupon in the system.
 *
 * A coupon can be of different types such as:
 * - cartwise  → discount applied on entire cart
 * - productwise → discount applied only on selected products
 * - bxgy → buy X get Y type offers
 *
 * All the conditions and rules for the coupon are stored inside
 * the "details" field as a JSON string. This makes the structure flexible,
 * because different coupon types may require different rules.
 *
 * Example of details JSON:
 *   {"minAmount":500, "discount":50}
 *   {"productId": 10, "discountPercent": 20}
 *
 * The coupon can also be activated or deactivated using the "active" field.
 */
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique coupon code (example: "CART80", "PROD10").
     */
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * Type of the coupon.
     * Expected values: "cartwise", "productwise", "bxgy".
     */
    @Column(nullable = false)
    private String type;

    /**
     * Coupon rules stored as a JSON string.
     * Each coupon type has its own rule structure.
     */
    @Column(columnDefinition = "TEXT")
    private String details;

    /**
     * Status of coupon: active or inactive.
     */
    private boolean active = true;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
