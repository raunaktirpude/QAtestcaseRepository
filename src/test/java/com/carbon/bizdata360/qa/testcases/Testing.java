package com.carbon.bizdata360.qa.testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

	/*
	 * @AfterMethod public void tearDown() { if (driver != null) { driver.quit(); }
	 * }
	 */

	@Test(priority = 1)
	public void verifyCreateBridgeWithMandatoryFields() throws Throwable {

		// Enter valid credentials
		driver.findElement(By.id("login_username")).sendKeys(prop.getProperty("validUsername"));
		driver.findElement(By.id("login_password")).sendKeys(prop.getProperty("validPassword"));

		// Select remember me checkbox
		driver.findElement(By.id("login_checkbox-input")).click();

		// Submit login form
		driver.findElement(By.id("login_submit")).click();

		// Select eZintegration
		driver.findElement(By.id("dashboard_ib_list")).click();

		// Wait for loader stable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.cssSelector("ion-backdrop.sc-ion-loading-md.md.backdrop-no-tappable.hydrated")));

		// Wait for add button
		WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ib_list_add_btn")));
		addButton.click();

		// Select API as data source
		WebElement dataSource = driver.findElement(By.id("ib_create_source_search_product"));
		dataSource.click();
		Thread.sleep(500);
		dataSource.sendKeys("Humanoid");

		// Select humanoid as source
		WebElement source = driver
				.findElement(By.xpath("//mat-option[@class='mat-mdc-option mdc-list-item ng-star-inserted']"));
		source.click();

		// Select business object
		WebElement sourceBusinessObject = driver.findElement(By.id("ib_create_source_search_businessObj"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true)", sourceBusinessObject);
		sourceBusinessObject.click();
		driver.findElement(By.xpath("//mat-option[@role='option']")).click();

		// click on test button
		WebElement testButton = driver.findElement(By.id("ib_create_source_endpoint_test_api_btn"));
		testButton.click();

		// wait for API response
		WebElement testResponse = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[@class='res-status ng-star-inserted']")));

		// Get the text from the element
		String actualSuccessMessage = testResponse.getText();
		String expectedSuccessMessage = "Status: 200";

		// Assert the text
		Assert.assertEquals(actualSuccessMessage, expectedSuccessMessage, "The success message does not match.");

		// click on next button
		WebElement sourceNextButton = driver.findElement(By.id("ib_create_source_dl_next"));
		jse.executeScript("arguments[0].scrollIntoView(true)", sourceNextButton);
		sourceNextButton.click();

		// Search for operation
		WebElement searchOperation = driver.findElement(By.xpath("//input[@placeholder='Search by operations']"));
		searchOperation.sendKeys("single line to multiline");

		Thread.sleep(500);
		// Initialize the Actions class
		Actions operationAction = new Actions(driver);

		// Locate the source element (the element to be dragged)
		WebElement singleLineOperation = driver.findElement(By.id("singletomultiline"));

		// Locate the target element (the element where the source element will be dropped) 
		WebElement dropOperationField = driver.findElement(By.xpath("//div[@class='cdk-drop-list timeline ng-star-inserted']"));
				
		// Perform the drag and drop operation using the dragAndDrop method		
		operationAction.clickAndHold(singleLineOperation).moveToElement(dropOperationField).build().perform();
        
        
        
		
		
					
        
        
	}
}
