package testrunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import page.DashboardPage;
import page.LoginPage;
import page.MyinfoPage;
import setup.Setup;
import utils.Utils;
import java.io.IOException;
import java.time.Duration;

public class UserTestRunner extends Setup {
    @Test(priority = 1, description = "User can not login with invalid username")
    public void doLoginWithInvalidUsername() throws IOException, ParseException{
        LoginPage loginPage = new LoginPage(driver);
        JSONArray empArray = Utils.readJSONArray("./src/test/resources/Employees.json");
        JSONObject empObj = (JSONObject) empArray.get(empArray.size()-1);
        String errorMessageActual = loginPage.doLoginWithWrongCreds("1wronguser", empObj.get("password").toString());
        String errorMessageExpected = "Invalid credentials";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
    }
    @Test(priority = 2, description = "User can not login with invalid password")
    public void doLoginWithInvalidPassword() throws IOException, ParseException {
        LoginPage loginPage = new LoginPage(driver);
        JSONArray empArray = Utils.readJSONArray("./src/test/resources/Employees.json");
        JSONObject empObj = (JSONObject) empArray.get(empArray.size()-1);
        String errorMessageActual = loginPage.doLoginWithWrongCreds(empObj.get("userName").toString(), "wrongpass");
        String errorMessageExpected = "Invalid credentials";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
    }
    @Test(priority = 3, description = "User can login with valid username and password", groups = "smoke")
    public void doLogin() throws IOException, ParseException {
        LoginPage loginPage = new LoginPage(driver);
        JSONArray empArray = Utils.readJSONArray("./src/test/resources/Employees.json");
        JSONObject empObj = (JSONObject) empArray.get(empArray.size()-1);
        String username = empObj.get("userName").toString();
        String password = empObj.get("password").toString();
        String nameExpected = empObj.get("firstName").toString()+ " " + empObj.get("lastName").toString();
        loginPage.doLogin(username, password);
        String nameActual = driver.findElement(By.className("oxd-userdropdown-name")).getText();
        Assert.assertEquals(nameActual, nameExpected);
    }

    @Test(priority = 4, description = "The employee's blood type should not be saved if the employee does not click the save button")
    public void saveInfoWithoutClickSaveButton() throws InterruptedException {
        MyinfoPage myInfoPage = new MyinfoPage(driver);
        myInfoPage.menuMyInfo.get(2).click();
        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-main-title")));
        Utils.doScroll(driver);
        myInfoPage.selectBloodType.get(2).click();
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).click();
        myInfoPage.btnSave.get(1).click();
        Thread.sleep(1000);
        driver.navigate().refresh();
        Thread.sleep(3000);
        String txtActual = myInfoPage.selectBloodType.get(2).getText();
        String txtExpected = "-- Select --";
        Assert.assertEquals(txtActual, txtExpected);
    }

    @Test(priority = 5, description = "Employee can select gender and blood type as 'O+' and save it")
    public void saveInfo() throws InterruptedException {
        MyinfoPage myInfoPage = new MyinfoPage(driver);
        myInfoPage.menuMyInfo.get(2).click();
        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-main-title")));
        Utils.doScroll(driver);
        myInfoPage.genderBtn.get(0).click();
        myInfoPage.btnSave.get(0).click();
        Thread.sleep(1000);
        myInfoPage.selectBloodType.get(2).click();
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).click();
        myInfoPage.btnSave.get(1).click();
        Thread.sleep(1000);
        String txtActual = myInfoPage.selectBloodType.get(2).getText();
        String txtExpected = "O+";
        Assert.assertEquals(txtActual, txtExpected);
    }

    @Test(priority = 8, description = "Employee can update blood type 'AB+' and logout", groups = "smoke")
    public void updateBloodType() throws InterruptedException {
        MyinfoPage myInfoPage = new MyinfoPage(driver);
        myInfoPage.menuMyInfo.get(2).click();
        Thread.sleep(3000);
        Utils.doScroll(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-main-title")));
        myInfoPage.selectBloodType.get(2).click();
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).sendKeys(Keys.ARROW_DOWN);
        myInfoPage.selectBloodType.get(2).click();
        myInfoPage.btnSave.get(1).click();
        Thread.sleep(1000);
        String txtActual = myInfoPage.selectBloodType.get(2).getText();
        String txtExpected = "AB-";
        Assert.assertEquals(txtActual, txtExpected);
    }
    @AfterTest(groups = "smoke")
    public void doLogout() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.doLogout();
    }
}