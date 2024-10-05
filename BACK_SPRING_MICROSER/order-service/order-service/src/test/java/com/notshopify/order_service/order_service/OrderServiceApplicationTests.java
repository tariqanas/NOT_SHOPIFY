package com.notshopify.order_service.order_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
                	"order_number": "Porsche 911",
                	"price": "100000",
                	"sku": "1562x"
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



}
