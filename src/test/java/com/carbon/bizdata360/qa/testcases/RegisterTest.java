package com.carbon.bizdata360.qa.testcases;

import java.awt.AWTException;
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

import com.carbon.bizdata360.qa.base.Base;
import com.carbon.bizdata360.qa.utils.Utilities;

public class RegisterTest extends Base {

	public RegisterTest() {
		super();
	}

	public WebDriver driver;

	@BeforeMethod
	public void setUp() throws AWTException {
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
	public void verifyRegisteringAnAccountWithMandatoryFields() {

		// Fill up registration form
		driver.findElement(By.id("signup_firstname")).sendKeys(dataProp.getProperty("firstName"));
		driver.findElement(By.id("signup_lastname")).sendKeys(dataProp.getProperty("lastName"));
		driver.findElement(By.xpath("//input[@placeholder='Work email Address']"))
				.sendKeys(Utilities.generateEmailWithTimeStamp());
		driver.findElement(By.id("signup_phone")).sendKeys(dataProp.getProperty("telePhoneNumber"));

		// Handle Job title dropdown
		WebElement signupJob = driver.findElement(By.id("signup_job"));
		signupJob.click();

		// Select the desired option
		WebElement jobOption = driver.findElement(By.xpath("//mat-option[@id='mat-option-1']"));
		jobOption.click();

		// Handle Employee dropdown
		WebElement signupEmployee = driver.findElement(By.id("signup_employee"));
		signupEmployee.click();

		// Select the desired option
		WebElement employeeOption = driver.findElement(By.xpath("//mat-option[@id='mat-option-10']"));
		employeeOption.click();

		// Select company
		WebElement signupCompany = driver.findElement(By.id("signup_company"));
		signupCompany.sendKeys(dataProp.getProperty("companyName"));

		// Handle Country dropdown
		WebElement country = driver.findElement(By.xpath("//mat-select[@formcontrolname='country']"));
		country.click();

		// Select the desired option
		WebElement countryOption = driver.findElement(By.xpath("//mat-option[@id='mat-option-117']"));
		countryOption.click();

		// Select checkbox
		WebElement checkbox = driver.findElement(By.id("signup_checkbox-input"));
		checkbox.click();

		// Click on submit button
		WebElement signupSubmit = driver.findElement(By.id("signup_submit"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", signupSubmit);
		signupSubmit.click();

		// Adding a wait to ensure the element is visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement alertBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alert-2-hdr")));

		// Verify the alert box message
		Assert.assertTrue(alertBox.isDisplayed(), "Alert box message is not displayed");
	}

	@Test(priority = 2)
	public void validateEmptyMandatoryFields() {

	    // Submit the registration form without filling any fields
	    WebElement signupSubmit = driver.findElement(By.id("signup_submit"));
	    JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("arguments[0].scrollIntoView(true);", signupSubmit);
	    signupSubmit.click();

	    // Verify error messages for all mandatory fields
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    // First Name error message verification
	    WebElement firstNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-0")));
	    String firstNameErrorMessage = firstNameError.getText();
	    String expectedFirstNameMessage = dataProp.getProperty("emptyFirstNameError");
	    Assert.assertTrue(firstNameErrorMessage.contains(expectedFirstNameMessage),
	            "Actual message: '" + firstNameErrorMessage + "' does not contain expected message: '" + expectedFirstNameMessage + "'");

	    // Last Name error message verification
	    WebElement lastNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-1")));
	    String lastNameErrorMessage = lastNameError.getText();
	    String expectedLastNameMessage = dataProp.getProperty("emptyLastNameError");
	    Assert.assertTrue(lastNameErrorMessage.contains(expectedLastNameMessage),
	            "Actual message: '" + lastNameErrorMessage + "' does not contain expected message: '" + expectedLastNameMessage + "'");

	    // Phone error message verification
	    WebElement phoneError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-2")));
	    String phoneErrorMessage = phoneError.getText();
	    String expectedPhoneMessage = dataProp.getProperty("emptyPhoneError");
	    Assert.assertTrue(phoneErrorMessage.contains(expectedPhoneMessage),
	            "Actual message: '" + phoneErrorMessage + "' does not contain expected message: '" + expectedPhoneMessage + "'");

	    // Company error message verification
	    WebElement companyError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-3")));
	    String companyErrorMessage = companyError.getText();
	    String expectedCompanyMessage = dataProp.getProperty("emptyCompanyError");
	    Assert.assertTrue(companyErrorMessage.contains(expectedCompanyMessage),
	            "Actual message: '" + companyErrorMessage + "' does not contain expected message: '" + expectedCompanyMessage + "'");

	    // Checkbox error message verification
	    WebElement checkboxError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-4")));
	    String checkboxErrorMessage = checkboxError.getText();
	    String expectedCheckboxMessage = dataProp.getProperty("emptyCheckboxError");
	    Assert.assertTrue(checkboxErrorMessage.contains(expectedCheckboxMessage),
	            "Actual message: '" + checkboxErrorMessage + "' does not contain expected message: '" + expectedCheckboxMessage + "'");
	}



	@Test(priority = 3)
	public void verifyInvalidFormatOfFields() {

	    // Providing invalid input for fields
	    WebElement signup_firstname = driver.findElement(By.id("signup_firstname"));
	    signup_firstname.sendKeys(dataProp.getProperty("invalidFirstName"));
	    
	    WebElement signup_lastname = driver.findElement(By.id("signup_lastname"));
	    signup_lastname.sendKeys(dataProp.getProperty("invalidLastName"));
	    
	    WebElement signup_phone = driver.findElement(By.id("signup_phone"));
	    signup_phone.sendKeys(dataProp.getProperty("invalidTelePhoneNumber"));
	    driver.findElement(By.id("signup_company")).click();

	    // Verify error messages for invalid data of fields
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // First Name error message verification
	    WebElement firstNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-4")));
	    String firstNameErrorMessage = firstNameError.getText();
	    String expectedFirstNameMessage = dataProp.getProperty("invalidFirstNameWarning");
	    Assert.assertTrue(firstNameErrorMessage.contains(expectedFirstNameMessage),
	            "Actual message: '" + firstNameErrorMessage + "' does not contain expected message: '" + expectedFirstNameMessage + "'");

	    // Last Name error message verification
	    WebElement lastNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-5")));
	    String lastNameErrorMessage = lastNameError.getText();
	    String expectedLastNameMessage = dataProp.getProperty("invalidLastNameWarning");
	    Assert.assertTrue(lastNameErrorMessage.contains(expectedLastNameMessage),
	            "Actual message: '" + lastNameErrorMessage + "' does not contain expected message: '" + expectedLastNameMessage + "'");

	    // Phone error message verification
	    WebElement phoneError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-mdc-error-6")));
	    String phoneErrorMessage = phoneError.getText();
	    String expectedPhoneErrorMessage = dataProp.getProperty("invalidPhoneWarning");
	    Assert.assertTrue(phoneErrorMessage.contains(expectedPhoneErrorMessage),
	            "Actual message: '" + phoneErrorMessage + "' does not contain expected message: '" + expectedPhoneErrorMessage + "'");
	}


	@Test(priority = 4)
	public void verifyAllHyperlinks() throws Exception {

		// Get the current window handle (original tab)
		String originalHandle = driver.getWindowHandle();

		// Click on a subscription link that opens in a new tab
		WebElement subscriptionLink = driver.findElement(By.xpath("//a[@class='signin-link'][@target='_blank']"));
		subscriptionLink.click();

		// Switch to the new tab
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		// Close the new tab and switch back to the original tab
		driver.close();
		driver.switchTo().window(originalHandle);

		// Click on Terms of use link
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement termsLink = driver.findElement(By.xpath("//a[normalize-space()='Terms of use']"));
		jse.executeScript("arguments[0].scrollIntoView(true);", termsLink);
		termsLink.click();

		// Switch to the new tab opened by Terms of use link
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		// Close the new tab and switch back to the original tab
		driver.close();
		driver.switchTo().window(originalHandle);

		// Click on Privacy policy link
		WebElement policyLink = driver.findElement(By.xpath("//a[normalize-space()='Privacy policy']"));
		policyLink.click();

		// Switch to the new tab opened by Privacy policy link
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		// Close the new tab and switch back to the original tab
		driver.close();
		driver.switchTo().window(originalHandle);

		// Click on sign in link
		WebElement signinLink = driver.findElement(By.id("signup_signin"));
		signinLink.click();

		// Verify sign in page text
		// Wait for the "Sign in page" element to be visible
		/*WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement signInPage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='REIMAGINE DATA']")));

		// Assert that the "Sign in page" element is displayed
		Assert.assertTrue(signInPage.isDisplayed(),
				"Reimagine data text is not displayed after clicking on sing in link");*/
		
		// Assertion to verify the URL after click on sign in
	    Assert.assertEquals(driver.getCurrentUrl(),(prop.getProperty("loginUrl")),
	            "The URL after click on sign in is not as expected");
	}

}