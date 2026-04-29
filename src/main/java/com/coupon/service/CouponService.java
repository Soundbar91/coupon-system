package com.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.common.exception.CustomException;
import com.coupon.common.exception.ErrorCode;
import com.coupon.dto.AvailableCouponResponse;
import com.coupon.dto.CouponStockResponse;
import com.coupon.entity.Coupon;
import com.coupon.repository.CouponRepository;
import com.coupon.entity.IssuedCoupon;
import com.coupon.repository.IssuedCouponRepository;
import com.coupon.dto.IssuedCouponResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    public List<AvailableCouponResponse> getAvailableCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
            .filter(Coupon::isAvailable)
            .map(AvailableCouponResponse::from)
            .toList();
    }

    public IssuedCouponResponse issueCoupon(Long couponId, Long userId) {
        if (issuedCouponRepository.findByCouponIdAndUserId(couponId, userId).isPresent()) {
            throw CustomException.of(ErrorCode.OUT_OF_STOCK_COUPON);
        }

        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("couponId : " + couponId));

        if (coupon.isNotAvailable()) {
            throw CustomException.of(ErrorCode.DUPLICATE_ISSUED_COUPON);
        }

        IssuedCoupon issuedCoupon = issuedCouponRepository.save(IssuedCoupon.create(coupon, userId));
        return IssuedCouponResponse.from(issuedCoupon);
    }

    public CouponStockResponse getCouponStock(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("couponId : " + couponId));
        Integer issuedCouponCount = issuedCouponRepository.countByCouponId(couponId);

        return CouponStockResponse.from(coupon, issuedCouponCount);
    }
}
