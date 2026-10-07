package com.cupom_api.application;

import com.cupom_api.application.domain.Coupon;

import java.math.BigDecimal;
import java.time.Instant;

public interface CreateCouponUseCase {
    Coupon execute(
            String code,
            String description,
            BigDecimal discountValue,
            Instant expirationDate,
            boolean published
    );
}

