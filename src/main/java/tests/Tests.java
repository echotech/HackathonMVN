package tests;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

/**
 * Created by jreisner on 10/18/2017.
 */
public class Tests {
    protected static FirefoxDriver driver = TestBase.getDriver();
    @Test
    public void getTitle(){
        try {
            driver.get("http://skiutah.com");
        } catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
        } finally {
            String titleString = driver.getTitle();
            System.out.println("The pages title is" + titleString);

        }
    }

    @Test(priority=1)
    public void clickStories(){
        try {
            driver.get("http://skiutah.com");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
        } finally {
            driver.findElement(By.linkText("Stories")).click();
        }
    }


}
