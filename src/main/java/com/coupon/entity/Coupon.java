package com.coupon.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import com.coupon.enums.CouponType;
import com.coupon.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotEmpty
    @Enumerated(value = STRING)
    @Column(name = "coupon_type")
    private CouponType couponType;

    @NotEmpty
    @Enumerated(value = STRING)
    @Column(name = "status")
    private Status status;

    @NotNull
    @Column(name = "discount_value")
    private Integer discountValue;

    @NotNull
    @Column(name = "min_order_amount")
    private Integer minOrderAmount;

    @NotNull
    @Column(name = "max_discount_amount")
    private Integer maxDiscountAmount;

    @NotNull
    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @NotNull
    @Column(name = "issued_quantity")
    private Integer issuedQuantity;

    @NotNull
    @Column(name = "valid_days")
    private Integer validDays;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Builder
    private Coupon(
        Long id,
        String name,
        String description,
        CouponType couponType,
        Status status,
        Integer discountValue,
        Integer minOrderAmount,
        Integer maxDiscountAmount,
        Integer totalQuantity,
        Integer issuedQuantity,
        Integer validDays,
        LocalDateTime startDate,
        LocalDateTime endDate
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.couponType = couponType;
        this.status = status;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = issuedQuantity;
        this.validDays = validDays;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer calcRemainingQuantity() {
        return this.totalQuantity - this.issuedQuantity;
    }

    public Boolean isAvailable() {
        return this.totalQuantity - this.issuedQuantity > 0;
    }

    public Boolean isNotAvailable() {
        return !this.isAvailable();
    }

    public void increaseIssuedQuantity() {
        this.issuedQuantity++;
    }
}
