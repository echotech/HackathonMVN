package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

public class MapsPage {
    private WebDriver driver;
    public MapsPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"sb_ifc50\"]/input")
    WebElement googleInput;

    @FindBy(xpath = "//*[@id=\"pane\"]/div/div[2]/div/div/div[5]/div[1]/div[2]/div[1]/div[1]/div[1]/span[1]")
    WebElement airportTime;

    @FindBy(xpath = "//*[@id=\"pane\"]/div/div[2]/div/div/div[5]/div[1]/div[2]/div[1]/div[1]/div[2]/div")
    WebElement airportDistance;

    public String calcDistance(){
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        googleInput.sendKeys("Salt Lake City International Airport");
        googleInput.sendKeys(Keys.RETURN);

        return airportDistance.getText();
    }
}
