import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;

public class loginTest {

    HelperAPI helperAPI = new HelperAPI();

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = helperAPI.base_URI;
    }

    @Test(priority = 1)
    public void login(){


        //Response object
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("email", "john@mail.com");
        requestBodyMap.put("password", "changeme");

        Response response = RestAssured
                .with()
                .contentType("application/json")
                .body(requestBodyMap)
                .request("POST", "/api/v1/auth/login");
        assertEquals(response.getStatusCode(), 201);

        //getting token
        String gettingToken = response.jsonPath().getString("access_token");
        helperAPI.setToken(gettingToken);
    }

    @Test(priority = 2)
    public void getProfile(){

        Response response =RestAssured
                .with()
                .header("Authorization", "Bearer " + helperAPI.getToken())
                .get("/api/v1/auth/profile");
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("email"), "john@mail.com");
        assertEquals(response.jsonPath().getString("name"), "Jhon");
        response.then().body(matchesJsonSchemaInClasspath("schema/getSingleUser.json"));
    }

}
