package com.coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.common.dto.ApiResponse;
import com.coupon.dto.AvailableCouponResponse;
import com.coupon.dto.CouponStockResponse;
import com.coupon.dto.IssuedCouponResponse;
import com.coupon.service.CouponService;

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

    @PostMapping("/{couponId}/issue")
    public ResponseEntity<ApiResponse<IssuedCouponResponse>> issueCoupon(
        @PathVariable Long couponId,
        @RequestBody Long userId
    ) {
        IssuedCouponResponse response = couponService.issueCoupon(couponId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{couponId}/stock")
    public ResponseEntity<ApiResponse<CouponStockResponse>> getCouponStock(
        @PathVariable Long couponId
    ) {
        CouponStockResponse response = couponService.getCouponStock(couponId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
