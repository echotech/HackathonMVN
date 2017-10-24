package pages;

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

    @FindBy(xpath = "//div[@id='top_menu']/ul/li[3]/a")
    WebElement storiesLink;

    @FindBy(xpath = "//div[@id='top_menu']/ul")
    List<WebElement> subMenu;

    @FindBy(xpath = "//div[@id=\"top_menu\"]/ul/li[2]/a")
    WebElement resortsHover;

    @FindBy(xpath = "//div[@id=\"top_menu\"]/ul/li[2]/ul/li[1]/ul")
    List<WebElement> resortsList;

    public void clickStories() {
        storiesLink.click();
    }

    public String getTitle() {
        return driver.getTitle().toString();
    }

    public void getSubMenu() {
        for (WebElement we : subMenu) {
            Actions builder = new Actions(driver);
            Actions hoverOver = builder.moveToElement(we);
            hoverOver.perform();

            //TODO Add logic to click the one you want.
        }
    }

    public String getDistance(String resort) {
        String result = "Something went wrong";
        Actions builder = new Actions(driver);
        Actions hoverOver = builder.moveToElement(resortsHover);
        hoverOver.perform();
        for (WebElement res : resortsList) {

            if ((res.toString()).equalsIgnoreCase(resort)) {
                res.click();
                result = res.toString();
                return result;
            }
        }

        return result;

    }


}
