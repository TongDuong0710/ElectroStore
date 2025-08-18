package com.example.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.given;

/**
 * Scenario: Multiple customers concurrently add the same product to their baskets.
 *
 * Steps:
 * 1. Seed a product with initial stock = 20.
 * 2. 50 concurrent customers attempt to add 1 unit each at the same time.
 * 3. System must NOT oversell: successes <= 20; failures are graceful with 409 Conflict.
 * 4. Final stock equals initialStock - successes.
 *
 * Purpose: Verify safe concurrent usage and atomic stock decrement (no oversell)
 * under high contention, regardless of locking strategy (optimistic/atomic SQL/etc.).
 */
class ConcurrentAddStockTests extends BaseE2E {

    @Test
    void many_customers_compete_for_limited_stock_no_oversell() throws Exception {
        final int initialStock = 20;
        final int threads = 50;

        // Seed product
        Long pid = given().contentType(ContentType.JSON).body("""
            {"name":"HotItem","description":"d","category":"PHONE","price":99.0,"stock":%d}
        """.formatted(initialStock)).when().post("/api/admin/products")
                .then().statusCode(200)
                .extract().jsonPath().getLong("data.id");

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch startGun = new CountDownLatch(1);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger conflict = new AtomicInteger();
        AtomicInteger other = new AtomicInteger();

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            final String customerId = "concurrent-user-" + i;
            futures.add(pool.submit(() -> {
                try {
                    startGun.await();
                    int status = given()
                            .header("X-Customer-ID", customerId)
                            .contentType(ContentType.JSON)
                            .body("""
                                {"productId": %d, "quantity": 1}
                            """.formatted(pid))
                            .when().post("/api/customer/basket/items")
                            .then().extract().statusCode();
                    if (status == 200) success.incrementAndGet();
                    else if (status == 409) conflict.incrementAndGet();
                    else other.incrementAndGet(); // bất kỳ status nào khác là lỗi test
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }

        // Fire all
        startGun.countDown();
        for (Future<?> f : futures) f.get(30, TimeUnit.SECONDS);
        pool.shutdown();

        // Invariants (không oversell, graceful failures)
        int succ = success.get();
        int conf = conflict.get();
        int oth = other.get();

        org.junit.jupiter.api.Assertions.assertEquals(0, oth, "unexpected status codes besides 200/409");
        org.junit.jupiter.api.Assertions.assertEquals(threads, succ + conf, "all requests accounted for");
        org.junit.jupiter.api.Assertions.assertTrue(succ <= initialStock, "must not oversell beyond stock");

        // Final stock should match initialStock - successes
        int finalStock = given().queryParam("page", 0).queryParam("size", 10)
                .when().get("/api/admin/products")
                .then().statusCode(200)
                .extract().jsonPath().getInt("data.content.find { it.id == %d }.stock".formatted(pid));

        org.junit.jupiter.api.Assertions.assertEquals(initialStock - succ, finalStock, "final stock matches consumed units");

        // (Optional) nếu bạn dùng atomic SQL và muốn test chặt hơn:
        // org.junit.jupiter.api.Assertions.assertEquals(initialStock, succ, "exactly stock size should succeed");
    }
}
