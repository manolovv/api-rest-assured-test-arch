package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.pojo.*;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class EndToEndDemo {

    public static void main(String[] args) {

        //Login

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();
        ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("mam96@abv.bg");
        loginRequest.setUserPassword("Manolov123*()");

        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
        LoginResponse logResponse = reqLogin.when().post("/api/ecom/auth/login")
                .then().spec(res).extract().response().as(LoginResponse.class);

        String token = logResponse.getToken();
        String userId = logResponse.getUserId();


        //Add Product

        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token).build();

        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
                .param("productName", "nadaren").param("productAddedBy", userId)
                .param("productCategory", "dadenosti").param("productSubCategory", "kofeni")
                .param("productPrice", "999999").param("productDescription", "Golemec")
                .param("productFor", "MAN")
                .multiPart("productImage", new File("\\Users\\User\\Downloads\\image.jpg"));

        ProductResponse addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().as(ProductResponse.class);

        String productId = addProductResponse.getProductId();


        // Create Order

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProductOrderedId(productId);
        orderDetails.setCountry("Bulgaria");

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrders(orderDetailsList);


        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON).build();

        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orderRequest);

        CreateOrderResponse createOrderResponse = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
                .extract().response().as(CreateOrderResponse.class);

        List<String> productOrderIdList = createOrderResponse.getProductOrderId();
        String productOrderId = productOrderIdList.get(0);


        // Delete Product

        RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setContentType("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON).build();

        RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBaseReq).pathParam("productId", productId);

        String deleteProductResponse = deleteProductReq.when().delete("https://rahulshettyacademy.com/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(deleteProductResponse);
        Object messageResponse = js.get("message");
        Assert.assertEquals("Product Deleted Successfully", messageResponse);

        System.out.println(messageResponse);

    }
}
