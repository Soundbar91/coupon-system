package com.coupon.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.coupon.entity.Coupon;
import com.coupon.enums.Status;

import jakarta.persistence.LockModeType;

public interface CouponRepository extends Repository<Coupon, Long> {

    List<Coupon> findAll();

    Optional<Coupon> findById(Long id);

    @Query("""
            select c
            from Coupon c
            where c.id = :couponId
        """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Coupon> findByIdForUpdate(@Param("couponId") Long couponId);

    @Modifying(flushAutomatically = true)
    @Query("""
        update Coupon c
        set c.issuedQuantity = c.issuedQuantity + 1
        where c.id = :couponId
          and c.status = :status
          and c.startDate <= :now
          and c.endDate >= :now
          and c.issuedQuantity < c.totalQuantity
        """)
    int increaseIssuedQuantityIfAvailable(
        @Param("couponId") Long couponId,
        @Param("status") Status status,
        @Param("now") LocalDateTime now
    );
}
