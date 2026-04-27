package com.coupon.dto;

import java.time.LocalDateTime;

import com.coupon.entity.IssuedCoupon;

public record IssuedCouponResponse(
    Long issuedCouponId,
    Long couponId,
    String couponName,
    String status,
    LocalDateTime issuedAt,
    LocalDateTime expiredAt
) {
    public static IssuedCouponResponse from(IssuedCoupon issuedCoupon) {
        return new IssuedCouponResponse(
            issuedCoupon.getId(),
            issuedCoupon.getCoupon().getId(),
            issuedCoupon.getCoupon().getName(),
            issuedCoupon.getStatus().name(),
            issuedCoupon.getIssuedAt(),
            issuedCoupon.getExpiredAt()
        );
    }
}
