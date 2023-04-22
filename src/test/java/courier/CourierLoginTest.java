package courier;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import functions.courier.CourierLogin;
import functions.courier.CourierCreate;
import org.junit.runner.RunWith;
import io.restassured.RestAssured;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.given;

/**
 * Требования к тестам для метода "Логин курьера" :
 * курьер может авторизоваться;
 * для авторизации нужно передать все обязательные поля;
 * система вернёт ошибку, если неправильно указать логин или пароль;
 * если какого-то поля нет, запрос возвращает ошибку;
 * если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
 * успешный запрос возвращает id.
 **/

@RunWith(Parameterized.class)
public class CourierLoginTest extends CourierLogin {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    private final String login;
    private final String password;
    private final String firstName;

    public CourierLoginTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0},{1},{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"mr.Darren", "50001", "Potter"},
                {"mr.Edgar", "90000", "Dalton"},
                {"mrs.Cynthia", "10205", "Chaplin"},
        };
    }

    CourierCreate courierCreate = new CourierCreate();

    @Test //курьер может авторизоваться;
    @DisplayName("Проверка авторизации курьера")
    public void checkAuthCourier() {
        courierCreate.getCreateCourier(login, password, firstName, 201);
        Assert.assertTrue(getCourierAuth(login, password, 200).contains("id"));
    }

    @Test
    //для авторизации нужно передать все обязательные поля;
    //если какого-то поля нет, запрос возвращает ошибку;
    @DisplayName("Проверка обязательности полей")
    public void checkAuthRequiredParams() {
        courierCreate.getCreateCourier(login, password, firstName, 201);
        Assert.assertTrue(getCourierAuth("", password, 400).contains("Недостаточно данных для входа"));
        Assert.assertTrue(getCourierAuth(login, "", 400).contains("Недостаточно данных для входа"));
    }

    @Test
    //система вернёт ошибку, если неправильно указать логин или пароль;
    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @DisplayName("Проверка ошибки при невалидном поле логин/пароль")
    public void checkAuthWithUnValidParams() {
        courierCreate.getCreateCourier(login, password, firstName, 201);
        Assert.assertEquals("{\"code\":404,\"message\":\"Учетная запись не найдена\"}",
                getCourierAuth(password, login, 404));
    }

    @Test //успешный запрос возвращает id;
    @DisplayName("Проверка ответа при успешной авторизации")
    public void checkAthResponse() {
        courierCreate.getCreateCourier(login, password, firstName, 201);
        Integer id = getCourierId(login, password);
        Assert.assertEquals("{\"id\":" + id + "}", getCourierAuth(login, password,200));
    }

    @After
    @DisplayName("Удаление созданных курьеров")
    public void testDeleteCouriers() {
        Integer id = getCourierId(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("api/v1/courier/" + id);
        response.then().statusCode(200);
    }
}