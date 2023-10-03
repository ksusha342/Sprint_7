import io.restassured.response.ValidatableResponse;
import org.example.order.*;
import org.junit.*;

import java.util.List;

public class OrderListTest {
    private final OrderClient orderClient = new OrderClient();
    private final OrderCheck orderCheck = new OrderCheck();

    protected int track;

    @Before
    public void createOrder() {
        Order order = new Order("Ян", "Ли", "Ивантеевская улица, 23",  4, "+7 999 999 99 99", 5, "2030-12-12", "Вход со двора", List.of("BLACK"));
        ValidatableResponse createOrderResponse = orderClient.create(order);
        track = orderCheck.createdOrderSuccessfully(createOrderResponse);
    }

    @Test
    public void getOrderListTest(){
        var getOrderListResponse = orderClient.getOrderList();
        orderCheck.getOrderListSuccessfully(getOrderListResponse);
    }

    @After
    public void cancelOrder() {
        orderCheck.cancelOrderSuccessfully(orderClient.cancel(track));
    }
}
