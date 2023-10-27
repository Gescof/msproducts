package com.example.products.application.rest.price;

import com.example.products.application.rest.price.request.GetPriceRequestParams;
import com.example.products.application.rest.price.request.PriceRequestMapper;
import com.example.products.application.rest.price.response.GetPricesResponse;
import com.example.products.application.rest.price.response.PriceResponseMapper;
import com.example.products.domain.service.price.PriceService;
import com.example.products.domain.service.price.idtos.GetPricesIdto;
import com.example.products.domain.service.price.odtos.GetPricesOdto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PriceController {

    private final PriceRequestMapper requestMapper;
    private final PriceResponseMapper responseMapper;
    private final PriceService priceService;

    @GetMapping("prices")
    public ResponseEntity<GetPricesResponse> getPrices(@Valid final GetPriceRequestParams requestParams) {
        log.info(String.format("GET /prices with params: %s", requestParams.toString()));

        GetPricesIdto getPricesIdto = requestMapper.toGetPriceIdto(requestParams);
        List<GetPricesOdto> getPricesOdto = priceService.getPricesByParameters(getPricesIdto);
        GetPricesResponse response = responseMapper.toResponseWithPrices(getPricesOdto);

        return ResponseEntity.ok(response);
    }
}
