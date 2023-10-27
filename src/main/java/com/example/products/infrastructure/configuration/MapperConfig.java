package com.example.products.infrastructure.configuration;

import com.example.products.application.rest.price.request.PriceRequestMapper;
import com.example.products.application.rest.price.response.PriceResponseMapper;
import com.example.products.domain.service.price.odtos.GetPricesOdtoMapper;
import com.example.products.infrastructure.repository.price.PriceMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public PriceRequestMapper priceRequestMapper() {
        return Mappers.getMapper(PriceRequestMapper.class);
    }

    @Bean
    public PriceResponseMapper priceResponseMapper() {
        return Mappers.getMapper(PriceResponseMapper.class);
    }

    @Bean
    public GetPricesOdtoMapper getPriceOdtoMapper() {
        return Mappers.getMapper(GetPricesOdtoMapper.class);
    }

    @Bean
    public PriceMapper getPriceMapper() {
        return Mappers.getMapper(PriceMapper.class);
    }
}
