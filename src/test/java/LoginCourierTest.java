import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.courier.*;
import org.junit.*;

public class LoginCourierTest {

    private static final CourierClient client = new CourierClient();
    private static final CourierCheck check = new CourierCheck();
    protected static Courier courier;
    protected Credentials credentials;

    @BeforeClass
    public static void createCourier() {
        courier = CourierGenerator.random();
        ValidatableResponse response = client.create(courier);
        check.createdCourierSuccessfully(response);
    }

    @Before
    public void createCredentials() {
        credentials = Credentials.from(courier);
    }

    @Test
    @DisplayName("Check courier login")
    @Description("Basic courier login test")
    public void loginCourierSuccessfully() {
        ValidatableResponse loginResponse = client.login(credentials);
        int id = check.loggedInSuccessfully(loginResponse);
        assert id != 0;
    }

    @Test
    @DisplayName("Login courier without login field")
    @Description("Check that it is impossible to login courier without login field")
    public void loginCourierWithoutLogin() {
        credentials.setLogin(null);
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithoutRequiredFields(loginResponse);
    }

    @Test
    @DisplayName("Login courier without password field")
    @Description("Check that it is impossible to login courier without password field")
    public void loginCourierWithoutPassword() {
        credentials.setPassword(null);
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithoutRequiredFields(loginResponse);
    }

    @Test
    @DisplayName("Login courier with incorrect login field")
    @Description("Check that it is impossible to login courier with incorrect login field")
    public void loginCourierWithIncorrectLogin() {
        credentials.setLogin(credentials.getLogin() + "1");
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithWrongCredentials(loginResponse);
    }
    @Test
    @DisplayName("Login courier with incorrect password field")
    @Description("Check that it is impossible to login courier with incorrect password field")
    public void loginCourierWithIncorrectPassword() {
        credentials.setPassword(credentials.getPassword() + "1");
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithWrongCredentials(loginResponse);
    }

    @Test
    @DisplayName("Login courier with with non-existent credentials")
    @Description("Check that it is impossible to login courier with non-existent credentials")
    public void loginCourierWithNonexistentCourier() {
        credentials.setLogin(credentials.getLogin() + "1");
        credentials.setPassword(credentials.getPassword() + "1");
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithWrongCredentials(loginResponse);
    }

    @After
    public void clearCredentials() {
        credentials = null;
    }

    @AfterClass
    public static void deleteCourier() {
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
