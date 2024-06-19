package tests;

import consts.CurrencyEndpoints;
import utils.StringUtils;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static consts.Errors.*;
import static consts.StatusCodes.*;
import static utils.StringUtils.generateRandomString;
import static utils.StringUtils.generateRandomText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CurrencyTests extends BaseTest {

    private final String currencyUsd = "USD";

    @Test
    @Description("API call is successful and returns valid price.")
    public void checkSuccessApiResponse() {

        Response response = createRequest()
                .contentType(ContentType.JSON)
                .pathParam("currency", currencyUsd)
                .get(CurrencyEndpoints.GET_CURRENCY_ENDPOINT);
        int statusCode = response.then()
                .extract().response().statusCode();
        Assertions.assertEquals(SUCCESS_CODE, statusCode);
    }

    private static Stream<Arguments> invalidArgumentsStreamProvider() {

        return Stream.of(
                Arguments.of(StringUtils.EMPTY, NOT_FOUND, NOT_FOUND_ERROR_STRING),
                Arguments.of(generateRandomString(10), SUCCESS_CODE, UNSUPPORTED_ERROR),
                Arguments.of(generateRandomText(500), BAD_REQUEST, BAD_REQUEST_ERROR),
                Arguments.of(generateRandomText(2600), TOO_LARGE_REQUEST, REQUEST_TOO_LARGE)
        );

    }


    @ParameterizedTest(name = "{1}, {2}")
    @MethodSource("invalidArgumentsStreamProvider")
    @Description("Check the status code and status returned by the API response.")
    public void verifyStatusCodeAndResponse(String value, int statusCode, String errorMessage) {

        Response response = createRequest()
                .contentType(ContentType.JSON)
                .get(value);
        String actualErrorMessage = response.then()
                .statusCode(statusCode)
                .extract().response().asString();
        Assertions.assertEquals(errorMessage, actualErrorMessage);
    }

    @Test
    @Description("Fetch the USD price against the AED and make sure the prices are in range on 3.6 – 3.7")
    public void verifyAedPricesAreInRange() {

        Response response = createRequest()
                .contentType(ContentType.JSON)
                .pathParam("currency", currencyUsd)
                .get(CurrencyEndpoints.GET_CURRENCY_ENDPOINT);
        double aed_rate = response.body().jsonPath().getDouble("rates.AED");
        assertThat(aed_rate, allOf(greaterThan(3.6), lessThan(3.7)));
    }


    @Test
    @Description("Make sure API response Ɵme is not less then 3 seconds then current Ɵme in second.")
    public void verifyApiResponseTimeIsLessThan3Seconds() {

        Response response = createRequest()
                .contentType(ContentType.JSON)
                .pathParam("currency", currencyUsd)
                .get(CurrencyEndpoints.GET_CURRENCY_ENDPOINT);
        long timeResponse = response.getTimeIn(TimeUnit.MILLISECONDS);
        assertThat(timeResponse, is(lessThan(3000L)));
    }


    @Test
    @Description("Verify that 162 currency pairs are returned by the API. ")
    public void verifyCurrencyPairs() {

        Response response = createRequest()
                .contentType(ContentType.JSON)
                .pathParam("currency", currencyUsd)
                .get(CurrencyEndpoints.GET_CURRENCY_ENDPOINT);
        int exchange_count = response.body().jsonPath().getMap("rates").size();
        Assertions.assertEquals(exchange_count, 162);
    }

    @Test
    @Description("Make sure API response matches the Json schema ")
    public void verifyJsonSchemaValidation() {

        createRequest()
                .contentType(ContentType.JSON)
                .pathParam("currency", currencyUsd)
                .get(CurrencyEndpoints.GET_CURRENCY_ENDPOINT)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get_currency.json"));

    }



}
