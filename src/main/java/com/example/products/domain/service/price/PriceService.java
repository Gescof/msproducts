package com.example.products.domain.service.price;

import com.example.products.domain.service.price.idtos.GetPricesIdto;
import com.example.products.domain.service.price.odtos.GetPricesOdto;

import java.util.List;

public interface PriceService {

    List<GetPricesOdto> getPricesByParameters(GetPricesIdto idto);
}
