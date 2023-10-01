package org.example.courier;

import io.restassured.response.ValidatableResponse;

import javax.net.ssl.HttpsURLConnection;

import static org.hamcrest.Matchers.is;

public class CourierCheck {

    public void createdCourierSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_CREATED)
                .body("ok", is(true));
    }
    public void createdCourierUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    public void createdCourierUnsuccessfullyWithoutRequiredFields(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .extract()
                .path("id");
        return id;
    }

    public void deletedCourierSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .extract()
                .path("id");
    }
}
