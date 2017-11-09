package tests;

import base.TestBase;
import org.testng.annotations.Test;
import pages.HomePage;

import static org.testng.Assert.assertEquals;

/**
 * Created by jreisner on 10/18/2017.
 */
public class Tests extends TestBase {

    @Test
    public void titleTest(){
        HomePage homePage = new HomePage(driver);
        //System.out.println(homePage.getTitle());
        assertEquals( driver.getTitle(), "Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah");
    }

    @Test(priority=1)
    public void navTest(){
        HomePage homePage = new HomePage(driver);
        homePage.clickTopNav("Stories");
        //System.out.println(driver.getTitle());
        assertEquals( driver.getTitle(), "Read About the Latest Happenings on the Slopes - Ski Utah");
    }

    @Test(priority=2)
    public void submenuTest(){
        HomePage homePage = new HomePage(driver);
        homePage.getSubMenu();
    }

    @Test(priority=3)
    public void distanceTest(){
        HomePage homePage = new HomePage(driver);
        //homePage.resortDistance("snowbird");
        assertEquals( homePage.resortDistance("snowbird"), "31.9 miles");

    }

    @Test(priority=4)
    public void searchTest(){
        HomePage homePage = new HomePage(driver);

        assertEquals(16, homePage.searchResults("dining", "alta", "menu").size());
    }

    @Test(priority=5)
    public void crawlLinksTest() throws Exception{
        HomePage homePage = new HomePage(driver);
        homePage.crawlLinks();
    }

    @Test(priority=6)
    public void crawlTextTest() throws Exception{
        HomePage homePage = new HomePage(driver);
        homePage.getAllText();
    }

    @Test(priority=7)
    public void crawlImageTest() throws Exception {
        HomePage homePage = new HomePage(driver);
        homePage.crawlLinks();
        homePage.crawlImages();
    }


}
