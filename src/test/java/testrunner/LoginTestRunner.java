package testrunner;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.Assert;
import page.DashboardPage;
import page.LoginPage;
import setup.Setup;

public class LoginTestRunner extends Setup {
    LoginPage loginPage;
    DashboardPage dashBoardPage;
    @Test(priority = 1, description = "Admin login with incorrect username and password")
    public void doLoginWithWrongCreds(){
        loginPage = new LoginPage(driver);
        String actualmessage = loginPage.doLoginWithWrongCreds("wrongusernam", "wrongpassword");
        String expectedmessage = "Invalid credentials";
        Assert.assertTrue(expectedmessage.contains(actualmessage));
    }
    @Test(priority = 2, description = "Admin login with correct username and password",groups = "smoke")
    public void doLogin(){
        loginPage = new LoginPage(driver);
        loginPage.doLogin("Admin", "admin123");
        dashBoardPage = new DashboardPage(driver);
        Assert.assertTrue(dashBoardPage.imgProfile.isDisplayed());
    }
    @Test(priority = 3, description = "Admin logout",groups = "smoke")
    public void doLogout() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.doLogout();
        String textActual = driver.findElement(By.className("orangehrm-login-title")).getText();
        String testExpected = "Login";
        Assert.assertTrue(textActual.equals(testExpected));
    }
}