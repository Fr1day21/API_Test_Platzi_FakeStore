import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;

public class userTest {

    Faker faker = new Faker();
    HelperAPI helperAPI = new HelperAPI();

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = helperAPI.base_URI;
    }

    @Test
    public void getAllUsers(){
        Response response = RestAssured
                .get("/api/v1/users");
        assertEquals(response.getStatusCode(), 200);
        response.then().body(matchesJsonSchemaInClasspath("schema/getAllUsers.json"));
    }

    @Test(priority = 2)
    public void getSingleUser(){
        Response response = RestAssured
                .get("/api/v1/users/" + helperAPI.getMyID());
        assertEquals(response.getStatusCode(), 200);
        response.then().body(matchesJsonSchemaInClasspath("schema/getSingleUser.json"));
    }

    @Test(priority = 1)
    public void createUser(){

        //Response object
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", "User Testing");
        requestBodyMap.put("email", "useremailtesting@gmail.com");
        requestBodyMap.put("password", faker.internet().password());
        requestBodyMap.put("avatar", faker.internet().image());

        Response response = RestAssured
                .with()
                .contentType("application/json")
                .body(requestBodyMap)
                .request("POST", "/api/v1/users/");
        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.jsonPath().getString("email"), "useremailtesting@gmail.com");
        assertEquals(response.jsonPath().getString("name"), "User Testing");
        response.then().body(matchesJsonSchemaInClasspath("schema/getSingleUser.json"));

        //getting id untuk digunakan pada test case yang lain
        String gettingId = response.jsonPath().getString("id");
        helperAPI.setMyID(gettingId);

        String gettingEmail = response.jsonPath().getString("email");
        helperAPI.setEmail(gettingEmail);

        String gettingPassword = response.jsonPath().getString("password");
        helperAPI.setPassword(gettingPassword);
    }

    @Test(priority = 3)
    public void updatedUser(){

        //Response object
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("email", "updateduseremailtesting@gmail.com");
        requestBodyMap.put("name", "Updated User Testing");

        Response response = RestAssured
                .with()
                .contentType("application/json")
                .body(requestBodyMap)
                .request("PUT", "/api/v1/users/" + helperAPI.getMyID());
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("email"), "updateduseremailtesting@gmail.com");
        assertEquals(response.jsonPath().getString("name"), "Updated User Testing");
        response.then().body(matchesJsonSchemaInClasspath("schema/getSingleUser.json"));
    }

}
