package com.cupom_api.application;

import com.cupom_api.application.domain.Coupon;

import java.util.UUID;

public interface DeleteCouponUseCase {

    Coupon execute(UUID id);
}
