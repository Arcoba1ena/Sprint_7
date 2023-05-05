package functions;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierDelete {
    public void getDeleteCourier(String login, String password) {
        CourierLogin courierLogin = new CourierLogin();
        Integer id = courierLogin.getCourierId(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("api/v1/courier/" + id);
        if (id != null) {
            response.then().statusCode(200);
        } else {
            response.then().statusCode(500);
        }
    }
}