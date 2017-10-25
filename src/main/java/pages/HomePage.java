package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class HomePage {
    private WebDriver driver;

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

    public String getTitle() {
        return driver.getTitle();
    }

    public void getSubMenu() {
        for (WebElement we : topNav) {
            Actions builder = new Actions(driver);
            Actions hoverOver = builder.moveToElement(we);
            hoverOver.perform();

            //TODO Add logic to click the one you want.
        }
    }


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


}
