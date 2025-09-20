import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.assertEquals;

public class filterProductTest {


    HelperAPI helperAPI = new HelperAPI();


    @BeforeTest
    public void setUp(){

        RestAssured.baseURI = helperAPI.base_URI;

    }


    //Berfungsi sebagai pemilihan categories secara acak
    int[] number = {2, 3, 4, 5};
    Random random = new Random();
    int categories = number[random.nextInt(number.length)];


    @Test
    public void filterByTitile(){

        Response response = RestAssured
                .get("/api/v1/products/?title=Classic");
        assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void filterByPrice(){

        Response response = RestAssured
                .get("/api/v1/products/?price=100");
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void filterByPriceRange(){

        Response response = RestAssured
                .get("/api/v1/products/?price_min=10&price_max=100");
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void filterByCategory(){

        Response response = RestAssured
                .get("/api/v1/products/?categoryId=" + categories);
        assertEquals(response.getStatusCode(), 200);
    }








}
