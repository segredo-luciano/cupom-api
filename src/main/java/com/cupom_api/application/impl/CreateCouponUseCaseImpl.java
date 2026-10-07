package com.cupom_api.application.impl;

import com.cupom_api.application.CreateCouponUseCase;
import com.cupom_api.application.domain.Coupon;
import com.cupom_api.application.CouponRepository;

import java.math.BigDecimal;
import java.time.Instant;

public class CreateCouponUseCaseImpl implements CreateCouponUseCase {

    private final CouponRepository couponRepository;

    public CreateCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon execute(
            String code,
            String description,
            BigDecimal discountValue,
            Instant expirationDate,
            boolean published) {

        Coupon coupon = Coupon.create(
                code,
                description,
                discountValue,
                expirationDate,
                published
        );

        return couponRepository.save(coupon);
    }
}
