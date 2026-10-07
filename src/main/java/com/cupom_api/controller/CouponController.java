package com.cupom_api.controller;

import com.cupom_api.application.CreateCouponUseCase;
import com.cupom_api.application.DeleteCouponUseCase;
import com.cupom_api.application.domain.Coupon;
import com.cupom_api.application.request.CreateCouponRequest;
import com.cupom_api.application.response.CouponResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CreateCouponUseCase createCouponUseCase;
    private final DeleteCouponUseCase deleteCouponUseCase;

    public CouponController(
            CreateCouponUseCase createCouponUseCase,
            DeleteCouponUseCase deleteCouponUseCase) {

        this.createCouponUseCase = createCouponUseCase;
        this.deleteCouponUseCase = deleteCouponUseCase;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> create(
            @Valid @RequestBody CreateCouponRequest request) {

        Coupon coupon = createCouponUseCase.execute(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                request.published()
        );

        return ResponseEntity
                .created(URI.create("/coupon/" + coupon.getId()))
                .body(CouponResponse.from(coupon));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CouponResponse> delete(
            @PathVariable UUID id) {

        Coupon coupon = deleteCouponUseCase.execute(id);

        return ResponseEntity.ok(
                CouponResponse.from(coupon)
        );
    }
}