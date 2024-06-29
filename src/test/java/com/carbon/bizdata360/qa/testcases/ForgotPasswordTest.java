package com.carbon.bizdata360.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.carbon.bizdata360.qa.base.Base2;

public class ForgotPasswordTest extends Base2 {

	public ForgotPasswordTest() {
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
	public void verifyForgotPasswordFunctionality() throws Exception {
		// Navigate to login page
		driver.get(prop.getProperty("loginUrl"));

		// Click on forgot password link
		WebElement forgotLink = driver.findElement(By.id("login_forgot_password"));
		forgotLink.click();

		// Provide the email adderess
		WebElement emailField = driver.findElement(By.xpath("//input[@placeholder=' Email']"));
		emailField.sendKeys(prop.getProperty("validUsername"));
		WebElement submitbutton = driver.findElement(By.xpath(
				"//*[@id=\"main-content\"]/app-forgot-password/ion-content/div/div/div/div/mat-card/mat-card-content/div[2]/div[2]/div[3]/form/div/button/span[2]"));
		submitbutton.click();

		// Wait for the password reset text element to be visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement resetText = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='text']")));

		// Assert that the "Organization Admin" element is displayed
		Assert.assertTrue(resetText.isDisplayed(), "Password reset text is not displayed after login");

	}

	@Test(priority = 2)
	public void verifySignInURL() {
	    // Navigate to login page
	    driver.get(prop.getProperty("loginUrl"));

	    // Click on forgot password link
	    WebElement forgotLink = driver.findElement(By.id("login_forgot_password"));
	    forgotLink.click();

	    // Wait for the "Sign in" link to be present
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    WebElement signLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Sign in']")));

	    // Use JavascriptExecutor to click on the "Sign in" link
	    JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("arguments[0].click();", signLink);

	    // Wait for the URL to change to the expected login URL
	    wait.until(ExpectedConditions.urlToBe(prop.getProperty("loginUrl")));

	    // Assertion to verify the URL after click on sign-in link
	    Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("loginUrl"),
	            "The URL after login is not as expected");
	}

	@Test(priority = 3)
	public void emptyForgotPasswordField() {
	    // Navigate to login page
	    driver.get(prop.getProperty("loginUrl"));

	    // Click on forgot password link
	    WebElement forgotLink = driver.findElement(By.id("login_forgot_password"));
	    forgotLink.click();

	    // Wait for the submit button to be clickable
	    WebDriverWait submitWait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    WebElement submitButton = submitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(
	            "//*[@id=\"main-content\"]/app-forgot-password/ion-content/div/div/div/div/mat-card/mat-card-content/div[2]/div[2]/div[3]/form/div/button/span[2]")));

	    // Click the submit button
	    submitButton.click();
	    
	 // Wait for the popup to appear
	   /* WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ion-overlay-1")));

	    // Get the shadow root of the popup
	    WebElement shadowRoot = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", popup);

	    // Find the element containing the message
	    WebElement messageElement = shadowRoot.findElement(By.cssSelector("div > div > div > div"));

	    // Get the text of the message
	    String actualMessage = messageElement.getText().trim();

	    // Define expected message
	    String expectedMessage = "Form incomplete";

	    // Assert that the actual message matches the expected message
	    Assert.assertEquals(actualMessage, expectedMessage, "Actual and expected messages do not match");*/
	}
}