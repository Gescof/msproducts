package com.example.products.application.rest.price.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Builder
public class Price {

    @JsonProperty(value = "brand_id")
    private Long brandId;

    @JsonProperty(value = "start_date")
    private LocalDateTime startDate;

    @JsonProperty(value = "end_date")
    private LocalDateTime endDate;

    @JsonProperty(value = "price_list")
    private Integer priceList;

    @JsonProperty(value = "product_id")
    private Long productId;

    @JsonProperty(value = "priority")
    private Integer priority;

    @JsonProperty(value = "value")
    private BigDecimal value;

    @JsonProperty(value = "currency")
    private Currency currency;
}