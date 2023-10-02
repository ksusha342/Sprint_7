package org.example.order;

import io.restassured.response.ValidatableResponse;
import org.example.Client;

public class OrderClient extends Client {
    public static final String ORDER_CREATE_PATH = "/orders";
    public static final String ORDER_CANCEL_PATH = "/orders/cancel";
    public static final String ORDER_LIST_PATH = "/orders";

    public ValidatableResponse create(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH)
                .then().log().all();
    }

    public ValidatableResponse cancel(int track) {
        return spec()
                .queryParam("track", track)
                .when()
                .put(ORDER_CANCEL_PATH)
                .then().log().all();
    }

    public ValidatableResponse getOrderList() {
        return spec()
                .when()
                .get(ORDER_LIST_PATH)
                .then().log().all();
    }
}
