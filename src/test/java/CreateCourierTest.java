import io.restassured.response.ValidatableResponse;
import org.example.courier.CourierCheck;
import org.example.courier.CourierClient;
import org.example.courier.CourierGenerator;
import org.example.courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Test;


public class CreateCourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierCheck check = new CourierCheck();
    protected int courierId;


    @Test
    @DisplayName("Check courier creation")
    @Description("Basic courier creation test")
    public void createCourier() {
        var courier = CourierGenerator.random();

        ValidatableResponse response = client.create(courier);
        check.createdCourierSuccessfully(response);

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check the creation of two identical couriers")
    @Description("Check that it is impossible to create two identical couriers")
    public void createTwoIdenticalCouriers() {
        var courier = CourierGenerator.random();

        ValidatableResponse response = client.create(courier);
        ValidatableResponse secondResponse = client.create(courier);

        check.createdCourierSuccessfully(response);
        check.createdCourierUnsuccessfully(secondResponse);

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check the creation of two couriers with identical login")
    @Description("Check that it is impossible to create two couriers with identical login")
    public void createTwoIdenticalCouriersLogin() {
        var firstCourier = CourierGenerator.random();
        var secondCourier = CourierGenerator.random();
        secondCourier.setLogin(firstCourier.getLogin());

        ValidatableResponse response = client.create(firstCourier);
        ValidatableResponse secondResponse = client.create(secondCourier);

        check.createdCourierSuccessfully(response);
        check.createdCourierUnsuccessfully(secondResponse);

        var creds = Credentials.from(firstCourier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check courier creation without login field")
    @Description("Check that it is impossible to create courier without login field")
    public void createCourierWithoutLogin() {
        var courier = CourierGenerator.random();
        courier.setLogin(null);

        ValidatableResponse response = client.create(courier);
        check.createdCourierUnsuccessfullyWithoutRequiredFields(response);
    }

    @Test
    @DisplayName("Check courier creation without password field")
    @Description("Check that it is impossible to create courier without password field")
    public void createCourierWithoutPassword() {
        var courier = CourierGenerator.random();
        courier.setPassword(null);

        ValidatableResponse response = client.create(courier);
        check.createdCourierUnsuccessfullyWithoutRequiredFields(response);
    }

    @Test
    @DisplayName("Check courier creation without firstName field")
    @Description("Check that it is impossible to create courier without firstName field")
    public void createCourierWithoutFirstName() {
        var courier = CourierGenerator.random();
        courier.setFirstName(null);

        ValidatableResponse response = client.create(courier);
        check.createdCourierSuccessfully(response);

        var creds = Credentials.from(courier);

        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse delete = client.delete(courierId);
            check.deletedCourierSuccessfully(delete);
        }
    }
}
