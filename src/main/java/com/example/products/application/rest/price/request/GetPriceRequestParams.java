package com.example.products.application.rest.price.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class GetPriceRequestParams {

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime applicationDate;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long productId;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long brandId;
}
