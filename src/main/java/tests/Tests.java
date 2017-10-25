package tests;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

/**
 * Created by jreisner on 10/18/2017.
 */
public class Tests extends TestBase {

    @Test
    public void titleTest(){
        HomePage homePage = new HomePage(driver);
        System.out.println(homePage.getTitle());
        Assert.assertEquals("Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah", driver.getTitle());
    }

    @Test(priority=1)
    public void navTest(){
        HomePage homePage = new HomePage(driver);
        homePage.clickTopNav("Stories");
        System.out.println(driver.getTitle());
        Assert.assertEquals("Read About the Latest Happenings on the Slopes - Ski Utah", driver.getTitle());
    }

    @Test(priority=2)
    public void submenuTest(){
        HomePage homePage = new HomePage(driver);
        homePage.getSubMenu();
    }

    @Test(priority=3)
    public void distanceTest(){
        HomePage homePage = new HomePage(driver);
        homePage.getDistance("snowbird");
    }


}
