package com.cupom_api.persistence.entity;

import com.cupom_api.application.domain.Coupon;
import com.cupom_api.application.domain.enums.CouponEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "coupons")
public class CouponEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private Instant expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponEnum status;

    @Column(nullable = false)
    private boolean published;

    @Column(nullable = false)
    private boolean redeemed;

    protected CouponEntity() {
    }

    public CouponEntity(Coupon coupon) {
        this.id = coupon.getId();
        this.code = coupon.getCode();
        this.description = coupon.getDescription();
        this.discountValue = coupon.getDiscountValue();
        this.expirationDate = coupon.getExpirationDate();
        this.status = coupon.getStatus();
        this.published = coupon.isPublished();
        this.redeemed = coupon.isRedeemed();
    }

    public Coupon toDomain() {

        return Coupon.builder()
                .id(id)
                .code(code)
                .description(description)
                .discountValue(discountValue)
                .expirationDate(expirationDate)
                .status(status)
                .published(published)
                .redeemed(redeemed)
                .build();
    }
}
