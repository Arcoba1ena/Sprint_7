package orders;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import java.util.ArrayList;
import org.junit.runner.RunWith;
import io.restassured.RestAssured;
import functions.OrdersCreate;
import functions.CourierLogin;
import functions.CourierCreate;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;

import static functions.Utils.getColour;
import static functions.Utils.getListColour;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Требования к тестам для метода "Список заказов" :
 * Проверь, что в тело ответа возвращается список заказов.
 **/

@RunWith(Parameterized.class)
public class OrdersListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    private final String phone;
    private final String login;
    private final String address;
    private final String comment;
    private final String password;
    private final String lastName;
    private final Integer rentTime;
    private final String firstName;
    private final String deliveryDate;
    private final Integer metroStation;
    private final ArrayList<String> color;

    public OrdersListTest(String phone, String login, String address, String comment, String password,
                          String lastName, Integer rentTime, String firstName, String deliveryDate,
                          Integer metroStation, ArrayList<String> color) {
        this.phone = phone;
        this.login = login;
        this.password = password;
        this.color = color;
        this.address = address;
        this.comment = comment;
        this.lastName = lastName;
        this.rentTime = rentTime;
        this.firstName = firstName;
        this.metroStation = metroStation;
        this.deliveryDate = deliveryDate;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0},{1} ...")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"+7 900 999 99 00", "Tomato", "Home", "Flat", "001", "Room", 1, "Station", "2023-04-03", 1,
                        null},
                {"+7 129 000 00 99", "Potato", "Gallery", "Mall", "002", "Santa", 2, "Claus", "2023-04-01", 2,
                        getColour("BLACK")},
                {"+7 500 100 20 55", "Cucumber", "Street", "Baker", "003", "Holmes", 3, "Sherlock", "2023-04-15", 3,
                        getColour("GRAY")},
                {"+7 900 999 99 00", "Salt","Place", "Hall", "004", "Mall", 4, "St", "2023-04-30", 4,
                        getListColour("BLACK", "GRAY")},
        };
    }

    CourierLogin courierLogin = new CourierLogin();
    OrdersCreate ordersCreate = new OrdersCreate();
    CourierCreate courierCreate = new CourierCreate();

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void checkOrderList() {
        courierCreate.getCreateCourier(login,password,firstName,201);
        courierLogin.getCourierAuth(login,password,200);
        ordersCreate.getOrdersCreate(phone, address, comment, lastName, rentTime, firstName,
                deliveryDate, metroStation, color, 201);

        Response response = given()
                .get("/api/v1/orders");
        response.then().statusCode(200);
        response.then().assertThat().body("orders", notNullValue());
    }

    @After
    @DisplayName("Удаление созданных курьеров")
    public void testDeleteCouriers() {
        CourierLogin courierLogin = new CourierLogin();
        Integer id = courierLogin.getCourierId(login,password);
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("api/v1/courier/" + id);
        response.then().statusCode(200);
    }
}