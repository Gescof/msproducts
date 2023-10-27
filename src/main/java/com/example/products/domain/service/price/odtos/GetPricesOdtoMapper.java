package com.example.products.domain.service.price.odtos;

import com.example.products.domain.model.Price;
import org.mapstruct.Mapper;

@Mapper
public interface GetPricesOdtoMapper {

    GetPricesOdto toGetPriceOdto(Price price);
}
