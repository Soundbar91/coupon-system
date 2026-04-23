package com.coupon;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<Coupon, Long> {

    List<Coupon> findAll();
}
