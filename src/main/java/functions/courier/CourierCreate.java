package functions.courier;

import io.restassured.response.Response;
import models.serialization.CourierAuthModel;
import static io.restassured.RestAssured.given;

public class CourierCreate {
    public String getCreateCourier(String login, String password, String firstName, Integer code) {
        CourierAuthModel authModel = new CourierAuthModel(login, password, firstName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(authModel)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(code);
        return response.getBody().asString();
    }
}