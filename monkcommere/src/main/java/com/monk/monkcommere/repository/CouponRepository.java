package com.monk.monkcommere.repository;

import com.monk.monkcommere.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for managing Coupon data.
 *
 * This interface gives us all the basic CRUD operations using Spring Data JPA,
 * so we don’t have to write SQL manually.
 *
 * Additional Queries:
 * - findByCode(): Helps check if a coupon exists with a given coupon code.
 *
 * Example usage:
 *   repo.findById(1);
 *   repo.findByCode("CART30");
 *
 * JpaRepository<Coupon, Long> means:
 *   Coupon  → Entity type the repository works with
 *   Long    → Type of the primary key (id)
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * Find a coupon using its unique code.
     * Useful when validating coupon codes before applying them.
     */
    Coupon findByCode(String code);
}
