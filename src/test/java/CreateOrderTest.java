import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderCheck;
import org.example.order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final OrderClient orderClient = new OrderClient();
    private final OrderCheck check = new OrderCheck();
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    private Order order;
    private Integer track;

    public CreateOrderTest(String firstName,
                           String lastName,
                           String address,
                           int metroStation,
                           String phone,
                           int rentTime,
                           String deliveryDate,
                           String comment,
                           List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] createOrder() {
        return new Object[][] {
                { "Ян", "Ли", "Ивантеевская улица, 23",  4, "+7 999 999 99 99", 5, "2030-12-12", "Вход со двора", List.of("BLACK")},
                { "Ян", "Ли", "Ивантеевская улица, 23",  4, "+7 999 999 99 99", 5, "2030-12-12", "Вход со двора", List.of("GREY")},
                { "Ян", "Ли", "Ивантеевская улица, 23",  4, "+7 999 999 99 99", 5, "2030-12-12", "Вход со двора", List.of("BLACK", "GREY")},
                { "Ян", "Ли", "Ивантеевская улица, 23",  4, "+7 999 999 99 99", 5, "2030-12-12", "Вход со двора", null},
                { "Ян", "Ли", "Ивантеевская улица, 23",  4, "+7 999 999 99 99", 5, "2030-12-12", "Вход со двора", List.of()}
        };
    }

    @Before
    public void prepareOrderObject() {
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    @Test
    public void createNewOrder() {
        ValidatableResponse createOrderResponse = orderClient.create(order);
        track = check.createdOrderSuccessfully(createOrderResponse);
        assert track != 0;
    }

    @After
    public void cancelObject() {
        check.cancelOrderSuccessfully(orderClient.cancel(track));
    }
}