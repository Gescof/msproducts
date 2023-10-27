package com.example.products.domain.service.price;

import com.example.products.domain.model.Price;
import com.example.products.domain.repository.PriceRepository;
import com.example.products.domain.service.price.idtos.GetPricesIdto;
import com.example.products.domain.service.price.odtos.GetPricesOdto;
import com.example.products.domain.service.price.odtos.GetPricesOdtoMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DomainPriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @Spy
    private GetPricesOdtoMapperImpl odtoMapper;

    @InjectMocks
    private DomainPriceService domainPriceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class WhenGetPricesByParametersTest {

        @ParameterizedTest(name = "with {0} parameters expect to find {1} and return {2}")
        @MethodSource("getPricesByParametersData")
        void shouldGetPricesByParameters(GetPricesIdto idto, List<Price> expectedPrices, List<GetPricesOdto> expectedOdtos) {
            // When
            when(priceRepository.findPricesByParameters(idto.applicationDate(), idto.productId(), idto.brandId()))
                    .thenReturn(expectedPrices);

            // Then
            List<GetPricesOdto> getPricesOdtos = domainPriceService.getPricesByParameters(idto);

            // Assert
            assertEquals(expectedOdtos, getPricesOdtos);
        }

        static Stream<Arguments> getPricesByParametersData() {
            // Test 1
            GetPricesIdto idto1 = GetPricesIdto.builder()
                    .applicationDate(LocalDateTime.of(2020, 6, 14, 10, 0, 0))
                    .productId(34555L)
                    .brandId(1L)
                    .build();
            List<Price> expectedPrices1 = Collections.singletonList(Price.builder()
                    .brandId(idto1.brandId())
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                    .priceList(1)
                    .productId(idto1.productId())
                    .priority(0)
                    .value(BigDecimal.valueOf(35.50))
                    .currency(Currency.getInstance("EUR"))
                    .build()
            );
            List<GetPricesOdto> expectedOdtos1 = Collections.singletonList(GetPricesOdto.builder()
                    .brandId(idto1.brandId())
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                    .priceList(1)
                    .productId(idto1.productId())
                    .priority(0)
                    .value(BigDecimal.valueOf(35.50))
                    .currency(Currency.getInstance("EUR"))
                    .build()
            );

            // Test 2
            GetPricesIdto idto2 = GetPricesIdto.builder()
                    .applicationDate(LocalDateTime.of(2020, 6, 16, 21, 0, 0))
                    .productId(34555L)
                    .brandId(1L)
                    .build();
            List<Price> expectedPrices2 = List.of(Price.builder()
                            .brandId(idto2.brandId())
                            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(1)
                            .productId(idto2.productId())
                            .priority(0)
                            .value(BigDecimal.valueOf(35.50))
                            .currency(Currency.getInstance("EUR"))
                            .build(),
                    Price.builder()
                            .brandId(idto2.brandId())
                            .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(4)
                            .productId(idto2.productId())
                            .priority(0)
                            .value(BigDecimal.valueOf(38.95))
                            .currency(Currency.getInstance("EUR"))
                            .build()
            );
            List<GetPricesOdto> expectedOdtos2 = List.of(GetPricesOdto.builder()
                            .brandId(idto2.brandId())
                            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(1)
                            .productId(idto2.productId())
                            .priority(0)
                            .value(BigDecimal.valueOf(35.50))
                            .currency(Currency.getInstance("EUR"))
                            .build(),
                    GetPricesOdto.builder()
                            .brandId(idto2.brandId())
                            .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(4)
                            .productId(idto2.productId())
                            .priority(0)
                            .value(BigDecimal.valueOf(38.95))
                            .currency(Currency.getInstance("EUR"))
                            .build()
            );

            return Stream.of(
                    Arguments.of(idto1, expectedPrices1, expectedOdtos1),
                    Arguments.of(idto2, expectedPrices2, expectedOdtos2),
                    Arguments.of(GetPricesIdto.builder().build(), List.of(), List.of()) // empty test
            );
        }
    }
}
