package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import utils.LoggingRequestFilter;

public class BaseTest {


    @BeforeAll
    public static void setBaseUrl() {

        RestAssured.baseURI = "https://open.er-api.com/v6/latest/";

    }


    protected RequestSpecification createRequest() {
        return RestAssured.given()
                .filter(new LoggingRequestFilter());

    }


}
