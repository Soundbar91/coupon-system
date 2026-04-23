package com.coupon;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "coupon_usage_history")
@NoArgsConstructor(access = PROTECTED)
public class CouponUsageHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "issued_coupon_id")
    IssuedCoupon issuedCoupon;

    @NotNull
    @Column(name = "user_id")
    Long userId;

    @NotNull
    @Column(name = "order_id")
    Long orderId;

    @NotNull
    @Column(name = "discount_amount")
    Integer discountAmount;

    @NotNull
    @Column(name = "used_at")
    LocalDateTime usedAt;

    @Builder
    private CouponUsageHistory(
        Long id,
        IssuedCoupon issuedCoupon,
        Long userId,
        Long orderId,
        Integer discountAmount,
        LocalDateTime usedAt
    ) {
        this.id = id;
        this.issuedCoupon = issuedCoupon;
        this.userId = userId;
        this.orderId = orderId;
        this.discountAmount = discountAmount;
        this.usedAt = usedAt;
    }
}
