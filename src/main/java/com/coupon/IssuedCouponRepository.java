package com.coupon;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface IssuedCouponRepository extends Repository<IssuedCoupon, Long> {

    Optional<IssuedCoupon> findByCouponIdAndUserId(Long couponId, Long userId);

    IssuedCoupon save(IssuedCoupon issuedCoupon);
}
