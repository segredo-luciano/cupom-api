package com.cupom_api.persistence.repository;

import com.cupom_api.application.CouponRepository;
import com.cupom_api.application.domain.Coupon;
import com.cupom_api.persistence.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CouponRepositoryAdapter implements CouponRepository {

    private final CouponJpaRepository repository;

    public CouponRepositoryAdapter(CouponJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Coupon save(Coupon coupon) {

        CouponEntity entity = new CouponEntity(coupon);

        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<Coupon> findById(UUID id) {

        return repository.findById(id)
                .map(CouponEntity::toDomain);
    }
}