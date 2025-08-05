package example;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;


public class CreatePlayerTests extends Base {


    @Test
    public void verifySupervisorCanCreateUser() {

        Response response = CreatePlayerMethods.createUser("supervisor", "user");

        Assert.assertEquals(response.statusCode(), 200,
                "Expected 200 but got " + response.statusCode());
    }

    @Test(description = "BUG: admin cannot create users (expected 200, got 403)")
    public void verifyAdminCanCreateUser() {

        Response response = CreatePlayerMethods.createUser("admin", "user");

        Assert.assertEquals(response.statusCode(), 200,
                "BUG: Expected 200, but got 403. Admin cannot create user — needs to be fixed.");
    }

    @Test
    public void verifySupervisorCanCreateAdmin() {

        Response response = CreatePlayerMethods.createUser("supervisor", "admin");

        Assert.assertEquals(response.statusCode(), 200,
                "Expected 200 for creating Admin, but got " + response.statusCode());
    }

    @Test
    public void verifyUserCannotCreateNewUser() {

        Response response = CreatePlayerMethods.createUser("user", "user");

        Assert.assertEquals(response.statusCode(), 403,
                "Expect 403 for user creating others, but got " + response.statusCode());
    }

    @Test(description = "BUG: Password shorter than 7 characters accepted")
    public void verifyUserCannotBeCreatedWithTooShortPassword() {

        Response response = CreatePlayerMethods.createUserPasswordTests("abc123");

        Assert.assertEquals(response.statusCode(), 403,
                "Expect 403 for creating too short password, but got " + response.statusCode());
    }

    @Test(description = "BUG: Password longer than 15 characters is accepted")
    public void verifyUserCannotBeCreatedWithTooLongPassword() {

        Response response = CreatePlayerMethods.createUserPasswordTests("abcdefgh1234567891011");

        Assert.assertEquals(response.statusCode(), 403,
                "Expect 403 for creating too long password, but got " + response.statusCode());
    }

    @Test(description = "BUG: Password without letters is accepted")
    public void verifyUserCannotBeCreatedWithPasswordWithoutLetters() {

        Response response = CreatePlayerMethods.createUserPasswordTests("12345678");

        Assert.assertEquals(response.statusCode(), 403,
                "Expect 403 for creating password without letter, but got " + response.statusCode());
    }

    @Test(description = "BUG: Password without numbers is accepted")
    public void verifyUserCannotBeCreatedWithPasswordWithoutNumbers() {

        Response response = CreatePlayerMethods.createUserPasswordTests("abcdefgh");

        Assert.assertEquals(response.statusCode(), 403,
                "Expect 403 for creating password without numbers, but got " + response.statusCode());
    }

    @Test
    public void verifyInabilityToCreateTooYoungUser() {

        Response response = CreatePlayerMethods.createUserAgeTests("16");

        Assert.assertEquals(response.statusCode(), 400,
                "Expect 400 for user under 17 age, but got " + response.statusCode());
    }

    @Test(description = "BUG: User age=60 should be rejected, but is accepted")
    public void VerifyInabilityToCreateTooOldUser() {

        Response response = CreatePlayerMethods.createUserAgeTests("60");

        Assert.assertEquals(response.statusCode(), 400,
                "Expect 400 for user older than 59 age, but got " + response.statusCode());
    }

}
