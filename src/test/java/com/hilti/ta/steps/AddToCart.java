package com.hilti.ta.steps;

import com.hilti.ta.utils.WebDriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import java.util.*;

public class AddToCart{
    private WebDriver driver = WebDriverFactory.getDriver();
    public static List<Map<String, String>> lst = new LinkedList<>();


    @When("User navigates to product page for {string}")
    public void user_navigates_to_product_page_for(String product_code) {
        driver.get(driver.getCurrentUrl()+"/"+product_code);
    }

    @When("User adds to cart products with following properties")
    public void user_adds_to_cart_products_with_following_properties(List<Map<String, String>> list) {

        for(int i =0; i<list.size(); i++) {
            WebElement color = driver.findElement(By.xpath("//span[text()='"+ list.get(i).get("cartridge color") + "']"));
            color.click();
            WebElement packSize = driver.findElement(By.xpath("//span[text()='"+ list.get(i).get("pack size") + "']"));
            packSize.click();
            WebElement quantity = driver.findElement(By.xpath("//input[@id='quantity-1']"));
            quantity.sendKeys(Keys.BACK_SPACE);
            quantity.sendKeys(list.get(i).get("quantity"));
            WebElement addToCart = driver.findElement(By.xpath("//span[@class='a-icon-cartadd ng-binding']"));
            addToCart.click();
        }
        lst.clear();
        lst.addAll(list);

    }

    @When("User navigates to cart page")
    public void user_navigates_to_cart_page() {
        driver.findElement(By.xpath("//span[text()='Shopping Cart']")).click();

    }

    @Then("User can see the products above are added to cart with proper quantities")
    public void user_can_see_the_products_above_are_added_to_cart_with_proper_quantities() {
        String color ="";
        String packSize ="";
        String quantity ="";

        for(int i =1; i<=lst.size(); i++) {
            color = driver.findElement(By.xpath("(//tbody[contains(@id,'NORMAL')]/tr[contains(@class,'row-main')])["+i+"]//td[2]//strong[1]")).getText();
            packSize = driver.findElement(By.xpath("(//tbody[contains(@id,'NORMAL')]/tr[contains(@class,'row-main')])["+i+"]//td[3]//span")).getText();
            quantity = driver.findElement(By.xpath("(//tbody[contains(@id,'NORMAL')]/tr[contains(@class,'row-main')])["+i+"]//td[6]//input[@name='quantity']")).getAttribute("value");

            Assert.assertTrue(color.contains(lst.get(i-1).get("cartridge color").toLowerCase()));
            Assert.assertEquals("Pack size not verified!", lst.get(i-1).get("pack size").trim(), packSize);
            Assert.assertEquals("Quantity not verified!", lst.get(i-1).get("quantity").trim(), quantity);
        }
    }
}


