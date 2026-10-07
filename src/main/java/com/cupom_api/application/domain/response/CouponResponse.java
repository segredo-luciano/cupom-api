package com.cupom_api.application.domain.response;

import com.cupom_api.application.domain.Coupon;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CouponResponse(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        Instant expirationDate,
        String status,
        boolean published,
        boolean redeemed
) {

    public static CouponResponse from(Coupon coupon) {

        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getStatus().name(),
                coupon.isPublished(),
                coupon.isRedeemed()
        );
    }
}
