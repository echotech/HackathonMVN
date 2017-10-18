package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

/**
 * Created by jreisner on 10/18/2017.
 */
public class TestBase {
    protected static FirefoxDriver driver;

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.gecko.driver", "C:\\\\geckodriver\\geckodriver.exe");
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        driver.close();
    }

    public static FirefoxDriver getDriver(){
        return driver;
    }
}
