package com.coupon.dto;

import com.coupon.entity.Coupon;

public record CouponStockResponse(
    Long couponId,
    Integer totalQuantity,
    Integer issuedQuantity,
    Integer remainingQuantity
) {
    public static CouponStockResponse from(Coupon coupon, Integer issuedQuantity) {
        return new CouponStockResponse(
            coupon.getId(),
            coupon.getTotalQuantity(),
            issuedQuantity,
            coupon.getTotalQuantity() - issuedQuantity
        );
    }
}
