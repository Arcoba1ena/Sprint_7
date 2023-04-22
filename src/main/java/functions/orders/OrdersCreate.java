package functions.orders;

import java.util.List;
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
        Response response = given()  //.log().all() ------------------------------------------------
                .header("Content-type", "application/json")
                .and()
                .body(ordersModel)
                .when()
                .post("/api/v1/orders");
        response.then().statusCode(code);
        System.out.println(response.getBody().asString());
        return response.getBody().asString();
    }

    public static List<ArrayList<String>> getColour(String colour){
        List<ArrayList<String>> listColour = new ArrayList<>();
        ArrayList<String> a = new ArrayList<>();
        a.add(colour);
        listColour.add(a);
        return listColour;
    }

    public static ArrayList<String> getListColour(String colour1, String colour2){
        ArrayList <String> listColour = new ArrayList<>();
        listColour.add(colour1);
        listColour.add(colour2);
        return listColour;
    }
}