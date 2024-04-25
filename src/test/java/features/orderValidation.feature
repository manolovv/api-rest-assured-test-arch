Feature: Validating Order APIs

  @LoginApp @Regression
  Scenario Outline: Verify if User is being Successfully Logged
    Given Login User Payload with "<userEmail>" "<userPassword>"
    When User calls "UserLoginAPI" with "POST" http request
    Then The API call got success with status code 200
    And "message" in response body is "Login Successfully"

    Examples:
      | userEmail      | userPassword     |
      | mam96@abv.bg   | Manolov123*()    |

  @AddProduct @Regression
  Scenario: Verify if Product is being Successfully Added
    Given Product info with Params
    When Product calls "AddProductAPI" with "POST" http request
    Then The API call got success with status code 201
    And "message" in response body is "Product Added Successfully"

  @CreateOrder @Regression
  Scenario: Verify if Order is being Successfully Created
    Given Order info with json Payload
    When Order calls "CreateOrderAPI" with "POST" http request
    Then The API call got success with status code 201
    And "message" in response body is "Order Placed Successfully"

  @DeleteProduct @Regression
  Scenario: Verify if product is Successfully Deleted
    Given Delete product Payload
    When Product calls "DeleteProductAPI" with "DELETE" http request
    Then The API call got success with status code 200
    And "message" in response body is "Product Deleted Successfully"