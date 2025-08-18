package com.example.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

// Run full Spring Boot app on random port, using H2 test profile
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseE2E {

    @LocalServerPort
    int port;

    @BeforeEach
    void setupRestAssured() {
        // Configure RestAssured to call the running app
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
}
