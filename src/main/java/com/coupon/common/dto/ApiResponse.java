package com.coupon.common.dto;

import static java.lang.Boolean.*;

public record ApiResponse<T>(
    Boolean success,
    T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
            TRUE,
            data
        );
    }
}
