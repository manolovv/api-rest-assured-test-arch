package resources;
import org.example.pojo.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestDataBuilder {

    public LoginRequest getUserDetails(String userEmail, String userPassword) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(userEmail);
        loginRequest.setUserPassword(userPassword);
        return loginRequest;
    }

    public CreateProductDetails getProductDetails(String productAddedBy) {

        CreateProductDetails productDetails = new CreateProductDetails();

        productDetails.setProductName("nadaren");
        productDetails.setProductAddedBy(productAddedBy);
        productDetails.setProductCategory("dadenosti");
        productDetails.setProductSubCategory("kofeni");
        productDetails.setProductPrice("999999");
        productDetails.setProductDescription("Golemec");
        productDetails.setProductFor("MEN");
        productDetails.setFile(new File("\\Users\\User\\Downloads\\image.jpg"));

        return productDetails;
    }

    public OrderRequest getOrderDetails(String productId) {
        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setProductOrderedId(productId);
        orderDetails.setCountry("Bulgaria");

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrders(orderDetailsList);
        return orderRequest;
    }

    public HashMap<String, String> getProductParams(CreateProductDetails productDetails) {

        HashMap<String, String> params = new HashMap<>();

        params.put("productName", productDetails.getProductName());
        params.put("productAddedBy", productDetails.getProductAddedBy());
        params.put("productCategory", productDetails.getProductCategory());
        params.put("productSubCategory", productDetails.getProductSubCategory());
        params.put("productPrice", productDetails.getProductPrice());
        params.put("productDescription", productDetails.getProductDescription());
        params.put("productFor", productDetails.getProductFor());

        return params;
    }

}
