package com.example.products.domain.service.price;

import com.example.products.domain.model.Price;
import com.example.products.domain.repository.PriceRepository;
import com.example.products.domain.service.price.idtos.GetPricesIdto;
import com.example.products.domain.service.price.odtos.GetPricesOdto;
import com.example.products.domain.service.price.odtos.GetPricesOdtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainPriceService implements PriceService {

    private final GetPricesOdtoMapper odtoMapper;
    private final PriceRepository priceRepository;

    @Override
    public List<GetPricesOdto> getPricesByParameters(final GetPricesIdto idto) {
        List<Price> prices = priceRepository.findPricesByParameters(idto.applicationDate(), idto.productId(),
                idto.brandId());
        return prices.stream().map(odtoMapper::toGetPriceOdto).toList();
    }
}
