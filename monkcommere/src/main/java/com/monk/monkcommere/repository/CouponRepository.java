package com.monk.monkcommere.repository;

import com.monk.monkcommere.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // find coupon by code
    Coupon findByCode(String code);
}

