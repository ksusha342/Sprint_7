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
                .body("message", is("Этот логин уже используется"));
    }

    public void createdCourierUnsuccessfullyWithoutRequiredFields(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .extract()
                .path("id");
    }

    public void loggedInUnsuccessfullyWithoutRequiredFields(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    public void loggedInUnsuccessfullyWithWrongCredentials(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    public void deletedCourierSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .extract()
                .path("id");
    }
}
