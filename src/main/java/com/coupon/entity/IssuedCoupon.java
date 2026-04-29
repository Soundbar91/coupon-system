package com.coupon.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import com.coupon.enums.IssuedCouponStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    name = "issued_coupon",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_issued_coupon_coupon_user",
        columnNames = {"coupon_id", "user_id"}
    )
)
@NoArgsConstructor(access = PROTECTED)
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Enumerated(value = STRING)
    @Column(name = "status")
    private IssuedCouponStatus status;

    @NotNull
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @NotNull
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Builder
    private IssuedCoupon(
        Long id,
        Coupon coupon,
        Long userId,
        IssuedCouponStatus status,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        LocalDateTime usedAt
    ) {
        this.id = id;
        this.coupon = coupon;
        this.userId = userId;
        this.status = status;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.usedAt = usedAt;
    }

    public static IssuedCoupon create(Coupon coupon, Long userId) {
        coupon.increaseIssuedQuantity();

        return IssuedCoupon.builder()
            .coupon(coupon)
            .userId(userId)
            .status(IssuedCouponStatus.ISSUED)
            .issuedAt(LocalDateTime.now())
            .expiredAt(LocalDateTime.now().plusDays(coupon.getValidDays()))
            .build();
    }
}
