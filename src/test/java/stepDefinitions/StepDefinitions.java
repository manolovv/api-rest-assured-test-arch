package stepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.pojo.CreateProductDetails;
import resources.APIResources;
import resources.TestDataBuilder;
import resources.Utils;
import org.testng.Assert;
import java.io.IOException;

import static io.restassured.RestAssured.*;

public class StepDefinitions extends Utils {

    RequestSpecification req;
    static Response response;
    TestDataBuilder data = new TestDataBuilder();
    static String token;
    static String productId;

    @Given("Login User Payload with {string} {string}")
    public void login_user_payload_with(String userEmail, String userPassword) throws IOException {

        req = given().spec(requestSpecification()).body(data.getUserDetails(userEmail, userPassword));

    }
    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource,String httpMethod) {
        APIResources apiResources = APIResources.valueOf(resource);

        if (httpMethod.equalsIgnoreCase("POST")) {
            response = req.when().post(apiResources.getResource());

        } else if (httpMethod.equalsIgnoreCase("GET")) {
            response = req.when().get(apiResources.getResource());
            
        } else if (httpMethod.equalsIgnoreCase("DELETE")) {
            response = req.when().delete(apiResources.getResource());

        }

    }
    @Then("The API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer searchingResCode) {

        Assert.assertEquals(response.getStatusCode(), searchingResCode);

    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String message, String expectedMessage) {

        Assert.assertEquals(getJsonPath(response, message), expectedMessage);
    }

    @Given("Product info with Params")
    public void product_info_payload_with() throws IOException {

        token = getJsonPath(response, "token");
        String userId = getJsonPath(response, "userId");
        CreateProductDetails productDetails = data.getProductDetails(userId);

        req = given().spec(requestSpecification()).header("Authorization", token)
                .contentType("multipart/form-data")
                .params(data.getProductParams(productDetails))
                .multiPart("productImage", productDetails.getFile());

    }
    @When("Product calls {string} with {string} http request")
    public void product_calls_with_http_request(String resource, String httpMethod) {

        user_calls_with_http_request(resource, httpMethod);

    }

    @Given("Order info with json Payload")
    public void order_info_with_json_payload() throws IOException {

        productId = getJsonPath(response, "productId");
        req = given().spec(requestSpecification()).header("Authorization", token)
                .body(data.getOrderDetails(productId));

    }
    @When("Order calls {string} with {string} http request")
    public void order_calls_with_http_request(String resource, String httpMethod) {

        user_calls_with_http_request(resource, httpMethod);

    }

    @Given("Delete product Payload")
    public void delete_product_payload() throws IOException {

        req = given().spec(requestSpecification()).header("Authorization", token)
                .pathParam("productId", productId);

    }

}
