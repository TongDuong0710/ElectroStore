package com.example.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
/**
 * Scenario: Multiple customers concurrently add the same product to their baskets.
 *
 * Steps:
 * 1. Admin seeds a product with initial stock = 20.
 * 2. 50 concurrent customers attempt to add 1 unit each to their baskets at the same time.
 * 3. Exactly 20 requests should succeed (HTTP 200) and consume all available stock.
 * 4. The remaining 30 requests should fail gracefully with HTTP 409 (Conflict: insufficient stock).
 * 5. After all requests, the final stock must be 0 (no oversell, no negative stock).
 *
 * Purpose: Verify safe concurrent usage and atomic stock decrement, ensuring that
 * high contention does not result in overselling beyond available inventory.
 */
class ConcurrentAddStockTests extends BaseE2E {

    @Test
    void many_customers_compete_for_limited_stock_no_oversell() throws Exception {
        // Seed product with stock=20
        Long pid = given().contentType(ContentType.JSON).body("""
            {"name":"HotItem","description":"d","category":"PHONE","price":99.0,"stock":20}
        """).when().post("/api/admin/products")
                .then().statusCode(200)
                .extract().jsonPath().getLong("data.id");

        int threads = 50; // 50 concurrent customers
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch startGun = new CountDownLatch(1);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger conflict = new AtomicInteger();

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            final String customerId = "concurrent-user-" + i;
            futures.add(pool.submit(() -> {
                try {
                    startGun.await(); // all fire at once
                    int status =
                            given().header("X-Customer-ID", customerId)
                                    .contentType(ContentType.JSON)
                                    .body("""
                                   {"productId": %d, "quantity": 1}
                               """.formatted(pid))
                                    .when().post("/api/customer/basket/items")
                                    .then().extract().statusCode();
                    if (status == 200) success.incrementAndGet();
                    else if (status == 409) conflict.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }

        // Fire!
        startGun.countDown();
        for (Future<?> f : futures) f.get(30, TimeUnit.SECONDS);
        pool.shutdown();

        // Expect exactly 20 successes, remaining are 409
        org.junit.jupiter.api.Assertions.assertEquals(20, success.get(), "exactly stock size should succeed");
        org.junit.jupiter.api.Assertions.assertTrue(conflict.get() >= 30, "the rest should conflict");

        // Final stock should be 0
        given().queryParam("page", 0).queryParam("size", 10)
                .when().get("/api/admin/products")
                .then().statusCode(200)
                .body("data.content.find { it.id == %d }.stock".formatted(pid), is(0));
    }
}
