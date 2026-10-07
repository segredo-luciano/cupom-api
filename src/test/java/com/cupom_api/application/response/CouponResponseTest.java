package com.cupom_api.application.response;

import com.cupom_api.application.domain.Coupon;
import com.cupom_api.application.domain.enums.CouponEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CouponResponseTest {

    @Test
    void shouldConvertCouponToResponse() {
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

        CouponResponse response = CouponResponse.from(coupon);

        assertEquals(id, response.id());
        assertEquals("ABC123", response.code());
        assertEquals("Cupom de desconto", response.description());
        assertEquals(BigDecimal.TEN, response.discountValue());
        assertEquals(expirationDate, response.expirationDate());
        assertEquals("ACTIVE", response.status());
        assertTrue(response.published());
        assertFalse(response.redeemed());
    }

    @Test
    void shouldConvertDeletedCouponToResponse() {
        Coupon coupon = Coupon.builder()
                .id(UUID.randomUUID())
                .code("ABC123")
                .description("Cupom excluído")
                .discountValue(BigDecimal.valueOf(20))
                .expirationDate(Instant.now().plusSeconds(3600))
                .status(CouponEnum.DELETED)
                .published(false)
                .redeemed(false)
                .build();

        CouponResponse response = CouponResponse.from(coupon);

        assertEquals("DELETED", response.status());
        assertEquals("ABC123", response.code());
        assertEquals("Cupom excluído", response.description());
        assertEquals(BigDecimal.valueOf(20), response.discountValue());
        assertFalse(response.published());
        assertFalse(response.redeemed());
    }
}