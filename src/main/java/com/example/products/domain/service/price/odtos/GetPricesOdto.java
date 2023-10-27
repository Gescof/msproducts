package com.example.products.domain.service.price.odtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Builder
public record GetPricesOdto(
        Long brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priceList,
        Long productId,
        Integer priority,
        BigDecimal value,
        Currency currency
) {
}
