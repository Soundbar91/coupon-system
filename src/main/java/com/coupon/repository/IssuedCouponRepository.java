package com.coupon.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.coupon.entity.IssuedCoupon;

public interface IssuedCouponRepository extends Repository<IssuedCoupon, Long> {

    Optional<IssuedCoupon> findByCouponIdAndUserId(Long couponId, Long userId);

    IssuedCoupon save(IssuedCoupon issuedCoupon);
}
