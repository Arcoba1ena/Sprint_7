package functions;

import java.util.ArrayList;
import io.restassured.response.Response;
import models.serialization.OrdersCreateModel;
import static io.restassured.RestAssured.given;

public class OrdersCreate {
    public String getOrdersCreate(String phone, String address, String comment,
                                  String lastName, Integer rentTime, String firstName,
                                  String deliveryDate, Integer metroStation, ArrayList<String> color, Integer code) {
        OrdersCreateModel ordersModel = new OrdersCreateModel(phone, address, comment, lastName, rentTime,
                firstName, deliveryDate, metroStation, color);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(ordersModel)
                .when()
                .post("/api/v1/orders");
        response.then().statusCode(code);
        return response.getBody().asString();
    }
}