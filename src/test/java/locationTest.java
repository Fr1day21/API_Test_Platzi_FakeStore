import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;

public class locationTest {

    HelperAPI helperAPI = new HelperAPI();

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = helperAPI.base_URI;
    }

    @Test
    public void getAllLocations(){

        Response response = RestAssured
                .get("/api/v1/locations");
        assertEquals(response.getStatusCode(), 200);
        response.then().body(matchesJsonSchemaInClasspath("schema/getAllLocations.json"));
    }

}
