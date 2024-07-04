package com.carbon.bizdata360.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.carbon.bizdata360.qa.base.Base2;

public class Testing extends Base2 {

	public Testing() {
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

	@Test(priority = 4, invocationCount = 5)
	public void verifyUserListToggleIcons() throws Throwable {
		// Enter valid credentials
		driver.findElement(By.id("login_username")).sendKeys(dataProp.getProperty("userMangementUser"));
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
		WebElement buttonSpan = (WebElement) js.executeScript("return arguments[0].querySelector('button > span')",
				shadowRoot);
		menuButtonWait.until(ExpectedConditions.elementToBeClickable(buttonSpan));

		// Click the element inside the shadow root
		buttonSpan.click();

		// Click on management
		WebElement managementButton = menuButtonWait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//ion-label[normalize-space()='Management']")));
		managementButton.click();

		// Click on user option
		WebElement userButton = menuButtonWait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//ion-label[normalize-space()='User']")));
		userButton.click();

		// Initialize Actions class
		Actions actions = new Actions(driver);
		actions.moveByOffset(200, 200).click().perform();

		// Wait for the loader or page stabilization
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.cssSelector("ion-backdrop.sc-ion-loading-md.md.backdrop-no-tappable.hydrated")));

		WebElement toggleButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@role='switch']")));

		// Retry mechanism for clicking the "toggle" button twice with a delay in
		// between
		for (int attempt = 0; attempt < 3; attempt++) {
			try {
				// Scroll to the "toggle" button
				js.executeScript("arguments[0].scrollIntoView(true);", toggleButton);

				// Try to click the "toggle" button twice with a delay in between
				toggleButton.click();
				Thread.sleep(500); // Wait before the second click
				toggleButton.click();
				break;
			} catch (org.openqa.selenium.ElementClickInterceptedException e) {
				System.out.println("Click intercepted, retrying...");
				Thread.sleep(1000); // Wait before retrying
			}
		}
	}
}