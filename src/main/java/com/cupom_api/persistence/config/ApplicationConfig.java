package com.cupom_api.persistence.config;

import com.cupom_api.application.CreateCouponUseCase;
import com.cupom_api.application.DeleteCouponUseCase;
import com.cupom_api.application.impl.CreateCouponUseCaseImpl;
import com.cupom_api.application.impl.DeleteCouponUseCaseImpl;
import com.cupom_api.application.CouponRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CreateCouponUseCase createCouponUseCase(
            CouponRepository couponRepository) {

        return new CreateCouponUseCaseImpl(couponRepository);
    }

    @Bean
    public DeleteCouponUseCase deleteCouponUseCase(
            CouponRepository couponRepository) {

        return new DeleteCouponUseCaseImpl(couponRepository);
    }

//    @Bean
//    public GetCouponUseCase getCouponUseCase(
//            CouponRepository couponRepository) {
//
//        return new GetCouponUseCaseImpl(couponRepository);
//    }
}
