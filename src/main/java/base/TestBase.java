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
    protected static WebDriver driver;

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.gecko.driver", "C:\\\\geckodriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.navigate().to("http://skiutah.com");
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        //driver.close();
    }

}
