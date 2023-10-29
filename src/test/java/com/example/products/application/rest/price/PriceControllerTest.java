package com.example.products.application.rest.price;

import com.example.products.application.rest.price.handler.PriceControllerAdvice;
import com.example.products.application.rest.price.request.GetPriceRequestParams;
import com.example.products.application.rest.price.request.PriceRequestMapperImpl;
import com.example.products.application.rest.price.response.ErrorResponse;
import com.example.products.application.rest.price.response.GetPricesResponse;
import com.example.products.application.rest.price.response.Price;
import com.example.products.application.rest.price.response.PriceResponseMapperImpl;
import com.example.products.domain.service.price.PriceService;
import com.example.products.domain.service.price.idtos.GetPricesIdto;
import com.example.products.domain.service.price.odtos.GetPricesOdto;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PriceController.class)
class PriceControllerTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.parse("2023-11-20T12:30:00");
    private static final String REQUEST_DATE_PATTERN = "yyyy-MM-dd-HH.mm.ss";

    @SpyBean
    private PriceRequestMapperImpl requestMapper;

    @SpyBean
    private PriceResponseMapperImpl responseMapper;

    @MockBean
    private Clock clock;

    @MockBean
    private PriceService priceService;

    @InjectMocks
    private PriceControllerAdvice priceControllerAdvice;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    void setup() {
        Clock fixedClock = Clock.fixed(LOCAL_DATE_TIME.atZone(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        RestAssuredMockMvc.standaloneSetup(
                priceController.toBuilder()
                        .priceService(priceService).requestMapper(requestMapper).responseMapper(responseMapper).build(),
                priceControllerAdvice.toBuilder()
                        .clock(clock).build()
        );
    }

    @Nested
    class WhenGetPricesTest {

        @ParameterizedTest
        @MethodSource("getPricesByParametersOkResponseData")
        void givenValidRequestParamsShouldExpectOkResponse(GetPriceRequestParams requestParams,
                                                           List<GetPricesOdto> expectedServiceResponse,
                                                           GetPricesResponse expectedResponse) {
            // When
            when(priceService.getPricesByParameters(any(GetPricesIdto.class))).thenReturn(expectedServiceResponse);

            // Given
            MockMvcResponse response = given()
                    .queryParam("applicationDate", requestParams.getApplicationDate().format(
                            DateTimeFormatter.ofPattern(REQUEST_DATE_PATTERN)))
                    .queryParam("productId", requestParams.getProductId())
                    .queryParam("brandId", requestParams.getBrandId())
                    .when()
                    .get("/api/v1/prices");

            // Assert
            assertThat(response.then()
                    .log().ifValidationFails()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(GetPricesResponse.class))
                    .isEqualTo(expectedResponse);
        }

        @Test
        void givenInvalidApplicationDateRequestParamShouldExpectBadRequestResponse() {
            // Given
            MockMvcResponse response = given()
                    .queryParam("applicationDate", "2020-06-12")
                    .when()
                    .get("/api/v1/prices");

            // Assert
            response.then()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .extract()
                    .as(ErrorResponse.class);
        }

        @Test
        void givenInvalidProductIdRequestParamShouldExpectBadRequestResponse() {
            // Given
            MockMvcResponse response = given()
                    .queryParam("productId", "invalid")
                    .when()
                    .get("/api/v1/prices");

            // Assert
            response.then()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .extract()
                    .as(ErrorResponse.class);
        }

        @Test
        void givenInvalidBrandIdRequestParamShouldExpectBadRequestResponse() {
            // Given
            MockMvcResponse response = given()
                    .queryParam("brandId", "invalid")
                    .when()
                    .get("/api/v1/prices");

            // Assert
            response.then()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .extract()
                    .as(ErrorResponse.class);
        }

        static Stream<Arguments> getPricesByParametersOkResponseData() {
            GetPriceRequestParams requestParams1 = createMockRequestParamsForOkTest1();
            List<GetPricesOdto> expectedServiceResponse1 = createMockServiceResponseForOkTest1();
            GetPricesResponse expectedResponse1 = createMockResponseForOkTest1();

            GetPriceRequestParams requestParams2 = createMockRequestParamsForOkTest2();
            List<GetPricesOdto> expectedServiceResponse2 = createMockServiceResponseForOkTest2();
            GetPricesResponse expectedResponse2 = createMockResponseForOkTest2();

            GetPriceRequestParams requestParams3 = createMockRequestParamsForOkTest3();
            List<GetPricesOdto> expectedServiceResponse3 = createMockServiceResponseForOkTest3();
            GetPricesResponse expectedResponse3 = createMockResponseForOkTest3();

            return Stream.of(
                    Arguments.of(requestParams1, expectedServiceResponse1, expectedResponse1),
                    Arguments.of(requestParams2, expectedServiceResponse2, expectedResponse2),
                    Arguments.of(requestParams3, expectedServiceResponse3, expectedResponse3)
            );
        }

        private static GetPriceRequestParams createMockRequestParamsForOkTest1() {
            return GetPriceRequestParams.builder()
                    .applicationDate(LocalDateTime.parse("2020-06-14-10.00.00",
                            DateTimeFormatter.ofPattern(REQUEST_DATE_PATTERN)))
                    .productId(35455L)
                    .brandId(1L)
                    .build();
        }

        private static GetPriceRequestParams createMockRequestParamsForOkTest2() {
            return GetPriceRequestParams.builder()
                    .applicationDate(LocalDateTime.parse("2020-06-16-21.00.00",
                            DateTimeFormatter.ofPattern(REQUEST_DATE_PATTERN)))
                    .productId(35455L)
                    .brandId(1L)
                    .build();
        }

        private static GetPriceRequestParams createMockRequestParamsForOkTest3() {
            return GetPriceRequestParams.builder()
                    .applicationDate(LocalDateTime.parse("2020-06-14-10.00.00",
                            DateTimeFormatter.ofPattern(REQUEST_DATE_PATTERN)))
                    .productId(12345L)
                    .brandId(2L)
                    .build();
        }

        private static List<GetPricesOdto> createMockServiceResponseForOkTest1() {
            return Collections.singletonList(
                    GetPricesOdto.builder()
                            .brandId(1L)
                            .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                            .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                            .priceList(1)
                            .productId(35455L)
                            .priority(0)
                            .value(BigDecimal.valueOf(35.50))
                            .currency(Currency.getInstance("EUR"))
                            .build());
        }

        private static List<GetPricesOdto> createMockServiceResponseForOkTest2() {
            return List.of(
                    GetPricesOdto.builder()
                            .brandId(1L)
                            .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                            .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                            .priceList(1)
                            .productId(35455L)
                            .priority(0)
                            .value(BigDecimal.valueOf(35.50))
                            .currency(Currency.getInstance("EUR"))
                            .build(),
                    GetPricesOdto.builder()
                            .brandId(1L)
                            .startDate(LocalDateTime.parse("2020-06-15T16:00:00"))
                            .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                            .priceList(4)
                            .productId(35455L)
                            .priority(0)
                            .value(BigDecimal.valueOf(38.95))
                            .currency(Currency.getInstance("EUR"))
                            .build());
        }

        private static List<GetPricesOdto> createMockServiceResponseForOkTest3() {
            return Collections.emptyList();
        }

        private static GetPricesResponse createMockResponseForOkTest1() {
            return GetPricesResponse.builder()
                    .prices(Collections.singletonList(
                            Price.builder()
                                    .brandId(1L)
                                    .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                                    .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                                    .priceList(1)
                                    .productId(35455L)
                                    .priority(0)
                                    .value(BigDecimal.valueOf(35.50))
                                    .currency(Currency.getInstance("EUR"))
                                    .build()))
                    .build();
        }

        private static GetPricesResponse createMockResponseForOkTest2() {
            return GetPricesResponse.builder()
                    .prices(List.of(
                            Price.builder()
                                    .brandId(1L)
                                    .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                                    .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                                    .priceList(1)
                                    .productId(35455L)
                                    .priority(0)
                                    .value(BigDecimal.valueOf(35.50))
                                    .currency(Currency.getInstance("EUR"))
                                    .build(),
                            Price.builder()
                                    .brandId(1L)
                                    .startDate(LocalDateTime.parse("2020-06-15T16:00:00"))
                                    .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                                    .priceList(4)
                                    .productId(35455L)
                                    .priority(0)
                                    .value(BigDecimal.valueOf(38.95))
                                    .currency(Currency.getInstance("EUR"))
                                    .build()))
                    .build();
        }

        private static GetPricesResponse createMockResponseForOkTest3() {
            return GetPricesResponse.builder().prices(Collections.emptyList()).build();
        }
    }
}
