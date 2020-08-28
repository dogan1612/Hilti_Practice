import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Driver {

    private static WebDriver driver;
    private static String browserType;

    public static WebDriver getDriver(){
        return getDriver("false");
    }

    public synchronized static WebDriver getDriver(String browserType) {
        browserType = browserType == null ? getParameter("browser") : browserType;

        switch (browserType) {
            case "false":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "true":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
                break;
        }
        return driver;
    }

    public  static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static String getParameter(String name) {
        String value = System.getProperty(name);
        if (value == null)
            throw new RuntimeException(name + " is not a parameter!");

        if (value.isEmpty())
            throw new RuntimeException(name + " is empty!");

        return value;
    }

}


