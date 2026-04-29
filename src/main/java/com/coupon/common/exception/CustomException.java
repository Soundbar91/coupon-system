package com.coupon.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static CustomException of(ErrorCode errorCode) {
        return new CustomException(errorCode);
    }
}
