package com.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponType {

    FIXED_AMOUNT,
    PERCENTAGE,
    ;
}
