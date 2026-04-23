package com.coupon;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    public List<AvailableCouponResponse> getAvailableCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
            .filter(Coupon::isAvailable)
            .map(AvailableCouponResponse::from)
            .toList();
    }
}
