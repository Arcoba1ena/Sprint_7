package functions;

import io.restassured.response.Response;
import models.deserialization.CourierIdModel;
import models.serialization.CourierLoginModel;
import static io.restassured.RestAssured.given;

public class CourierLogin {
    public String getCourierAuth(String login, String password, Integer code) {
        CourierLoginModel loginModel = new CourierLoginModel(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginModel)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(code);
        return response.getBody().asString();
    }

    public Integer getCourierId(String login, String password) {
        CourierLoginModel loginModel = new CourierLoginModel(login, password);
        CourierIdModel response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginModel)
                .when()
                .post("/api/v1/courier/login")
                .body().as(CourierIdModel.class);
        return response.getId();
    }
}