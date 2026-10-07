package com.cupom_api.application;

import com.cupom_api.application.domain.Coupon;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {
    Coupon save(Coupon coupon);

    Optional<Coupon> findById(UUID id);
}
