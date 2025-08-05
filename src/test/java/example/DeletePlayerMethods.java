package example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeletePlayerMethods {

    public static Response deleteUser(Integer  playerID) {
        return given()
                .basePath("/player/delete/supervisor")
                .contentType(ContentType.JSON)
                .body("{ \"playerId\": " + playerID +" }")
                .when()
                .delete();
    }
}
