package runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

    @RunWith(Cucumber.class)
    @CucumberOptions(
            features = "src/test/resources/Feature Files",
            glue = {"action"},
            monochrome = true,
            publish = true
    )

    public class apiRunnerClass {

    }

