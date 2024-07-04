package com.carbon.bizdata360.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test(priority = 1)
    public void verifyCreateUserWithMandatoryField() throws Throwable {
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

        // Wait for the "Add" button to be visible and clickable
        WebElement addButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Add']")));

        // Retry mechanism for clicking the "Add" button
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                // Scroll to the "Add" button
                js.executeScript("arguments[0].scrollIntoView(true);", addButton);

                // Try to click the "Add" button
                addButton.click();
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element reference, retrying...");
                addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Add']")));
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                System.out.println("Click intercepted, retrying...");
                Thread.sleep(1000); // Wait before retrying
            }
        }

        // Fill up add user form
        // First name
        WebElement firstName = driver.findElement(By.xpath("//input[@placeholder='First name']"));
        firstName.sendKeys(dataProp.getProperty("createUserFirstName"));

        // Last name
        WebElement lastName = driver.findElement(By.xpath("//input[@placeholder='Last name']"));
        lastName.sendKeys(dataProp.getProperty("createUserLastName"));

        // Email address
        WebElement emailAddress = driver.findElement(By.xpath("//input[@placeholder='Email Address']"));
        emailAddress.sendKeys(Utilities.generateEmailWithTimeStamp());

        // User permission
        WebElement permission = driver.findElement(By.xpath("//mat-select[@formcontrolname='permission']"));
        permission.click();
        WebElement listUser = driver
                .findElement(By.xpath("//mat-option[@role='option']//span[contains(text(),'List (User)')]"));
        listUser.click();
        WebElement listIntegration = driver.findElement(
                By.xpath("//mat-option[@role='option']//span[contains(text(),'List (Integration Bridge)')]"));
        listIntegration.click();

        // Perform Tab key press
        actions.sendKeys(Keys.TAB).perform();

        // Make admin toggle button
        WebElement toggleButton = driver.findElement(By.xpath("//button[@role='switch']"));
        toggleButton.click();
        Thread.sleep(1000);
        toggleButton.click();

        // Click on add button to create or add user
        WebElement userAddButton = driver.findElement(By.xpath("//span[normalize-space()='Add']"));
        userAddButton.click();
    }

	   @Test(priority = 2)
	    public void verifyCreateUserWithEmptyField() throws Throwable {
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

	        // Wait for the "Add" button to be visible and clickable
	        WebElement addButton = wait
	                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Add']")));

	        // Retry mechanism for clicking the "Add" button
	        for (int attempt = 0; attempt < 3; attempt++) {
	            try {
	                // Scroll to the "Add" button
	                js.executeScript("arguments[0].scrollIntoView(true);", addButton);

	                // Try to click the "Add" button
	                addButton.click();
	                break;
	            } catch (StaleElementReferenceException e) {
	                System.out.println("Stale element reference, retrying...");
	                // Find the "Add" button again
	                addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Add']")));
	            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
	                System.out.println("Click intercepted, retrying...");
	                Thread.sleep(1000); // Wait before retrying
	            }
	        }

	        // Click on add button to create or add user
	        WebElement userAddButton = driver.findElement(By.xpath("//span[normalize-space()='Add']"));
	        userAddButton.click();

	        // Verify error messages for all mandatory fields
	        // First Name error message verification
	        WebElement firstNameError = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(By.xpath("//mat-error[normalize-space()='First Name Required']")));
	        String firstNameErrorMessage = firstNameError.getText();
	        String expectedFirstNameMessage = dataProp.getProperty("createUserFirstNameError");
	        Assert.assertTrue(firstNameErrorMessage.contains(expectedFirstNameMessage), "Actual message: '"
	                + firstNameErrorMessage + "' does not contain expected message: '" + expectedFirstNameMessage + "'");

	        // Last Name error message verification
	        WebElement lastNameError = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(By.xpath("//mat-error[normalize-space()='Last Name Required']")));
	        String lastNameErrorMessage = lastNameError.getText();
	        String expectedLastNameMessage = dataProp.getProperty("createUserLastNameError");
	        Assert.assertTrue(lastNameErrorMessage.contains(expectedLastNameMessage), "Actual message: '"
	                + lastNameErrorMessage + "' does not contain expected message: '" + expectedLastNameMessage + "'");

	        // Email error message verification
	        WebElement emailError = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(By.xpath("//mat-error[normalize-space()='Work email Required.']")));
	        String emailErrorMessage = emailError.getText();
	        String expectedErrorMessage = dataProp.getProperty("createUserEamilError");
	        Assert.assertTrue(emailErrorMessage.contains(expectedErrorMessage), "Actual message: '" + emailErrorMessage
	                + "' does not contain expected message: '" + expectedErrorMessage + "'");

	        // User permission error message verification
	        WebElement permissionError = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(By.xpath("//mat-error[normalize-space()='Need to select permission']")));
	        String permissionErrorMessage = permissionError.getText();
	        String expectedpermissionMessage = dataProp.getProperty("createUserPermissionError");
	        Assert.assertTrue(permissionErrorMessage.contains(expectedpermissionMessage), "Actual message: '"
	                + permissionErrorMessage + "' does not contain expected message: '" + expectedpermissionMessage + "'");
	    }
	
	 @Test(priority = 3)
	    public void verifyUserListInfoIcons() throws Throwable {
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

	        // Wait for the "Info" button to be visible and clickable
	        WebElement infoButton = wait
	                .until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-icon[normalize-space()='info']")));

	        // Retry mechanism for clicking the "Info" button
	        for (int attempt = 0; attempt < 3; attempt++) {
	            try {
	                // Scroll to the "Info" button
	                js.executeScript("arguments[0].scrollIntoView(true);", infoButton);

	                // Try to click the "Info" button
	                infoButton.click();
	                break;
	            } catch (StaleElementReferenceException e) {
	                System.out.println("Stale element reference, retrying...");
	                // Find the "Info" button again
	                infoButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-icon[normalize-space()='info']")));
	            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
	                System.out.println("Click intercepted, retrying...");
	                Thread.sleep(1000); // Wait before retrying
	            }
	        }

	        // Wait for the user details page to be visible
	        WebElement userDetailsPage = wait
	                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Details']")));

	        // Assert that the "User details page" element is displayed
	        Assert.assertTrue(userDetailsPage.isDisplayed(),
	                "Details text is not displayed after clicking on view details icon");
	    }
	  
	 @Test(priority = 4)
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
	        
	  @Test(priority = 5)
	    public void verifyUpdateUser() throws Throwable {
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

	        // Wait for the "Edit" button to be visible and clickable
	        WebElement editButton = wait
	                .until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-icon[normalize-space()='edit']")));

	        // Retry mechanism for clicking the "Edit" button
	        try {
	            // Scroll to the "Edit" button
	            js.executeScript("arguments[0].scrollIntoView(true);", editButton);
	            editButton.click();
	        } catch (StaleElementReferenceException e) {
	            // Find the "Edit" button again
	            editButton = wait
	                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-icon[normalize-space()='edit']")));
	            // Scroll to the "Edit" button again
	            js.executeScript("arguments[0].scrollIntoView(true);", editButton);
	            editButton.click();
	        }

	        // Scroll to the "Update" button
	        WebElement updateButton = driver.findElement(By.xpath("//span[normalize-space()='Update']"));
	        js.executeScript("arguments[0].scrollIntoView(true);", updateButton);

	        // Wait for the "Update" button to be clickable
	        updateButton = wait.until(ExpectedConditions.elementToBeClickable(updateButton));
	        
	        Thread.sleep(500);

	        // Click the "Update" button
	        updateButton.click();

	        // Wait for a while after clicking
	        Thread.sleep(1000);

	        // Assertion to verify the URL after click on update button
	        Assert.assertEquals(driver.getCurrentUrl(), dataProp.getProperty("userListUrl"),
	                "The URL after click on update button is not as expected");
	        System.out.println(driver.getCurrentUrl());
	    }

	}