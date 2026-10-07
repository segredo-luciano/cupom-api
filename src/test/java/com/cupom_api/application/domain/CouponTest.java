package com.cupom_api.application.domain;

import com.cupom_api.application.domain.enums.CouponEnum;
import com.cupom_api.application.exeption.InvalidCouponException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    void shouldCreateCouponSuccessfully() {
        Instant expirationDate = Instant.now().plusSeconds(3600);

        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.valueOf(10),
                expirationDate,
                false
        );

        assertNotNull(coupon.getId());
        assertEquals("ABC123", coupon.getCode());
        assertEquals("Cupom de desconto", coupon.getDescription());
        assertEquals(BigDecimal.valueOf(10), coupon.getDiscountValue());
        assertEquals(expirationDate, coupon.getExpirationDate());
        assertEquals(CouponEnum.ACTIVE, coupon.getStatus());
        assertFalse(coupon.isPublished());
        assertFalse(coupon.isRedeemed());
    }

    @Test
    void shouldRemoveSpecialCharactersFromCode() {
        Coupon coupon = Coupon.create(
                "ABC-123",
                "Cupom de desconto",
                BigDecimal.valueOf(10),
                Instant.now().plusSeconds(3600),
                false
        );

        assertEquals("ABC123", coupon.getCode());
    }

    @Test
    void shouldAcceptCodeWithExactlySixAlphanumericCharacters() {
        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.valueOf(10),
                Instant.now().plusSeconds(3600),
                false
        );

        assertEquals("ABC123", coupon.getCode());
    }

    @Test
    void shouldRejectCodeWithLessThanSixCharacters() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC12",
                        "Cupom de desconto",
                        BigDecimal.valueOf(10),
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldRejectCodeWithMoreThanSixCharacters() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC1234",
                        "Cupom de desconto",
                        BigDecimal.valueOf(10),
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldRejectNullCode() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        null,
                        "Cupom de desconto",
                        BigDecimal.valueOf(10),
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldRejectBlankCode() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "      ",
                        "Cupom de desconto",
                        BigDecimal.valueOf(10),
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC123",
                        "   ",
                        BigDecimal.valueOf(10),
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldRejectNullDescription() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC123",
                        null,
                        BigDecimal.valueOf(10),
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldAcceptMinimumDiscount() {
        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.valueOf(0.5),
                Instant.now().plusSeconds(3600),
                false
        );

        assertEquals(BigDecimal.valueOf(0.5), coupon.getDiscountValue());
    }

    @Test
    void shouldRejectDiscountBelowMinimum() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC123",
                        "Cupom de desconto",
                        BigDecimal.valueOf(0.49),
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldRejectNullDiscount() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC123",
                        "Cupom de desconto",
                        null,
                        Instant.now().plusSeconds(3600),
                        false
                )
        );
    }

    @Test
    void shouldRejectExpirationDateInThePast() {
        Instant expirationDate = Instant.now().minusSeconds(1);

        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC123",
                        "Cupom de desconto",
                        BigDecimal.TEN,
                        expirationDate,
                        false
                )
        );
    }

    @Test
    void shouldRejectNullExpirationDate() {
        assertThrows(
                InvalidCouponException.class,
                () -> Coupon.create(
                        "ABC123",
                        "Cupom de desconto",
                        BigDecimal.TEN,
                        null,
                        false
                )
        );
    }

    @Test
    void shouldCreateCouponAsPublished() {
        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                Instant.now().plusSeconds(3600),
                true
        );

        assertTrue(coupon.isPublished());
    }

    @Test
    void shouldDeleteCoupon() {
        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                Instant.now().plusSeconds(3600),
                false
        );

        coupon.delete();

        assertEquals(CouponEnum.DELETED, coupon.getStatus());
    }

    @Test
    void shouldNotDeleteAlreadyDeletedCoupon() {
        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                Instant.now().plusSeconds(3600),
                false
        );

        coupon.delete();

        assertThrows(
                InvalidCouponException.class,
                coupon::delete
        );
    }

    @Test
    void shouldCreateCouponUsingBuilder() {
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

        assertEquals(id, coupon.getId());
        assertEquals("ABC123", coupon.getCode());
        assertEquals("Cupom de desconto", coupon.getDescription());
        assertEquals(BigDecimal.TEN, coupon.getDiscountValue());
        assertEquals(expirationDate, coupon.getExpirationDate());
        assertEquals(CouponEnum.ACTIVE, coupon.getStatus());
        assertTrue(coupon.isPublished());
        assertFalse(coupon.isRedeemed());
    }
}