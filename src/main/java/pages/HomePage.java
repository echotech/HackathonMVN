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

    @FindBy(xpath = "//div[@id='top_menu']/ul/li/a[text()]")
    List<WebElement> topNav;

    @FindBy(xpath = "//div[@id='top_menu']/ul/li")
    List<WebElement> subMenu;

    @FindBy(xpath = "//div[@id=\"top_menu\"]/ul/li[2]/a")
    WebElement resortsHover;

    @FindBy(xpath = "//div[@id=\"top_menu\"]/ul/li[2]/ul/li[1]/ul")
    List<WebElement> resortsList;

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
