package Cukes_Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "json:target/json/cucumber.json",
                "junit:target/junit/cucumber.xml",
                "json:target/site/cucumber-pretty",
                "rerun:target/rerun.txt"
                },
        features = {"src/test/resources/features/AddToCart.feature"},
        glue = {"Step_Definitions"},
        dryRun = false
)
public class RunCucumberTest {
}