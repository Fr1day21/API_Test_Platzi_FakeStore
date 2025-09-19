import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;

public class productTest {

    Faker faker = new Faker();

    String base_uri = "https://api.escuelajs.co";

    HelperValidasi helper = new HelperValidasi();

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = base_uri;
    }


    //Berfungsi sebagai pemilihan categories secara acak
    int[] number = {2, 3, 4, 5};
    Random random = new Random();
    int categories = number[random.nextInt(number.length)];




    @Test(priority = 1)
    public void getProducts(){
        Response response = RestAssured.get("/api/v1/products");
        assertEquals(response.getStatusCode(), 200);
        //validasi response body
        response.then().body(matchesJsonSchemaInClasspath("schema/getProducts.json"));
    }


    @Test(priority = 3)
    public void getProductById(){
        Response response = RestAssured.get("/api/v1/products/" + helper.getMyID());
        assertEquals(response.getStatusCode(), 200);
        response.then().body(matchesJsonSchemaInClasspath("schema/getProductById.json"));
    }

    @Test(priority = 2)
    public void postProduct(){


        //Berfungsi responsePOST array didalam object
        List<String> images = new ArrayList<>();
        images.add(faker.internet().image());

        //Response object
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("title", faker.book().title());
        requestBodyMap.put("price", 100);
        requestBodyMap.put("description", faker.lorem().sentence());
        requestBodyMap.put("categoryId", categories);
        requestBodyMap.put("images", images);

        Response responsePOST = RestAssured
                .with()
                .contentType("application/json")
                .body(requestBodyMap)
                .request("POST", "/api/v1/products/");

        String getingID = responsePOST.jsonPath().getString("id");
        helper.setMyID(getingID);

        //validasi status code
        assertEquals(responsePOST.getStatusCode(), 201);

        //Digunakan untuk melakukan setter validation dari title dikarenakan tergenerate random
        String titleValidationPOST = responsePOST.jsonPath().getString("title");
        helper.setTitle(titleValidationPOST);

        //validasi value respose dari title
        assertEquals(responsePOST.jsonPath().getString("title"), helper.getTitle());

    }

    @Test(priority = 4)
    public void updateProduct(){

        //Berfungsi responsePOST array didalam object
        List<String> images = new ArrayList<>();
        images.add(faker.internet().image());

        //Response object
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("title", faker.book().title());
        requestBodyMap.put("price", 100);
        requestBodyMap.put("description", faker.lorem().sentence());
        requestBodyMap.put("categoryId", categories);
        requestBodyMap.put("images", images);


        Response response = RestAssured
                .with()
                .contentType("application/json")
                .body(requestBodyMap)
                .request("PUT", "/api/v1/products/" + helper.getMyID());
        assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 5)
    public void deleteProduct(){

        Response response = RestAssured
                .with()
                .request("DELETE", "/api/v1/products/" + helper.getMyID());
        assertEquals(response.getStatusCode(), 200);
    }

}
