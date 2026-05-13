package com.coupon.service;

import static com.coupon.common.exception.ErrorCode.DUPLICATE_ISSUED_COUPON;
import static com.coupon.common.exception.ErrorCode.OUT_OF_STOCK_COUPON;
import static com.coupon.common.exception.ErrorCode.SERVER_ERROR;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import com.coupon.common.exception.CustomException;
import com.coupon.entity.Coupon;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisService {

    private static final String ISSUED_COUNT_KEY = "coupon:%d:issued-count";
    private static final String ISSUED_USERS_KEY = "coupon:%d:issued-users";
    private static final int ISSUE_SUCCESS = 1;
    private static final int ISSUE_DUPLICATE = -1;
    private static final int ISSUE_OUT_OF_STOCK = -2;

    private final StringRedisTemplate stringRedisTemplate;
    private final DefaultRedisScript<Long> couponIssueScript;
    private final DefaultRedisScript<Long> couponIssueRollbackScript;

    public void reserve(Coupon coupon, Long userId) {
        Long issueResult = stringRedisTemplate.execute(
            couponIssueScript,
            getRedisKeys(coupon.getId()),
            String.valueOf(userId),
            String.valueOf(coupon.getTotalQuantity())
        );

        validateIssueResult(issueResult);
    }

    public void rollback(Long couponId, Long userId) {
        stringRedisTemplate.execute(
            couponIssueRollbackScript,
            getRedisKeys(couponId),
            String.valueOf(userId)
        );
    }

    private void validateIssueResult(Long issueResult) {
        switch (issueResult.intValue()) {
            case ISSUE_DUPLICATE -> throw CustomException.of(DUPLICATE_ISSUED_COUPON);
            case ISSUE_OUT_OF_STOCK -> throw CustomException.of(OUT_OF_STOCK_COUPON);
            case ISSUE_SUCCESS -> {
            }
            default -> throw CustomException.of(SERVER_ERROR);
        }
    }

    private List<String> getRedisKeys(Long couponId) {
        return List.of(
            ISSUED_COUNT_KEY.formatted(couponId),
            ISSUED_USERS_KEY.formatted(couponId)
        );
    }
}
