package com.example.products.application.rest.price.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPricesResponse {

    @JsonProperty(value = "prices")
    private List<Price> prices;
}
