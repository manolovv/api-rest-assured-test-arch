package resources;

public enum APIResources {

    UserLoginAPI("/api/ecom/auth/login"),
    AddProductAPI("/api/ecom/product/add-product"),
    CreateOrderAPI("/api/ecom/order/create-order"),
    DeleteProductAPI("/api/ecom/product/delete-product/{productId}");

    private final String resource;
    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
