package com.cupom_api.persistence.repository;

import com.cupom_api.persistence.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponJpaRepository
        extends JpaRepository<CouponEntity, UUID> {
}
