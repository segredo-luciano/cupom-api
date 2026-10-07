package com.cupom_api.application.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.cupom_api.application.domain.enums.CouponEnum;
import com.cupom_api.application.exeption.InvalidCouponException;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {

    private UUID id;
    private String code;
    private String description;
    private BigDecimal discountValue;
    private Instant expirationDate;
    private CouponEnum status;
    private boolean published;
    private boolean redeemed;

    public static Coupon create(
            String code,
            String description,
            BigDecimal discountValue,
            Instant expirationDate,
            boolean published) {

        validateCode(code);
        validateDescription(description);
        validateDiscount(discountValue);
        validateExpiration(expirationDate);

        Coupon coupon = new Coupon();

        coupon.id = UUID.randomUUID();
        coupon.code = sanitizeCode(code);
        coupon.description = description;
        coupon.discountValue = discountValue;
        coupon.expirationDate = expirationDate;
        coupon.status = CouponEnum.ACTIVE;
        coupon.published = published;
        coupon.redeemed = false;

        return coupon;
    }

    public void delete() {

        if (status == CouponEnum.DELETED) {
            throw new InvalidCouponException("Este cupom já foi excluído");
        }

        status = CouponEnum.DELETED;
    }

    private static void validateCode(String code) {

        if (code == null || code.isBlank()) {
            throw new InvalidCouponException("Favor inserir um código");
        }

        String sanitizedCode = sanitizeCode(code);

        if (sanitizedCode.length() != 6) {
            throw new InvalidCouponException(
                    "Código deve conter 6 caracteres"
            );
        }
    }

    private static String sanitizeCode(String code) {
        return code.replaceAll("[^a-zA-Z0-9]", "");
    }

    private static void validateDescription(String description) {

        if (description == null || description.isBlank()) {
            throw new InvalidCouponException("Descrição é obrigatória");
        }
    }

    private static void validateDiscount(BigDecimal discountValue) {

        if (discountValue == null) {
            throw new InvalidCouponException("Valor do desconto é obrigatório");
        }

        if (discountValue.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new InvalidCouponException(
                    "Valor do disconto deve ser maior ou igual a 0.5"
            );
        }
    }

    private static void validateExpiration(Instant expirationDate) {

        if (expirationDate == null) {
            throw new InvalidCouponException("Data de expiração é obrigatória");
        }

        if (!expirationDate.isAfter(Instant.now())) {
            throw new InvalidCouponException(
                    "Cupom não pode ser criado com data de expiração no passado"
            );
        }
    }
}
