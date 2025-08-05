package example;

import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Listeners;


import static io.restassured.RestAssured.given;
public class DeletePlayerTests extends Base{

    @Test
    public void verifyUserCanBeDeletedBySupervisor() {

        String userToDelete1 = "userToDelete1";

        Response createResponse = CreatePlayerMethods.createExactUser("supervisor", "user", userToDelete1);

        Assert.assertEquals(createResponse.statusCode(), 200,
                "User creation failed");

        Response getAllResponse = given()
                .basePath("/player/get/all")
                .when()
                .get();

        Assert.assertEquals(getAllResponse.statusCode(), 200,
                "Getting all players failed");

        JsonPath jsonPath = getAllResponse.jsonPath();
        List<Map<String, Object>> players = jsonPath.getList("players");

        Integer playerId = null;
        for (Map<String, Object> player : players) {
            if (userToDelete1.equals(player.get("screenName"))) {
                playerId = (Integer) player.get("id");
                break;
            }
        }

        Assert.assertNotNull(playerId, "User ID is not found by screenName: " + userToDelete1);

        Response deleteResponse = DeletePlayerMethods.deleteUser(playerId);

        Assert.assertEquals(deleteResponse.statusCode(), 204, "User deletion failed");

        Response getAllAfterDelete = given()
                .basePath("/player/get/all")
                .when()
                .get();

        List<Map<String, Object>> updatedPlayers = getAllAfterDelete.jsonPath().getList("players");

        boolean stillExists = updatedPlayers.stream()
                .anyMatch(p -> userToDelete1.equals(p.get("screenName")));

        Assert.assertFalse(stillExists, "User was not deleted from the list");
    }

    @Test(description = "BUG: regular user can delete a user (expected 403, got 204)")
    public void verifyUserCannotBeDeletedByUser() {

        String userToDelete2 = "userToDelete2";

        Response createResponse = CreatePlayerMethods.createExactUser("supervisor", "user", userToDelete2);

        Assert.assertEquals(createResponse.statusCode(), 200,
                "User creation failed");

        Response getAllResponse = given()
                .basePath("/player/get/all")
                .when()
                .get();

        Assert.assertEquals(getAllResponse.statusCode(), 200,
                "Getting all players failed");

        JsonPath jsonPath = getAllResponse.jsonPath();
        List<Map<String, Object>> players = jsonPath.getList("players");

        Integer playerId = null;
        for (Map<String, Object> player : players) {
            if (userToDelete2.equals(player.get("screenName"))) {
                playerId = (Integer) player.get("id");
                break;
            }
        }

        Assert.assertNotNull(playerId, "User ID is not found by screenName: " + userToDelete2);

        Response deleteResponse = DeletePlayerMethods.deleteUser(playerId);

        Assert.assertEquals(deleteResponse.statusCode(), 403, "User could be deleted by another user");
    }

    @Test
    public void verifyInabilityToDeleteNonExistentUser() {
        Integer nonExistentId = 12345;

        Response deleteResponse = DeletePlayerMethods.deleteUser(nonExistentId);

        Assert.assertTrue(deleteResponse.statusCode() != 204 && deleteResponse.statusCode() >= 400,
                "Expected error status for non-existent user deletion, but got " + deleteResponse.statusCode());
    }
}







