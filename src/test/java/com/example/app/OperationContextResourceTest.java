package com.example.app;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(OperationContextResource.class)
public class OperationContextResourceTest {

    @Test
    public void whenPostCalled_andIsNoFailure_thenShouldReturn201() {
        given()
                .body("{\n" +
                        "\t\"merchantOrderId\": 123,\n" +
                        "\t\"amount\": 13.4,\n" +
                        "\t\"currency\": \"SEK\"\n" +
                        "}")
                .header("Content-Type", "application/json")
          .when()
                .post()
          .then()
             .statusCode(201);
    }

}