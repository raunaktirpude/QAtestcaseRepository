package com.carbon.bizdata360.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.carbon.bizdata360.qa.base.Base2;
import com.carbon.bizdata360.qa.utils.Utilities;

public class UserManagement extends Base2 {

    public UserManagement() {
        super();
    }

    public WebDriver driver;

    @BeforeMethod
    public void setUp() throws Throwable {
        // Initialize the WebDriver and open the application URL
        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
    }

    /*
     * @AfterMethod public void tearDown() { if (driver != null) { driver.quit(); }
     * }
     */

    @Test(priority = 1)
    public void verifyCreateUser() throws Throwable {
        // Enter valid credentials
        driver.findElement(By.id("login_username")).sendKeys(prop.getProperty("validUsername"));
        driver.findElement(By.id("login_password")).sendKeys(prop.getProperty("validPassword"));

        // Select remember me checkbox
        driver.findElement(By.id("login_checkbox-input")).click();

        // Submit login form
        driver.findElement(By.id("login_submit")).click();

        // Wait for the shadow host menu button element to be present
        WebDriverWait menuButtonWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement menuShadowHost = menuButtonWait
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#head_ham_btn")));

        // Use JavaScript to get the shadow root
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object shadowRoot = js.executeScript("return arguments[0].shadowRoot", menuShadowHost);

        // Wait for the element inside the shadow root to be present and clickable
        WebElement buttonSpan = (WebElement) js.executeScript("return arguments[0].querySelector('button > span')", shadowRoot);
        menuButtonWait.until(ExpectedConditions.elementToBeClickable(buttonSpan));

        // Click the element inside the shadow root
        buttonSpan.click();

        // Click on management
        WebElement managementButton = menuButtonWait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ion-label[normalize-space()='Management']")));
        managementButton.click();

        // Click on user option
        WebElement userButton = menuButtonWait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ion-label[normalize-space()='User']")));
        userButton.click();

        // Wait for the side panel to disappear
        //menuButtonWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#head_ham_btn")));
       
     // Wait for the loader or page stabilization
        // Example: Wait for an element to become invisible (replace with appropriate loader element)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("ion-backdrop.sc-ion-loading-md.md.backdrop-no-tappable.hydrated")));

        // Wait for the "Add" button to be visible and clickable
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Add']")));

        // Click the "Add" button
        addButton.click();
        
        //Fill up add user form
        //First name
        WebElement firstName = driver.findElement(By.xpath("//input[@placeholder='First name']"));
        firstName.sendKeys("Testing");
        
        //Last name
        WebElement lastName = driver.findElement(By.xpath("//input[@placeholder='Last name']"));
        lastName.sendKeys("User1");
        
        //Eamil address
        WebElement emailAddress = driver.findElement(By.xpath("//input[@placeholder='Email Address']"));
        emailAddress.sendKeys(Utilities.generateEmailWithTimeStamp());
        
        //User permission
        WebElement permission = driver.findElement(By.xpath("//mat-select[@formcontrolname='permission']"));
        permission.click();
        WebElement listUser = driver.findElement(By.xpath("//mat-option[@role='option']//span[contains(text(),'List (User)')]"));
        listUser.click();
        WebElement listIntegraiton = driver.findElement(By.xpath("//mat-option[@role='option']//span[contains(text(),'List (Integration Bridge)')]"));
        //driver.findElement(By.xpath("//body/div/div[1]")).click();
        
        //User Group
        WebElement userGroup = driver.findElement(By.xpath("//mat-select[@formcontrolname='userGroup']"));
        userGroup.click();
        WebElement defaultGroup =driver.findElement(By.xpath("//span[normalize-space()='DEFAULT GROUP']"));
        defaultGroup.click();
        
        //Analytics permission
        WebElement analyticsPermission = driver.findElement(By.xpath("//mat-label[normalize-space()='Select Analytics Permission']"));
        analyticsPermission.click();
        WebElement dashboardView = driver.findElement(By.xpath("//span[normalize-space()='Dashboard View']"));
        dashboardView.click();
        
        //click on add button
        WebElement userAddButton = driver.findElement(By.xpath("//span[normalize-space()='Add']"));
        //userAddButton.click();
        
    }
    
}
