package com.example.products.application.rest.price.response;

import com.example.products.domain.service.price.odtos.GetPricesOdto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PriceResponseMapper {

    List<Price> toPrices(List<GetPricesOdto> getPricesOdtos);

    default GetPricesResponse toResponseWithPrices(List<GetPricesOdto> getPricesOdtos) {
        return GetPricesResponse.builder().prices(toPrices(getPricesOdtos)).build();
    }
}
