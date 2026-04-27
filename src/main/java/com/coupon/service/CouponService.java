package com.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.dto.AvailableCouponResponse;
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
            throw new IllegalArgumentException("이미 발급받은 쿠폰입니다");
        }

        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("couponId : " + couponId));

        if (coupon.isNotAvailable()) {
            throw new IllegalArgumentException("쿠폰 재고가 소진되었습니다.");
        }

        IssuedCoupon issuedCoupon = issuedCouponRepository.save(IssuedCoupon.create(coupon, userId));
        return IssuedCouponResponse.from(issuedCoupon);
    }
}
