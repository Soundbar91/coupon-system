package com.coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<Coupon, Long> {

    List<Coupon> findAll();

    Optional<Coupon> findById(Long id);
}
