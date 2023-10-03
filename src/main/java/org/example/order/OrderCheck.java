package org.example.order;

import io.restassured.response.ValidatableResponse;

import javax.net.ssl.HttpsURLConnection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCheck {
    public int createdOrderSuccessfully(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_CREATED)
                .extract()
                .path("track");
    }

    public void cancelOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("ok", is(true));
    }

    public void getOrderListSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("orders.id[0]", notNullValue());
    }
}
