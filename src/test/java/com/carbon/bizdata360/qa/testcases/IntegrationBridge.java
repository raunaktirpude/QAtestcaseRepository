package com.carbon.bizdata360.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.carbon.bizdata360.qa.base.Base2;

public class IntegrationBridge extends Base2 {

	public IntegrationBridge() {
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
	public void verifyCreateBridgeWithMandatoryFields() {

		// Enter valid credentials
		driver.findElement(By.id("login_username")).sendKeys(prop.getProperty("validUsername"));
		driver.findElement(By.id("login_password")).sendKeys(prop.getProperty("validPassword"));

		// Select remember me checkbox
		driver.findElement(By.id("login_checkbox-input")).click();

		// Submit login form
		driver.findElement(By.id("login_submit")).click();

		// Select eZintegration
		driver.findElement(By.id("dashboard_ib_list")).click();
	}
}
