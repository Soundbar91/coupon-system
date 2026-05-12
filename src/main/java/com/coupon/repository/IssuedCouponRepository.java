package com.coupon.repository;

import org.springframework.data.repository.Repository;

import com.coupon.entity.IssuedCoupon;

public interface IssuedCouponRepository extends Repository<IssuedCoupon, Long> {

    boolean existsByCouponIdAndUserId(Long couponId, Long userId);

    Integer countByCouponId(Long couponId);

    IssuedCoupon save(IssuedCoupon issuedCoupon);
}
