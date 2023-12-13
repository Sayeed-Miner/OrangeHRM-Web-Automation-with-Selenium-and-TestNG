package testrunner;

import com.github.javafaker.Faker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.DashboardPage;
import page.LoginPage;
import page.PIMPage;
import page.UserModel;
import setup.Setup;
import utils.Utils;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.Random;

public class EmployeeTestRunner extends Setup {
    @BeforeTest(groups = "smoke")
    public void doLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin", "admin123");
    }

    @Test(priority = 1, description = "Employee cannot be created by admin without username")
    public void createEmployeeWithoutUsername() throws InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        pimPage.pimMenu.get(1).click();
        pimPage.button.get(2).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-main-title")));
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        pimPage.textElm.get(1).sendKeys(firstName);
        String lastName = faker.name().lastName();
        pimPage.textElm.get(3).sendKeys(lastName);
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait2.until(ExpectedConditions.elementToBeClickable(By.className("oxd-switch-input")));
        driver.findElement(By.className("oxd-switch-input")).click();
        String password = randomStrongPassword();
        pimPage.textElm.get(6).sendKeys(password);
        pimPage.textElm.get(7).sendKeys(password);
        driver.findElements(By.className("oxd-button--medium")).get(1).click();
        String titleActual = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        String titleExpected = "Required";
        Assert.assertEquals(titleActual, titleExpected);
    }

    @Test(priority = 2, description = "Employee cannot be created by admin without password")
    public void createEmployeeWithoutPassword() throws InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        pimPage.pimMenu.get(1).click();
        pimPage.button.get(2).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-main-title")));
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        pimPage.textElm.get(1).sendKeys(firstName);
        String lastName = faker.name().lastName();
        pimPage.textElm.get(3).sendKeys(lastName);
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait2.until(ExpectedConditions.elementToBeClickable(By.className("oxd-switch-input")));
        driver.findElement(By.className("oxd-switch-input")).click();
        String username = faker.name().username() + Utils.generateRandomId(100,999);
        pimPage.textElm.get(5).sendKeys(username);
        driver.findElements(By.className("oxd-button--medium")).get(1).click();
        String titleActual = driver.findElements(By.className("oxd-input-field-error-message")).get(0).getText();
        String titleExpected = "Required";
        Assert.assertEquals(titleActual, titleExpected);
    }

    @Test(priority = 3, description = "Employee is created successfully")
    public void createEmployee() throws InterruptedException, IOException, ParseException, org.json.simple.parser.ParseException {
        driver.findElements(By.className("oxd-main-menu-item--name")).get(1).click();
        PIMPage pimPage = new PIMPage(driver);
        driver.findElements(By.className("oxd-button--medium")).get(2).click();
        String titleActual = driver.findElements(By.className("orangehrm-main-title")).get(0).getText();
        String titleExpected = "Add Employee";
        Assert.assertEquals(titleActual,titleExpected);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        driver.findElement(By.name("firstName")).sendKeys(firstName);
        driver.findElement(By.name("lastName")).sendKeys(lastName);
        String employeeid = pimPage.textElm.get(4).getAttribute("value");
        driver.findElement(By.className("oxd-switch-input")).click();
        String username = faker.name().username()+ Utils.generateRandomId(100,999);
        String password = randomStrongPassword();
        pimPage.textElm.get(5).sendKeys(username);
        pimPage.textElm.get(6).sendKeys(password);
        pimPage.textElm.get(7).sendKeys(password);
        driver.findElements(By.className("oxd-button--medium")).get(1).click();
        Thread.sleep(7000);
        String titleActuall = driver.findElements(By.className("orangehrm-main-title")).get(0).getText();
        String titleExpectedd = "Personal Details";
        UserModel model = new UserModel();
        model.setFirstname(firstName);
        model.setLastname(lastName);
        model.setEmployeeid(employeeid);
        model.setUsername(username);
        model.setPassword(password);
        Utils.saveInfo(model);
        Assert.assertTrue(titleActuall.contains(titleExpectedd));
    }

    @Test(priority = 4, description = "Search employee by invalid id from dashboard")
    public void searchEmployeeByInvalidID() throws IOException, ParseException, InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        pimPage.pimMenu.get(1).click();//PIM
        pimPage.textElm.get(1).sendKeys("18");
        Thread.sleep(2000);
        pimPage.button.get(1).click();
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-text--span")));
        String textActual = driver.findElements(By.className("oxd-text")).get(14).getText();
        System.out.println(textActual);
        String textExpected = "No Records Found";
        Assert.assertTrue(textActual.contains(textExpected));
        Thread.sleep(7000);
    }

    @Test(priority = 5, description = "Search employee by invalid name from dashboard")
    public void searchEmployeeByInvalidName() throws IOException, ParseException, InterruptedException {
        PIMPage pimPage = new PIMPage(driver);
        pimPage.pimMenu.get(8).click();
        driver.findElements(By.tagName("input")).get(1).sendKeys("joy");
        pimPage.button.get(1).click();
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-text--span")));
        String textActual = driver.findElements(By.className("oxd-text")).get(14).getText();
        System.out.println(textActual);
        String textExpected = "Invalid";
        Assert.assertTrue(textActual.contains(textExpected));
        Thread.sleep(7000);
    }

    @Test(priority = 6, description = "Search employee by valid id", groups = "smoke")
    public void searchEmployeeByID() throws IOException, ParseException, InterruptedException, org.json.simple.parser.ParseException {
        PIMPage pimPage = new PIMPage(driver);
        pimPage.pimMenu.get(1).click();//PIM
        JSONArray empArray = Utils.readJSONArray("./src/test/resources/Employees.json");
        JSONObject empObj = (JSONObject) empArray.get(empArray.size()-1);
        String employeeId = empObj.get("employeeid").toString();
        pimPage.textElm.get(1).sendKeys(employeeId);
        Thread.sleep(2000);
        pimPage.button.get(1).click();
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-text--span")));
        String textActual = driver.findElements(By.className("oxd-text")).get(14).getText();
        System.out.println(textActual);
        String textExpected = "Record Found";
        Assert.assertTrue(textActual.contains(textExpected));
        Thread.sleep(7000);
    }

    @Test(priority = 7, description = "Search employee by valid name")
    public void searchEmployeeByName() throws IOException, ParseException, InterruptedException, org.json.simple.parser.ParseException {
        PIMPage pimPage = new PIMPage(driver);
        pimPage.pimMenu.get(8).click();
        JSONArray empArray = Utils.readJSONArray("./src/test/resources/Employees.json");
        JSONObject empObj = (JSONObject) empArray.get(empArray.size()-1);
        String employeeName = empObj.get("firstName").toString();
        driver.findElements(By.tagName("input")).get(1).sendKeys(employeeName);
        Thread.sleep(3000);
        driver.findElement(By.className("oxd-autocomplete-option")).click();
        Thread.sleep(2000);
        pimPage.button.get(1).click();
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-text--span")));
        String textActual = driver.findElements(By.className("oxd-text")).get(14).getText();
        System.out.println(textActual);
        String textExpected = "Record Found";
        Assert.assertTrue(textActual.contains(textExpected));
        Thread.sleep(7000);
    }

    @Test(priority = 8, description = "Admin logout", groups = "smoke")
    public void doLogout() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.doLogout();
        String textActual = driver.findElement(By.className("orangehrm-login-title")).getText();
        String testExpected = "Login";
        Assert.assertTrue(textActual.equals(testExpected));
    }

    @Test(priority = 9, description = "Logout option hidden until the user has logged in")
    public void doLogoutBeforeLogin() {
        boolean isLogoutVisible;
        try {
            WebElement btnLogout = driver.findElement(By.linkText("Logout"));
            if(btnLogout.isDisplayed()) {
                isLogoutVisible = true;
            } else {
                isLogoutVisible = false;
            }
        } catch (NoSuchElementException e) {
            isLogoutVisible = false;
        }
        Assert.assertFalse(isLogoutVisible);
    }

    public static String randomStrongPassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()_+=-";
        String combinedChars = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;
        Random random = new Random();
        int passwordLength = 8;
        String password = "";
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(combinedChars.length());
            password += combinedChars.charAt(randomIndex);
        }
        return password;
    }
}