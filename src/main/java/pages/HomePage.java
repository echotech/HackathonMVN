package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.By.linkText;

public class HomePage {
    private FirefoxDriver driver;

    public HomePage(FirefoxDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;

    }

    @FindBy(className="body")
    WebElement pageBody;

    @FindBy(className="title")
    WebElement pageTitle;

    @FindBy(partialLinkText="Stories")
    WebElement storiesLink;

    public void clickStories(){
        storiesLink.click();
    }

    public String getTitle(){
        return pageTitle.toString();
    }


}
