@Smoke
Feature: Add to cart on Product Page

  Scenario Outline: Add product to cart and verify cart content
    Given User opens Hilti website for country "<country>"
    When User navigates to product page for "<product_code>"
    And User adds to cart products with following properties
      | cartridge color | pack size | quantity |
      | <color_1>       | <size_1>  | <qty_1>  |
      | <color_2>       | <size_2>  | <qty_2>  |
      | <color_3>       | <size_3>  | <qty_3>  |
    And User navigates to cart page
    Then User can see the products above are added to cart with proper quantities
    Examples:
      | country | product_code | color_1 | size_1  | qty_1 | color_2 | size_2  | qty_2 | color_3 | size_3 | qty_3 |
      | US      | r457         | Green   | 100 pc  | 1     | Red     | 1000 pc | 1    | Yellow  | 100 pc  | 3     |
      | CA      | r457         | Green   | 1000 pc | 2     | Red     | 100 pc  | 1    | Yellow  | 1000 pc | 2     |





