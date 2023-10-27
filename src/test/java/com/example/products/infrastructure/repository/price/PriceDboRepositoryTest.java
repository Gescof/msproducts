package com.example.products.infrastructure.repository.price;

import com.example.products.domain.model.Price;
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

class PriceDboRepositoryTest {

    @Mock
    private SpringDataPriceRepository priceRepository;

    @Spy
    private PriceMapperImpl priceMapper;

    @InjectMocks
    private PriceDboRepository priceDboRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class WhenFindPricesByParametersTest {

        @ParameterizedTest(name = "with {0} applicationDate, {1} productId and {2} brandId expect to find {3} and return {4}")
        @MethodSource("findPricesByParametersData")
        void shouldFindPricesByParameters(LocalDateTime applicationDate, Long productId, Long brandId,
                                          List<PriceEntity> expectedPricesEntities, List<Price> expectedPrices) {
            // When
            when(priceRepository.findPriceByParameters(applicationDate, productId, brandId))
                    .thenReturn(expectedPricesEntities);

            // Then
            List<Price> prices = priceDboRepository.findPricesByParameters(applicationDate, productId, brandId);

            // Assert
            assertEquals(expectedPrices, prices);
        }

        static Stream<Arguments> findPricesByParametersData() {
            // Test 1
            LocalDateTime applicationDate1 = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
            Long productId1 = 34555L;
            Long brandId1 = 1L;
            List<PriceEntity> expectedPricesEntities1 = Collections.singletonList(PriceEntity.builder()
                    .brandId(brandId1)
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                    .priceList(1)
                    .productId(productId1)
                    .priority(0)
                    .value(BigDecimal.valueOf(35.50))
                    .currency(Currency.getInstance("EUR"))
                    .build()
            );
            List<Price> expectedPrices1 = Collections.singletonList(Price.builder()
                    .brandId(brandId1)
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                    .priceList(1)
                    .productId(productId1)
                    .priority(0)
                    .value(BigDecimal.valueOf(35.50))
                    .currency(Currency.getInstance("EUR"))
                    .build()
            );

            // Test 2
            LocalDateTime applicationDate2 = LocalDateTime.of(2020, 6, 16, 21, 0, 0);
            Long productId2 = 34555L;
            Long brandId2 = 1L;
            List<PriceEntity> expectedPricesEntities2 = List.of(PriceEntity.builder()
                            .brandId(brandId2)
                            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(1)
                            .productId(productId2)
                            .priority(0)
                            .value(BigDecimal.valueOf(35.50))
                            .currency(Currency.getInstance("EUR"))
                            .build(),
                    PriceEntity.builder()
                            .brandId(brandId2)
                            .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(4)
                            .productId(productId2)
                            .priority(0)
                            .value(BigDecimal.valueOf(38.95))
                            .currency(Currency.getInstance("EUR"))
                            .build()
            );
            List<Price> expectedPrices2 = List.of(Price.builder()
                            .brandId(brandId2)
                            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(1)
                            .productId(productId2)
                            .priority(0)
                            .value(BigDecimal.valueOf(35.50))
                            .currency(Currency.getInstance("EUR"))
                            .build(),
                    Price.builder()
                            .brandId(brandId2)
                            .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                            .priceList(4)
                            .productId(productId2)
                            .priority(0)
                            .value(BigDecimal.valueOf(38.95))
                            .currency(Currency.getInstance("EUR"))
                            .build()
            );

            return Stream.of(
                    Arguments.of(applicationDate1, productId1, brandId1, expectedPricesEntities1, expectedPrices1),
                    Arguments.of(applicationDate2, productId2, brandId2, expectedPricesEntities2, expectedPrices2),
                    Arguments.of(null, null, null, List.of(), List.of()) // empty test
            );
        }
    }
}
