package com.notshopify.product_service;

import com.notshopify.product_service.dto.ProductResponse;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testcontainers.containers.MongoDBContainer;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private Integer port;

    @Autowired
    TestRestTemplate testRestTemplate;


    @BeforeEach
    public void setup() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
    }


    @Test
    //RestAssured
    void shouldCreateAProduct() {
        String body = """
                {
                	"id": "146",
                	"name": "car paint",
                	"price": "1000",
                	"description": "GREEN"
                }
                """;

        RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/products/")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("car paint"))
                .body("price", Matchers.equalTo(1000))
                .body("description", Matchers.equalTo("GREEN"));
    }

	@Test
    //TestRestTemplate
	void shouldCreateAProductWithTestRestTemplate() {
		String body = """
				{
					"id": "146",
					"name": "car paint",
					"price": "1000",
					"description": "GREEN"
				}
				""";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
		HttpEntity<String> exch = new HttpEntity<>(body,httpHeaders);


        testRestTemplate.postForObject(URI.create("http://localhost:"+port+"/api/products/"), exch, ProductResponse.class);

        ProductResponse[] list =   testRestTemplate.getForObject(URI.create("http://localhost:"+port+"/api/products/"),  ProductResponse[].class);

		assertEquals(146, list[0].id());

	}

}
