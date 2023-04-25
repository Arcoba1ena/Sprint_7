package courier;

import org.junit.Test;
import functions.Utils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import functions.CourierCreate;
import functions.CourierDelete;
import org.junit.runner.RunWith;
import io.restassured.RestAssured;
import org.junit.runners.Parameterized;
import io.qameta.allure.junit4.DisplayName;

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

    Utils utils = new Utils();
    private final String path = "src/test/resources/constants/";

    public CourierCreateTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0},{1},{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Summer", "00001", "Bridges"}
        };
    }

    @Test
    @DisplayName("Создание курьера")
    public void checkCourierCreate() {
        Assert.assertTrue(getCreateCourier(login, password, firstName, 201)
                .contains("true"));
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    public void checkRepeatCreate() {
        Assert.assertTrue(getCreateCourier(login, password, firstName, 201)
                .contains("true"));

        Assert.assertTrue(getCreateCourier(login, password, firstName, 409)
                .contains("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Проверка обязательности поля login")
    public void checkLoginRequiredParam() {
        Assert.assertTrue(getCreateCourier(null, password, firstName, 400)
                .contains("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка обязательности поля password")
    public void checkPasswordRequiredParam(){
        Assert.assertTrue(getCreateCourier(login, null, firstName, 400)
                .contains("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка ответа при успешном создании курьера")
    public void checkCourierResponse() {
        String expected = utils.readJson(path + "courier_create_code_201.json");
        Assert.assertEquals(expected, getCreateCourier(login, password, firstName, 201));
    }

    @Test
    @DisplayName("Проверка ответа при создании дубля курьера")
    public void checkCourierRepeatResponse()  {
        String expected = utils.readJson(path + "courier_create_code_409.json");
        getCreateCourier(login, password, firstName, 201);
        Assert.assertEquals(expected, getCreateCourier(login, password, firstName, 409));
    }

    @Test
    @DisplayName("Проверка ответа при отсутствии обязательных параметров")
    public void checkCourierResponseRequiredParams(){
        String expected = utils.readJson(path + "courier_create_code_400.json");
        getCreateCourier(login, password, firstName, 201);
        Assert.assertEquals(expected,getCreateCourier(null, password, firstName, 400));
        Assert.assertEquals(expected,getCreateCourier(login, null, firstName, 400));
    }

    @After
    @DisplayName("Удаление созданных курьеров")
    public void testDeleteCouriers() {
        CourierDelete courierDelete = new CourierDelete();
        courierDelete.getDeleteCourier(login,password);
    }
}