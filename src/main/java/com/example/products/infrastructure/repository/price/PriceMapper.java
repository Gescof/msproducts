package com.example.products.infrastructure.repository.price;

import com.example.products.domain.model.Price;
import org.mapstruct.Mapper;

@Mapper
public interface PriceMapper {

    Price toPrice(PriceEntity price);
}
