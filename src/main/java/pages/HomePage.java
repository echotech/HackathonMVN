package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.*;
import java.util.*;


public class HomePage {
    private WebDriver driver;
    private List<String> crawlQueue = new LinkedList<String>();
    private List<String> alreadyVisited = new ArrayList<String>();


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@id='top_menu']/ul/li/a[text()]")
    List<WebElement> topNav;

    @FindBy(xpath = "//div[@id=\"top_menu\"]/ul/li[2]/a")
    WebElement resortsHover;

    @FindBy(xpath = "//div[@id=\"top_menu\"]/ul/li[2]/ul/li[1]/ul/li/a")
    List<WebElement> resortsList;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[3]/div/div/div[3]/div[2]/p/a")
    WebElement googleDirections;

    @FindBy(xpath = "//*[@id=\"sb_ifc50\"]/input")
    WebElement googleInput;

    @FindBy(xpath = "//*[@id=\"directions-searchbox-0\"]/div/div")
    WebElement googleSearchbox;

    @FindBy(xpath = "//*[@id=\"main-nav\"]/div[1]/div/div[2]")
    WebElement searchIcon;

    @FindBy(xpath = "//*[@id=\"main-nav\"]/div[2]/div/form/input")
    WebElement searchInput;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[2]/div/div/h2/a")
    List<WebElement> searchResults;

    @FindBy(tagName = "a")
    List<WebElement> pageLinks;

    //Challenge 1: Returns the title of the current page
    public String getTitle() {
        return driver.getTitle();
    }

    //Challenge 2: Clicks a link in the top navigation of the page based on string passed in.
    public void clickTopNav(String nav) {
        for (WebElement we : topNav) {
            //System.out.println(we.getAttribute("text"));
            String linkText = we.getAttribute("text").trim();
            if (linkText.equalsIgnoreCase(nav)) {
                we.click();
                break;
            }
        }
    }

    //Challenge 3: Hovers over each item in the top nav
    public void getSubMenu() {
        for (WebElement we : topNav) {
            Actions builder = new Actions(driver);
            Actions hoverOver = builder.moveToElement(we);
            hoverOver.perform();

        }
    }

    //Challenge 4: Will return the distance from Salt Lake International airport for the resort passed as a string
    public String resortDistance(String resort) {
        String result = "Something went wrong";
        Actions builder = new Actions(driver);
        Actions hoverOver = builder.moveToElement(resortsHover);

        for (WebElement res : resortsList) {
            hoverOver.perform();
            String resortText = res.getAttribute("text").trim();
            if (resortText.equalsIgnoreCase(resort)) {
                res.click();
                new Actions(driver).keyDown(Keys.CONTROL).click(googleDirections).keyUp(Keys.CONTROL).build().perform();
                MapsPage maps = new MapsPage(driver);
                return maps.calcDistance();
            }
        }
        return result;
    }

    //Challenge 5: Allows user to search 3 strings and returns the urls from the results in an ArrayList
    public ArrayList<String> searchResults(String query, String resort, String subcategory) {
        ArrayList<String> results = new ArrayList();
        searchIcon.click();
        searchInput.sendKeys(query + " " + resort + " " + subcategory);
        searchInput.sendKeys(Keys.RETURN);
        for (WebElement e : searchResults) {
            results.add(e.getAttribute("href"));

        }
        //System.out.println(results.size());
        return results;
    }

    //Challenge 6: Crawl a page and go to every link on the page with no duplicates.
    public void crawlLinks() {
        for (WebElement e : pageLinks) {
            try {
                //Create URI to compare links with
                URI ski = new URI("http://www.skiutah.com");
                //Creates URI from href tag
                URI test = new URI(e.getAttribute("href"));

                if (test.getHost().equals(ski.getHost())) {
                    crawlQueue.add(test.toString());
                }
            } catch (URISyntaxException url) {
                System.out.println("Malformed url " + e.getAttribute("href"));
                continue;
            } catch (NullPointerException npe) {
                continue;
            }
        }
        for (String link : crawlQueue) {
            if (!alreadyVisited.contains(link)) {
                try {
                    alreadyVisited.add(link);
                    driver.get(link);
                } catch (TimeoutException e) {
                    try {
                        crawlLinks();
                    } catch (NullPointerException npe) {
                        continue;
                    }
                }
            }
            //System.out.println("Visited " + alreadyVisited.size() + " links!");
        }
    }

}
