package com.coupon.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OUT_OF_STOCK_COUPON(HttpStatus.CONFLICT, "쿠폰 재고가 소진되었습니다."),
    DUPLICATE_ISSUED_COUPON(HttpStatus.CONFLICT, "이미 발급받은 쿠폰입니다."),
    NOT_FOUND_COUPON(HttpStatus.NOT_FOUND, "존재하지 않은 쿠폰입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
