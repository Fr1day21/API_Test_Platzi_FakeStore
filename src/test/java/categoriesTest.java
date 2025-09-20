import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;

public class categoriesTest {

    HelperAPI helperAPI = new HelperAPI();

    Faker faker = new Faker();


    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = helperAPI.base_URI;
    }


    @Test(priority = 2)
    public void getCategoryById(){

        Response response = RestAssured
                .get("/api/v1/categories/" + helperAPI.getMyID());
        assertEquals(response.getStatusCode(), 200);
        response.then().body(matchesJsonSchemaInClasspath("schema/postCategories.json"));
    }

    @Test(priority = 1)
    public void createCategory(){

        //Response object
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", "Category Test");
        requestBodyMap.put("image", faker.internet().image());


        Response response = RestAssured
                .with()
                .contentType("application/json")
                .body(requestBodyMap)
                .request("POST", "/api/v1/categories/");
        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.jsonPath().getString("name"), "Category Test");
        response.then().body(matchesJsonSchemaInClasspath("schema/postCategories.json"));

        String gettingID = response.jsonPath().getString("id");
        helperAPI.setMyID(gettingID);
    }

    @Test (priority = 3)
    public void updateCategory(){

        //Response object
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("name", "Update Category Test");
        requestBodyMap.put("images", faker.internet().image());

        Response response = RestAssured
                .with()
                .contentType("application/json")
                .body(requestBodyMap)
                .request("PUT", "/api/v1/categories/" + helperAPI.getMyID());
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("name"), "Update Category Test");
        response.then().body(matchesJsonSchemaInClasspath("schema/postCategories.json"));
    }

    @Test(priority = 4)
    public void deleteCategory(){

        Response response = RestAssured
                .delete("/api/v1/categories/" + helperAPI.getMyID());
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getAllCategory(){

        Response response = RestAssured
                .get("/api/v1/categories/");
        assertEquals(response.getStatusCode(), 200);
        response.then().body(matchesJsonSchemaInClasspath("schema/allCategories.json"));
    }

}
