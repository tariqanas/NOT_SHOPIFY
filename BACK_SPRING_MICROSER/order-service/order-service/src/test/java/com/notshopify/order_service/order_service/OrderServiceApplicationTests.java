package com.notshopify.order_service.order_service;

import com.notshopify.order_service.order_service.strubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

    @ServiceConnection
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

    @LocalServerPort
    private Integer port;


    @BeforeEach
    public void setup() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mySQLContainer.start();
    }


    @Test
    void ShouldPlaceAnOrder() {
        String body = """
                {
                	"order_number": "9524",
                	"price": "100000",
                	"sku": "1562x",
                	"quantity":"4"
                }
                """;

        RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/orders/")
                .then()
                .statusCode(201)
                .body(Matchers.equalTo("Your order has been placed"));
    }

    @Test
    void ShouldPlaceAnOrderCallingWireMock() {
        String body = """
                {
                	"order_number": "9524",
                	"price": "100000",
                	"skuCode ": "1562x",
                	"quantity":"4"
                }
                """;
        InventoryClientStub.stubInventoryCall("1562x", 4);

        RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/orders/")
                .then()
                .statusCode(201)
                .body(Matchers.equalTo("Your order has been placed"));
    }

}
