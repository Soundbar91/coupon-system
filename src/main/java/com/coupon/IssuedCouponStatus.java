package com.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssuedCouponStatus {

    ISSUED,
    USED,
    EXPIRED,
    CANCELLED,
    ;
}
