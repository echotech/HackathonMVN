package tests;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import pages.HomePage;

/**
 * Created by jreisner on 10/18/2017.
 */
public class Tests extends TestBase {

    @Test
    public void titleTest(){
        HomePage homePage = new HomePage(driver);
        System.out.println(homePage.getTitle().toString());
    }

    @Test(priority=1)
    public void storiesTest(){
        HomePage homePage = new HomePage(driver);

        homePage.clickStories();
    }


}
