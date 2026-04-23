package com.coupon;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<AvailableCouponResponse>>> getAvailableCoupons() {
        List<AvailableCouponResponse> response = couponService.getAvailableCoupons();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
