package com.coupon;

import java.time.LocalDateTime;

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
            issuedCoupon.id,
            issuedCoupon.coupon.id,
            issuedCoupon.coupon.name,
            issuedCoupon.status.name(),
            issuedCoupon.issuedAt,
            issuedCoupon.expiredAt
        );
    }
}
