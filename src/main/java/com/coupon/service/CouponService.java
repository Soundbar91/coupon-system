package com.coupon.service;

import static com.coupon.common.exception.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.common.exception.CustomException;
import com.coupon.dto.AvailableCouponResponse;
import com.coupon.dto.CouponStockResponse;
import com.coupon.dto.IssuedCouponResponse;
import com.coupon.entity.Coupon;
import com.coupon.entity.IssuedCoupon;
import com.coupon.enums.Status;
import com.coupon.repository.CouponRepository;
import com.coupon.repository.IssuedCouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponIssueRedisService couponIssueRedisService;

    public List<AvailableCouponResponse> getAvailableCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
            .filter(Coupon::isAvailable)
            .map(AvailableCouponResponse::from)
            .toList();
    }

    @Transactional
    public IssuedCouponResponse issueCoupon(Long couponId, Long userId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> CustomException.of(NOT_FOUND_COUPON));

        if (!coupon.isActive() || !coupon.isIssuingPeriod()) {
            throw CustomException.of(NOT_AVAILABLE_COUPON);
        }

        couponIssueRedisService.reserve(coupon, userId);

        try {
            int updatedCount = couponRepository.increaseIssuedQuantityIfAvailable(couponId, Status.ACTIVE, LocalDateTime.now());
            if (updatedCount != 1) {
                throw CustomException.of(OUT_OF_STOCK_COUPON);
            }

            IssuedCoupon issuedCoupon = issuedCouponRepository.save(IssuedCoupon.create(coupon, userId));
            issuedCouponRepository.flush();
            return IssuedCouponResponse.from(issuedCoupon);
        } catch (DataIntegrityViolationException exception) {
            couponIssueRedisService.rollback(couponId, userId);
            throw CustomException.of(DUPLICATE_ISSUED_COUPON);
        }
    }

    public CouponStockResponse getCouponStock(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> CustomException.of(NOT_FOUND_COUPON));

        return CouponStockResponse.from(coupon, coupon.getIssuedQuantity());
    }
}
