package com.cupom_api.application.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateCouponRequest(

        @NotBlank
        String code,

        @NotBlank
        String description,

        @NotNull
        @DecimalMin("0.5")
        BigDecimal discountValue,

        @NotNull
        Instant expirationDate,

        boolean published
) {
}
