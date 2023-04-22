package orders;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

import org.junit.runner.RunWith;
import io.restassured.RestAssured;
import functions.orders.OrdersCreate;
import org.junit.runners.Parameterized;

/**
 * Требования к тестам для метода "Создание заказа" :
 * можно указать оба цвета;
 * тело ответа содержит track;
 * можно совсем не указывать цвет;
 * можно указать один из цветов — BLACK или GREY;
 **/

@RunWith(Parameterized.class)
public class OrdersCreateTest extends OrdersCreate {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    private final String phone;
    private final String address;
    private final String comment;
    private final String lastName;
    private final Integer rentTime;
    private final String firstName;
    private final String deliveryDate;
    private final Integer metroStation;
    private final ArrayList<String> color;

    public OrdersCreateTest(String phone, String address, String comment, String lastName, Integer rentTime,
                            String firstName, String deliveryDate, Integer metroStation, ArrayList<String> color) {
        this.phone = phone;
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
                {"+7 900 999 99 00", "Home", "Flat", "Room", 1, "Station", "2023-04-03", 1, null},
                {"+7 129 000 00 99", "Gallery", "Mall", "Santa", 2, "Claus", "2023-04-01", 2, getColour("BLACK")},
                {"+7 500 100 20 55", "Street", "Baker", "Holmes", 1, "Sherlock", "2023-04-15", 1, getColour("GRAY")},
                {"+7 900 999 99 00", "Place", "Hall", "Mall", 1, "St", "2023-04-30", 1, getListColour("BLACK", "GRAY")},
        };
    }

    @Test
    @DisplayName("Проверка входного параметра colour")
    //можно указать оба цвета;
    //можно совсем не указывать цвет;
    //можно указать один из цветов — BLACK или GREY;
    public void checkOrdersParams() {
        getOrdersCreate(phone, address, comment, lastName, rentTime, firstName,
                deliveryDate, metroStation, color, 201);
    }

    @Test //тело ответа содержит track;
    @DisplayName("Проверка ответа при успешном создании заказов")
    public void checkOrdersCreate(){
        Assert.assertTrue(getOrdersCreate(phone, address, comment, lastName, rentTime, firstName,
                        deliveryDate, metroStation, color,201).contains("track"));
    }
}