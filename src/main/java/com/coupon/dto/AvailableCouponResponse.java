package com.coupon.dto;

import java.time.LocalDateTime;

import com.coupon.entity.Coupon;

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
            coupon.getId(),
            coupon.getName(),
            coupon.getCouponType().name(),
            coupon.getDiscountValue(),
            coupon.getTotalQuantity(),
            coupon.calcRemainingQuantity(),
            coupon.getStartDate(),
            coupon.getEndDate()
        );
    }
}
