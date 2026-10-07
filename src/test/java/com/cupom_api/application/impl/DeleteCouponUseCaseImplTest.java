package com.cupom_api.application.impl;

import com.cupom_api.application.domain.Coupon;
import com.cupom_api.application.CouponRepository;
import com.cupom_api.application.domain.enums.CouponEnum;
import com.cupom_api.application.exeption.InvalidCouponException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCouponUseCaseImplTest {

    @Mock
    private CouponRepository couponRepository;

    private DeleteCouponUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteCouponUseCaseImpl(couponRepository);
    }

    @Test
    void shouldDeleteCouponSuccessfully() {
        UUID id = UUID.randomUUID();

        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                Instant.now().plusSeconds(3600),
                false
        );

        when(couponRepository.findById(id))
                .thenReturn(Optional.of(coupon));

        when(couponRepository.save(coupon))
                .thenReturn(coupon);

        Coupon result = useCase.execute(id);

        assertEquals(CouponEnum.DELETED, result.getStatus());

        verify(couponRepository).findById(id);
        verify(couponRepository).save(coupon);
    }

    @Test
    void shouldThrowExceptionWhenCouponDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(couponRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                InvalidCouponException.class,
                () -> useCase.execute(id)
        );

        verify(couponRepository).findById(id);
        verify(couponRepository, never()).save(any());
    }

    @Test
    void shouldNotSaveAlreadyDeletedCoupon() {
        UUID id = UUID.randomUUID();

        Coupon coupon = Coupon.create(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                Instant.now().plusSeconds(3600),
                false
        );

        coupon.delete();

        when(couponRepository.findById(id))
                .thenReturn(Optional.of(coupon));

        assertThrows(
                InvalidCouponException.class,
                () -> useCase.execute(id)
        );

        verify(couponRepository).findById(id);
        verify(couponRepository, never()).save(any());
    }
}