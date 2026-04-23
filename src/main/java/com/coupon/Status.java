package com.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACTIVE,
    INACTIVE,
    EXHAUSTED,
    EXPIRED,
    ;
}
