package com.example.products.infrastructure.repository.price;

import com.example.products.domain.model.Price;
import com.example.products.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceDboRepository implements PriceRepository {

    private final PriceMapper priceMapper;
    private final SpringDataPriceRepository priceRepository;

    @Override
    public List<Price> findPricesByParameters(final LocalDateTime applicationDate, final Long productId,
                                              final Long brandId) {
        List<PriceEntity> prices = priceRepository.findPriceByParameters(applicationDate, productId, brandId);
        return prices.stream().map(priceMapper::toPrice).toList();
    }
}
