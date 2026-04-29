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

    public static <T> ApiResponse<String> fail(String message) {
        return new ApiResponse<>(
            FALSE,
            message
        );
    }
}
