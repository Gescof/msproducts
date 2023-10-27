package com.example.products.application.rest.price.request;

import com.example.products.domain.service.price.idtos.GetPricesIdto;
import org.mapstruct.Mapper;

@Mapper
public interface PriceRequestMapper {

    GetPricesIdto toGetPriceIdto(GetPriceRequestParams getPriceRequestParams);
}
