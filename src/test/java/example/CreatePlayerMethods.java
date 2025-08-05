package example;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CreatePlayerMethods {

    public static Response createUser(String editorRole, String role) {
        return given()
                .basePath("/player/create/{editor}")
                .pathParam("editor", editorRole)
                .queryParam("age", 30)
                .queryParam("gender", "male")
                .queryParam("login", "u" + System.currentTimeMillis())
                .queryParam("password", "abc1234")
                .queryParam("role", role)
                .queryParam("screenName", "sc" + System.currentTimeMillis())
                .when().get();
    }

    public static Response createExactUser(String editorRole, String role, String name) {
        return given()
                .basePath("/player/create/{editor}")
                .pathParam("editor", editorRole)
                .queryParam("age", 30)
                .queryParam("gender", "male")
                .queryParam("login", name)
                .queryParam("password", "abc1234")
                .queryParam("role", role)
                .queryParam("screenName", name)
                .when().get();
    }

    public static Response createUserPasswordTests(String password) {
        return given()
                .basePath("/player/create/{editor}")
                .pathParam("editor", "supervisor")
                .queryParam("age", 30)
                .queryParam("gender", "male")
                .queryParam("login", "u" + System.currentTimeMillis())
                .queryParam("password", password)
                .queryParam("role", "user")
                .queryParam("screenName", "sc" + System.currentTimeMillis())
                .when().get();
    }

    public static Response createUserAgeTests(String age) {
        return given()
                .basePath("/player/create/{editor}")
                .pathParam("editor", "supervisor")
                .queryParam("age", age)
                .queryParam("gender", "male")
                .queryParam("login", "u" + System.currentTimeMillis())
                .queryParam("password", "abc1234")
                .queryParam("role", "user")
                .queryParam("screenName", "sc" + System.currentTimeMillis())
                .when().get();
    }
}
