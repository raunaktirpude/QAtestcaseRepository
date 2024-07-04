package com.carbon.bizdata360.qa.testcases;

import java.io.File;
import java.time.Duration;

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

public class LoginTest extends Base2 {

	public LoginTest() {
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

	@Test(priority = 3)
	public void verifyLoginWithValidCredentials() throws Exception {

		// Enter valid credentials
		driver.findElement(By.id("login_username")).sendKeys(prop.getProperty("validUsername"));
		driver.findElement(By.id("login_password")).sendKeys(prop.getProperty("validPassword"));

		// Select remember me checkbox
		WebElement Checkbox = driver.findElement(By.id("login_checkbox-input"));
		Checkbox.click();

		// Submit login form
		WebElement submitButton = driver.findElement(By.id("login_submit"));
		submitButton.click();

		// Wait for the "Organization Admin" element to be visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement organizationAdminElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='profile-name']")));

		// Assert that the "Organization Admin" element is displayed
		Assert.assertTrue(organizationAdminElement.isDisplayed(),
				"Organization Admin text is not displayed after login");
		// Additional assertion to verify the URL after login
		Assert.assertEquals(driver.getCurrentUrl(), (dataProp.getProperty("dashboardPageUrl")),
				"The URL after login is not as expected");
	}

	@Test(priority = 1)
	public void verifyLoginWithInvalidUsername() throws Throwable {

		// Enter invalid username and valid password
		driver.findElement(By.id("login_username")).sendKeys(dataProp.getProperty("invalidUsername"));
		driver.findElement(By.id("login_password")).sendKeys(prop.getProperty("validPassword"));

		// Submit login form
		WebElement submitButton = driver.findElement(By.id("login_submit"));
		submitButton.click();

		Thread.sleep(1000);
		// Take a screenshot of the current state, including the pop-up message
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(
				"C:\\Users\\Raunak MilindTripude\\git\\QAtestcaseRepository\\Screenshot\\verifyLoginWithInvalidUsername.png");
		FileHandler.copy(screenshot, destinationFile);

		// Wait for the toast message to appear
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		 * WebElement shadowHost = wait
		 * .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
		 * "#ion-overlay-1")));
		 * 
		 * // Initialize JavaScriptExecutor JavascriptExecutor jse =
		 * (JavascriptExecutor) driver;
		 * 
		 * // Access the shadow DOM and get the element within it WebElement
		 * invalidUsernameMessage = (WebElement) jse
		 * .executeScript("return arguments[0].shadowRoot.querySelector('div.toast-content > div')"
		 * , shadowHost);
		 * 
		 * // Get the actual error message text String actualMessage =
		 * invalidUsernameMessage.getText().trim();
		 * 
		 * // Print the actual error message System.out.println("Actual error message: "
		 * + actualMessage);
		 * 
		 * // Expected error message String expectedMessage =
		 * dataProp.getProperty("invalidUserNoMatchWarning");
		 * 
		 * // Assert that the actual message matches the expected message
		 * Assert.assertTrue(actualMessage.contains(expectedMessage),
		 * "Actual message: '" + actualMessage +
		 * "' does not contain expected message: '" + expectedMessage + "'");
		 */
	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidPassword() throws Throwable {

		// Enter valid username and invalid password
		driver.findElement(By.id("login_username")).sendKeys(prop.getProperty("validUsername"));
		driver.findElement(By.id("login_password")).sendKeys(dataProp.getProperty("invalidPassword"));

		// Submit login form
		WebElement submitButton = driver.findElement(By.id("login_submit"));
		submitButton.click();

		// Wait for the Attempt remaining text element to be visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement attemptRemaining = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='error-message ng-star-inserted']")));

		// Assert that the Attempt remaining text element is displayed
		Assert.assertTrue(attemptRemaining.isDisplayed(),
				"Attempt remaining text is not displayed after click on login");

		Thread.sleep(1000);
		// Take a screenshot of the current state, including the pop-up message
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(
				"C:\\Users\\Raunak MilindTripude\\git\\QAtestcaseRepository\\Screenshot\\verifyLoginWithInvalidPassword.png");
		FileHandler.copy(screenshot, destinationFile);

		// Wait for the shadow host of the toast message to appear
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		 * WebElement shadowHost = wait
		 * .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
		 * "#ion-overlay-1")));
		 * 
		 * // Initialize JavaScriptExecutor JavascriptExecutor jse =
		 * (JavascriptExecutor) driver;
		 * 
		 * // Access the shadow DOM and get the element within it WebElement
		 * toastContent = (WebElement) jse.executeScript(
		 * "return arguments[0].shadowRoot.querySelector('div > div > div.toast-content')"
		 * , shadowHost);
		 * 
		 * // Get the actual error message text String actualMessage =
		 * toastContent.getText().trim();
		 * 
		 * // Print the actual error message System.out.println("Actual error message: "
		 * + actualMessage);
		 * 
		 * // Expected error message String expectedMessage =
		 * dataProp.getProperty("invalidPasswordNoMatchWarning");
		 * 
		 * // Assert that the actual message matches the expected message
		 * Assert.assertTrue(actualMessage.contains(expectedMessage),
		 * "Actual message: '" + actualMessage +
		 * "' does not contain expected message: '" + expectedMessage + "'");
		 */
	}

	@Test(priority = 4)
	public void verifyAllHyperLink() {

		// Verify forgot password link
		WebElement forgotLink = driver.findElement(By.id("login_forgot_password"));
		forgotLink.click();

		// Wait for the "forgot password page" element to be visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement forgotPageText = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Reset Your Password']")));

		// Assert that the "Forgot password" element is displayed
		Assert.assertTrue(forgotPageText.isDisplayed(),
				"Reset your password text is not displayed after click on forgot password link");

		// Navigate back to login page
		driver.navigate().back();

		// Verify sign in link
		WebElement signinLink = driver.findElement(By.xpath("//a[normalize-space()='Try it here']"));
		signinLink.click();

		// Wait for the "sign in page" element to be visible
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement signinPageText = wait2.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[normalize-space()='Real Time Cloud Based Integration Platform']")));

		// Assert that the "Sign in page" element is displayed
		Assert.assertTrue(signinPageText.isDisplayed(),
				"Real time cloud based integration platform text is not displayed after click on sign in link");

	}
}