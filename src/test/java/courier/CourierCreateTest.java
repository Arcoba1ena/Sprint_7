package courier;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import functions.courier.CourierLogin;
import functions.courier.CourierCreate;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.given;

/**
 * Требования к тестам для метода "Создание курьера" :
 * курьера можно создать;
 * нельзя создать двух одинаковых курьеров;
 * чтобы создать курьера, нужно передать в ручку все обязательные поля;
 * запрос возвращает правильный код ответа;
 * успешный запрос возвращает ok: true;
 * если одного из обязательных полей нет, запрос возвращает ошибку;
 * если создать пользователя с логином, который уже есть, возвращается ошибка.
 **/

@RunWith(Parameterized.class)
public class CourierCreateTest extends CourierCreate {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    private final String login;
    private final String password;
    private final String firstName;

    public CourierCreateTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }


    @Parameterized.Parameters(name = "Тестовые данные: {0},{1},{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Summer", "00001", "Bridges"},
                {"Michelle", "10009", "Cooper"},
                {"Wednesday", "50205", "Adams"},
        };
    }

    @Test //курьера можно создать;
    @DisplayName("Создание курьера")
    public void checkCourierCreate() {
        getCreateCourier(login, password, firstName, 201);
    }

    @Test //нельзя создать двух одинаковых курьеров;
    @DisplayName("Создание дубликата курьера")
    public void checkRepeatCreate() {
        Assert.assertTrue(getCreateCourier(login, password, firstName, 201)
                .contains("true"));

        Assert.assertTrue(getCreateCourier(login, password, firstName, 409)
                .contains("Этот логин уже используется"));
    }

    @Test
    //чтобы создать курьера, нужно передать в ручку все обязательные поля;
    //если одного из обязательных полей нет, запрос возвращает ошибку;
    @DisplayName("Проверка обязательности полей")
    public void checkRequiredParams() {
        Assert.assertTrue(getCreateCourier(null, password, firstName, 400)
                .contains("Недостаточно данных для создания учетной записи"));

        Assert.assertTrue(getCreateCourier(login, null, firstName, 400)
                .contains("Недостаточно данных для создания учетной записи"));

        Assert.assertTrue(getCreateCourier(login, password, null, 201)
                .contains("true"));
    }

    @Test //запрос возвращает правильный код ответа;
    @DisplayName("Проверка валидности статус кодов")
    public void checkStatusCode() {
        getCreateCourier(login, password, firstName, 201);
        getCreateCourier(login, password, firstName, 409);
        getCreateCourier(null, password, firstName, 400);
        getCreateCourier(login, null, firstName, 400);
    }

    @Test //успешный запрос возвращает ok: true;
    @DisplayName("Проверка ответа при успешном создании курьера")
    public void checkCourierResponse() {
        Assert.assertEquals("{\"ok\":true}", getCreateCourier(login, password, firstName, 201));
    }

    @Test //если создать пользователя с логином, который уже есть, возвращается ошибка.
    @DisplayName("Проверка ответа при создании дубля курьера")
    public void checkCourierRepeatResponse() {
        getCreateCourier(login, password, firstName, 201);
        Assert.assertEquals("{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}",
                getCreateCourier(login, password, firstName, 409));
    }

    @After
    @DisplayName("Удаление созданных курьеров")
    public void testDeleteCouriers() {
        CourierLogin courierLogin = new CourierLogin();
        Integer id = courierLogin.getCourierId(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("api/v1/courier/" + id);
        response.then().statusCode(200);
    }
}