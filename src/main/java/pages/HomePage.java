package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id='top_menu']/ul/li[3]/a")
    WebElement storiesLink;

    public void clickStories() {
        storiesLink.click();
    }

    public String getTitle() {
        return driver.getTitle().toString();
    }


}
