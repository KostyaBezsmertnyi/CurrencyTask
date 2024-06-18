package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Headers;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class LoggingRequestFilter implements Filter {

    private final Prettifier prettifier = new Prettifier();

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        log.info(requestSpec.getMethod() + " " + requestSpec.getURI()
                + " \n Request Body => " + prettifier.getPrettifiedBodyIfPossible(requestSpec)
                + " \n Request Headers => " + requestSpec.getHeaders()
                + " \n Request Form Parameters => " + requestSpec.getFormParams()
                + " \n Response Status => " + response.getStatusLine()
                + " \n Response Body => " + response.getBody().asPrettyString()
                + " \n Duration: " + response.getTimeIn(TimeUnit.MILLISECONDS) + " ms");
        return response;
    }
}