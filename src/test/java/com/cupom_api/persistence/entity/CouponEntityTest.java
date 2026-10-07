package com.cupom_api.persistence.entity;

import com.cupom_api.application.domain.Coupon;
import com.cupom_api.application.domain.enums.CouponEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CouponEntityTest {

    @Test
    void shouldConvertDomainToEntityAndBackToDomain() {
        UUID id = UUID.randomUUID();
        Instant expirationDate = Instant.now().plusSeconds(3600);

        Coupon coupon = Coupon.builder()
                .id(id)
                .code("ABC123")
                .description("Cupom de desconto")
                .discountValue(BigDecimal.TEN)
                .expirationDate(expirationDate)
                .status(CouponEnum.ACTIVE)
                .published(true)
                .redeemed(false)
                .build();

        CouponEntity entity = new CouponEntity(coupon);

        Coupon result = entity.toDomain();

        assertEquals(coupon.getId(), result.getId());
        assertEquals(coupon.getCode(), result.getCode());
        assertEquals(coupon.getDescription(), result.getDescription());
        assertEquals(coupon.getDiscountValue(), result.getDiscountValue());
        assertEquals(coupon.getExpirationDate(), result.getExpirationDate());
        assertEquals(coupon.getStatus(), result.getStatus());
        assertEquals(coupon.isPublished(), result.isPublished());
        assertEquals(coupon.isRedeemed(), result.isRedeemed());
    }

    @Test
    void shouldPreserveDeletedStatusWhenConvertingDomainToEntity() {
        Coupon coupon = Coupon.builder()
                .id(UUID.randomUUID())
                .code("ABC123")
                .description("Cupom de desconto")
                .discountValue(BigDecimal.TEN)
                .expirationDate(Instant.now().plusSeconds(3600))
                .status(CouponEnum.DELETED)
                .published(false)
                .redeemed(false)
                .build();

        CouponEntity entity = new CouponEntity(coupon);

        Coupon result = entity.toDomain();

        assertEquals(CouponEnum.DELETED, result.getStatus());
    }

    @Test
    void shouldPreserveAllFieldsWhenConvertingDeletedCoupon() {
        UUID id = UUID.randomUUID();
        Instant expirationDate = Instant.now().plusSeconds(3600);

        Coupon coupon = Coupon.builder()
                .id(id)
                .code("ABC123")
                .description("Cupom excluído")
                .discountValue(BigDecimal.valueOf(25.50))
                .expirationDate(expirationDate)
                .status(CouponEnum.DELETED)
                .published(true)
                .redeemed(true)
                .build();

        CouponEntity entity = new CouponEntity(coupon);

        Coupon result = entity.toDomain();

        assertEquals(id, result.getId());
        assertEquals("ABC123", result.getCode());
        assertEquals("Cupom excluído", result.getDescription());
        assertEquals(BigDecimal.valueOf(25.50), result.getDiscountValue());
        assertEquals(expirationDate, result.getExpirationDate());
        assertEquals(CouponEnum.DELETED, result.getStatus());
        assertEquals(true, result.isPublished());
        assertEquals(true, result.isRedeemed());
    }
}