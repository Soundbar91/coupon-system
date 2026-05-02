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
    NOT_AVAILABLE_COUPON(HttpStatus.BAD_REQUEST, "발급 가능한 쿠폰이 아닙니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내에 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
