package com.example.products.domain.repository;

import com.example.products.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {

    List<Price> findPricesByParameters(LocalDateTime applicationDate, Long productId, Long brandId);
}
