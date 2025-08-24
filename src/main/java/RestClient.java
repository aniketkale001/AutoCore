import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class RestClient
{
    private RestClient()
    {
        // prevent instantiation
    }
    // GET Request
    public static Response get(String url)
    {
        return RestAssured
                .given()
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }
    // POST Request with JSON Body
    public static Response post(String url, String jsonBody)
    {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }
    // PUT Request with JSON Body
    public static Response put(String url, String jsonBody)
    {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }
    // DELETE Request
    public static Response delete(String url)
    {
        return RestAssured
                .given()
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }
}