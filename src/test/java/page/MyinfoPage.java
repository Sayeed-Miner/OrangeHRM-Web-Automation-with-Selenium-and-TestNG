package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class MyinfoPage {
    @FindBy(className = "oxd-main-menu-item")
    public List<WebElement> menuMyInfo;
    @FindBy(className = "oxd-radio-input")
    public List<WebElement> genderBtn;
    @FindBy(className = "oxd-button--medium")
    public List<WebElement> btnSave;
    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> selectBloodType;
    public MyinfoPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}