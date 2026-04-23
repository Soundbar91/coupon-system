package com.coupon;

import java.time.LocalDateTime;

public record AvailableCouponResponse(
    Long couponId,
    String name,
    String couponType,
    Integer discountValue,
    Integer totalQuantity,
    Integer remainingQuantity,
    LocalDateTime startDate,
    LocalDateTime endDate
) {
    public static AvailableCouponResponse from(Coupon coupon) {
        return new AvailableCouponResponse(
            coupon.id,
            coupon.name,
            coupon.couponType.name(),
            coupon.discountValue,
            coupon.totalQuantity,
            coupon.calcRemainingQuantity(),
            coupon.starDate,
            coupon.endDate
        );
    }
}
