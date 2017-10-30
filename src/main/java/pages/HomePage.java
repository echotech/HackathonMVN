package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class HomePage {
    private WebDriver driver;
    private List<String> crawlQueue = new LinkedList<String>();
    private List<String> alreadyVisited = new ArrayList<String>();
    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<String> textList = new ArrayList<String>();


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

    @FindBy(xpath = "//a[contains(text(), 'Get Directions')]")
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

    @FindBy(xpath = "//body")
    List<WebElement> allText;

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
    public void crawlLinks() throws Exception{
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
                    getAllText();
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
        makeDictionary();
    }

    //Challenge 7: Get all text on the site and count occurrences of each word.
    public void getAllText() throws Exception {


        //Adds all text to an ArrayList
        for (WebElement e : allText) {
            textList.add(e.getText());
        }

    }

    //Turns all text on all pages into a dictionary file.
    public void makeDictionary() throws Exception{
        //Outputs the ArrayList to a text file.
        Path out = Paths.get("raw.txt");
        Files.write(out, textList, Charset.defaultCharset());

        //Split the contents into a new ArrayList
        Scanner file = new Scanner(out);

        while (file.hasNext()) {
            String next = file.next().toLowerCase();
            next = next.replaceAll("[^a-zA-Z]", "");
            if (!next.isEmpty()) {
                words.add(next);
                Collections.sort(words);
            }
        }
        //Outputs the ArrayList to a text file.
        Path sortedOut = Paths.get("sortedoutput.txt");
        Files.write(sortedOut, words, Charset.defaultCharset());

        //Takes text file and counts the number of times each word occurs.
        Scanner counting = new Scanner(sortedOut);
        Map<String, Integer> dictionary = new TreeMap<String, Integer>();
        while (counting.hasNext()) {
            String entry = counting.next();
            if (!dictionary.containsKey(entry)) {
                dictionary.put(entry, 0);
            }
            dictionary.put(entry, dictionary.get(entry) + 1);
        }

        ArrayList<String> countedList = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
            String res = "'" + entry.getKey() + "'" + " occurs " + entry.getValue() + " times";
            countedList.add(res);
        }

        Path dictFile = Paths.get("dictionary.txt");
        Files.write(dictFile, countedList, Charset.defaultCharset());
    }


}
