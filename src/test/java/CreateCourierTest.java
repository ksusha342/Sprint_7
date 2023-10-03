import io.restassured.response.ValidatableResponse;
import org.example.courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreateCourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierCheck check = new CourierCheck();
    protected Courier courier;

    @Before
    public void createCourier() {
        courier = CourierGenerator.random();
    }


    @Test
    @DisplayName("Check courier creation")
    @Description("Basic courier creation test")
    public void createCourierSuccessfully() {
        ValidatableResponse response = client.create(courier);
        check.createdCourierSuccessfully(response);
    }

    @Test
    @DisplayName("Check the creation of two identical couriers")
    @Description("Check that it is impossible to create two identical couriers")
    public void createTwoIdenticalCouriers() {
        ValidatableResponse response = client.create(courier);
        ValidatableResponse secondResponse = client.create(courier);

        check.createdCourierSuccessfully(response);
        check.createdCourierUnsuccessfully(secondResponse);
    }

    @Test
    @DisplayName("Check the creation of two couriers with identical login")
    @Description("Check that it is impossible to create two couriers with identical login")
    public void createTwoIdenticalCouriersLogin() {
        var identicalLoginCourier = CourierGenerator.random();
        identicalLoginCourier.setLogin(courier.getLogin());

        ValidatableResponse response = client.create(courier);
        ValidatableResponse secondResponse = client.create(identicalLoginCourier);

        check.createdCourierSuccessfully(response);
        check.createdCourierUnsuccessfully(secondResponse);
    }

    @Test
    @DisplayName("Check courier creation without login field")
    @Description("Check that it is impossible to create courier without login field")
    public void createCourierWithoutLogin() {
        courier.setLogin(null);

        ValidatableResponse response = client.create(courier);
        check.createdCourierUnsuccessfullyWithoutRequiredFields(response);
    }

    @Test
    @DisplayName("Check courier creation without password field")
    @Description("Check that it is impossible to create courier without password field")
    public void createCourierWithoutPassword() {
        courier.setPassword(null);

        ValidatableResponse response = client.create(courier);
        check.createdCourierUnsuccessfullyWithoutRequiredFields(response);
    }

    @Test
    @DisplayName("Check courier creation without firstName field")
    @Description("Check that it is impossible to create courier without firstName field")
    public void createCourierWithoutFirstName() {
        courier.setFirstName(null);

        ValidatableResponse response = client.create(courier);
        check.createdCourierSuccessfully(response);
    }

    @After
    public void deleteCourier() {
        if (!courier.isCorrect()) {
            return;
        }

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        var courierId = check.loggedInSuccessfully(loginResponse);

        if (courierId != 0) {
            ValidatableResponse delete = client.delete(courierId);
            check.deletedCourierSuccessfully(delete);
        }
    }
}
