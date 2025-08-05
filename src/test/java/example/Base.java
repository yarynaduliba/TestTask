package example;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import io.qameta.allure.restassured.AllureRestAssured;

public class Base {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://3.68.165.45";
        RestAssured.filters(new AllureRestAssured());
    }

}
