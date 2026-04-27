package com.coupon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.coupon.entity.Coupon;

public interface CouponRepository extends Repository<Coupon, Long> {

    List<Coupon> findAll();

    Optional<Coupon> findById(Long id);
}
