package com.example.products.domain.service.price.idtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record GetPricesIdto(
        LocalDateTime applicationDate,
        Long productId,
        Long brandId
) {
}
