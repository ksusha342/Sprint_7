import io.restassured.response.ValidatableResponse;
import org.example.courier.CourierCheck;
import org.example.courier.CourierClient;
import org.example.courier.Credentials;
import org.junit.Test;

public class LoginCourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierCheck check = new CourierCheck();

    @Test
    public void loginCourier() {

        var creds = new Credentials("cour11", "Qwerty123");

        ValidatableResponse loginResponse = client.login(creds);
        int id = check.loggedInSuccessfully(loginResponse);
        assert id != 0;
    }

}
