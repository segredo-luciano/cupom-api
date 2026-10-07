package com.cupom_api.application.impl;

import com.cupom_api.application.DeleteCouponUseCase;
import com.cupom_api.application.domain.Coupon;
import com.cupom_api.application.domain.exeption.InvalidCouponException;
import com.cupom_api.application.CouponRepository;

import java.util.UUID;

public class DeleteCouponUseCaseImpl implements DeleteCouponUseCase {

    private final CouponRepository couponRepository;

    public DeleteCouponUseCaseImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon execute(UUID id) {

        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new InvalidCouponException("Cupom não encontrado com o identificador "+id));

        coupon.delete();

        return couponRepository.save(coupon);
    }
}
