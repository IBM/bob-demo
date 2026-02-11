package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BookingResourceTest {
    @Test
    void testFindBookingEndpoint() {
        given()
          .when()
                .queryParam("bookingNumber", "123-456")
                .queryParam("name", "Alex")
                .queryParam("surname", "Soto")
                .get("/booking")
          .then()
             .statusCode(200);
    }

}