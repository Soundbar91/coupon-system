package com.coupon.dto;

import jakarta.validation.constraints.NotNull;

public record IssueCouponRequest(
    @NotNull Long userId
) {
}
