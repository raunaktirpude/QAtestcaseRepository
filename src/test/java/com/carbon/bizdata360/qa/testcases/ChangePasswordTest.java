package com.carbon.bizdata360.qa.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.carbon.bizdata360.qa.base.Base2;
import com.carbon.bizdata360.qa.utils.Utilities;

public class ChangePasswordTest extends Base2 {

    public ChangePasswordTest() {
        super();
    }

    public WebDriver driver;

    @BeforeMethod
    public void setUp() throws Throwable {
        // Initialize the WebDriver and open the application URL
        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
    }
    
    @AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

    @Test(priority = 1)
    public void verifyChangePasswordWithMandatoryFields() throws Exception {

        Properties changePasswordProp = new Properties();
        FileInputStream fis = new FileInputStream(
                "C:\\Users\\Raunak MilindTripude\\git\\QAtestcaseRepository\\src\\main\\java\\com\\carbon\\bizdata360\\qa\\testdata\\changePasswordTestdata.properties");
        changePasswordProp.load(fis);

        // Enter valid credentials
        driver.findElement(By.id("login_username")).sendKeys(changePasswordProp.getProperty("loginUsername"));
        driver.findElement(By.id("login_password")).sendKeys(changePasswordProp.getProperty("changePassword"));

        // Select remember me checkbox
        WebElement Checkbox = driver.findElement(By.id("login_checkbox-input"));
        Checkbox.click();

        // Submit login form
        WebElement submitButton = driver.findElement(By.id("login_submit"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement settingButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//*[@id=\"main-content\"]/app-base-app/div/app-header/ion-header/ion-toolbar/div/div[3]/div/button/span[4]")));
        settingButton.click();

        WebElement changePasswordButton = driver.findElement(By.xpath("//div[normalize-space()='Change Password']"));
        changePasswordButton.click();

        WebElement oldPassword = driver.findElement(By.xpath("//input[@placeholder='Old Password']"));
        oldPassword.sendKeys(changePasswordProp.getProperty("changePassword"));

        // Generate the new password and store it in a variable
        String newPasswordValue = Utilities.generateChangePasswordWithRandomInt();

        WebElement newPassword = driver.findElement(By.xpath("//input[@formcontrolname='newPassword']"));
        newPassword.sendKeys(newPasswordValue);

        WebElement confirmPassword = driver.findElement(By.xpath("//input[@formcontrolname='confirmPassword']"));
        confirmPassword.sendKeys(newPasswordValue);

        changePasswordProp.setProperty("changePassword", newPasswordValue);

        FileOutputStream fos = new FileOutputStream(
                "C:\\Users\\Raunak MilindTripude\\git\\QAtestcaseRepository\\src\\main\\java\\com\\carbon\\bizdata360\\qa\\testdata\\changePasswordTestdata.properties");
        changePasswordProp.store(fos, null);

        // Close FileInputStream and FileOutputStream
        fis.close();
        fos.close();

        WebElement changePasswordSubmit = driver.findElement(By.xpath(
                "//*[@id=\"main-content\"]/app-base-app/div/div/app-change-password-new/ion-content/div/div/div/div/mat-card/mat-card-content/div/form/div/button/span[2]"));
        changePasswordSubmit.click();

        // Add wait for the login URL to load
        wait.until(ExpectedConditions.urlToBe(prop.getProperty("loginUrl")));

        // Assertion to verify the URL after click on change password submit
        Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("loginUrl"),
                "The URL after click on sign in is not as expected");
    }
    
    @Test(priority = 2)
    public void verifyChangePasswordWithEmptyMandatoryFields() throws Exception {
        Properties changePasswordProp = new Properties();
        try (FileInputStream fis = new FileInputStream("C:\\Users\\Raunak MilindTripude\\git\\QAtestcaseRepository\\src\\main\\java\\com\\carbon\\bizdata360\\qa\\testdata\\changePasswordTestdata.properties")) {
            changePasswordProp.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Enter valid credentials
        driver.findElement(By.id("login_username")).sendKeys(changePasswordProp.getProperty("loginUsername"));
        driver.findElement(By.id("login_password")).sendKeys(changePasswordProp.getProperty("changePassword"));

        // Select remember me checkbox
        WebElement checkbox = driver.findElement(By.id("login_checkbox-input"));
        checkbox.click();

        // Submit login form
        WebElement submitButton = driver.findElement(By.id("login_submit"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        //click on setting icon
        WebElement settingButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//*[@id=\"main-content\"]/app-base-app/div/app-header/ion-header/ion-toolbar/div/div[3]/div/button/span[4]")));
        settingButton.click();

        //click on change password icon
        WebElement changePasswordButton = driver.findElement(By.xpath("//div[normalize-space()='Change Password']"));
        changePasswordButton.click();
        
         //click on change password submit button
        WebElement changePasswordSubmit = driver.findElement(By.xpath(
                "//*[@id=\"main-content\"]/app-base-app/div/div/app-change-password-new/ion-content/div/div/div/div/mat-card/mat-card-content/div/form/div/button/span[2]"));
        changePasswordSubmit.click();

        // New password field error message verification
        WebElement newPasswordFieldError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-error[@aria-atomic='true']")));
        String newPasswordFieldErrorMessage = newPasswordFieldError.getText();
        String expectedNewPasswordFieldErrorMessage = changePasswordProp.getProperty("newPasswordError");
        Assert.assertTrue(newPasswordFieldErrorMessage.contains(expectedNewPasswordFieldErrorMessage),
                "Actual message: '" + newPasswordFieldErrorMessage + "' does not contain expected message: '"
                        + expectedNewPasswordFieldErrorMessage + "'");

        // Assertion to verify the URL after click on change password submit button in
        Assert.assertEquals(driver.getCurrentUrl(), changePasswordProp.getProperty("changePasswordUrl"),
                "The URL after clicking on sign in is not as expected");
    }
    
    @Test(priority = 3)
    public void verifyChangePasswordWithSamePassword() throws Exception {
        Properties changePasswordProp = new Properties();
        try (FileInputStream fis = new FileInputStream("C:\\Users\\Raunak MilindTripude\\git\\QAtestcaseRepository\\src\\main\\java\\com\\carbon\\bizdata360\\qa\\testdata\\changePasswordTestdata.properties")) {
            changePasswordProp.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Enter valid credentials
        driver.findElement(By.id("login_username")).sendKeys(changePasswordProp.getProperty("loginUsername"));
        driver.findElement(By.id("login_password")).sendKeys(changePasswordProp.getProperty("changePassword"));

        // Select remember me checkbox
        WebElement checkbox = driver.findElement(By.id("login_checkbox-input"));
        checkbox.click();

        // Submit login form
        WebElement submitButton = driver.findElement(By.id("login_submit"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        //click on setting icon
        WebElement settingButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//*[@id=\"main-content\"]/app-base-app/div/app-header/ion-header/ion-toolbar/div/div[3]/div/button/span[4]")));
        settingButton.click();

        //click on change password icon
        WebElement changePasswordButton = driver.findElement(By.xpath("//div[normalize-space()='Change Password']"));
        changePasswordButton.click();
        
        //Click on oldPassword 
        WebElement oldPassword = driver.findElement(By.xpath("//input[@placeholder='Old Password']"));
        oldPassword.sendKeys(changePasswordProp.getProperty("changePassword"));
        
      //Click on newPassword
        WebElement newPassword = driver.findElement(By.xpath("//input[@formcontrolname='newPassword']"));
        newPassword.sendKeys(changePasswordProp.getProperty("changePassword"));
      
        //Click on confirmPassword
        WebElement confirmPassword = driver.findElement(By.xpath("//input[@formcontrolname='confirmPassword']"));
        confirmPassword.sendKeys(changePasswordProp.getProperty("changePassword"));
        
         //click on change password submit button
        WebElement changePasswordSubmit = driver.findElement(By.xpath(
                "//*[@id=\"main-content\"]/app-base-app/div/div/app-change-password-new/ion-content/div/div/div/div/mat-card/mat-card-content/div/form/div/button/span[2]"));
        changePasswordSubmit.click();
        
     // Assertion to verify the URL after click on change password submit button in
        Assert.assertEquals(driver.getCurrentUrl(), changePasswordProp.getProperty("changePasswordUrl"),
                "The URL after clicking on sign in is not as expected");
        
         Thread.sleep(1000);
        // Take a screenshot of the current state, including the pop-up message
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationFile = new File("C:\\Users\\Raunak MilindTripude\\git\\QAtestcaseRepository\\Screenshot\\verifyChangePasswordWithSamePassword.png");
        FileHandler.copy(screenshot, destinationFile);

        
    }
}
