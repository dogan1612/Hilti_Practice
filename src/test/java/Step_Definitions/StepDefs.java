package Step_Definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StepDefs {

    private WebDriver driver = Driver.getDriver();
    private final String baseURL = "https://www.hilti.com/";
    private final String baseURLca = "https://www.hilti.ca/";
    private static String country = "";
    public static List<Map<String, String>> lst = new LinkedList<>();

    @Given("User opens Hilti website for country {string}")
    public void user_opens_hilti_website_for_country(String country) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        StepDefs.country = country;
        if (country.equalsIgnoreCase("US")){
            driver.get(baseURL);
            WebElement policy = driver.findElement(By.xpath("//button[@name='primary']"));
            if (policy.isDisplayed()) {
                policy.click();
            }
        }else if(country.equalsIgnoreCase("CA")){
            driver.get("https://www.hilti.ca");
        }else{
            System.out.println("country unknown");
        }

    }

    @When("User navigates to product page for {string}")
    public void user_navigates_to_product_page_for(String product_code) {

        if(country.contentEquals("CA")){
            driver.get(baseURLca+product_code);
        }else{
            driver.get(baseURL+product_code);
        }
    }

    @When("User adds to cart products with following properties")
    public void user_adds_to_cart_products_with_following_properties(List<Map<String, String>> list) {
// Example: [{cartridge color=Green, pack size=1000 pc, quantity=2}, {cartridge color=Red, pack size=100 pc, quantity=1}, {cartridge color=Yellow, pack size=1000 pc, quantity=2}]

        for(int i =0; i<list.size(); i++) {
            WebElement color = driver.findElement(By.xpath("//span[text()='"+ list.get(i).get("cartridge color") + "']"));
            color.click();
            WebElement packSize = driver.findElement(By.xpath("//span[text()='"+ list.get(i).get("pack size") + "']"));
            packSize.click();
            WebElement quantity = driver.findElement(By.xpath("//input[@id='quantity-1']"));
//            ((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", quantity);       // to clear the field
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

        if(country.contentEquals("CA")){
            driver.get(baseURLca+"cart");
        }else{
            driver.get(baseURL+"cart");
        }
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


