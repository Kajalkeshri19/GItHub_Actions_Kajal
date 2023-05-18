import org.example.BrokenLink;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import resources.Base;

import java.io.IOException;

public class TestRunner {
    public WebDriver driver;
    public Base base;
    BrokenLink brokenLink;

    @BeforeTest
    public void setUp() throws IOException {
        base = new Base();
        driver = base.initializeDriver();
        brokenLink = new BrokenLink(driver, base);
    }

    @Test
    public void checkLink() throws IOException, InterruptedException {
        brokenLink.checkAllLinks();
    }
}