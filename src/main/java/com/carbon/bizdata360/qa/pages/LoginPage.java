package com.carbon.bizdata360.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    // Objects
    @FindBy(id = "login_username")
    private WebElement loginUsername;

    @FindBy(id = "login_password")
    private WebElement loginPassword;

    @FindBy(id = "login_checkbox-input")
    private WebElement rememberMeCheckbox;

    @FindBy(id = "login_submit")
    private WebElement loginSubmitButton;

    @FindBy(xpath = "//span[@class='profile-name']")
    private WebElement verifyAccountEmailAlertBox;

    @FindBy(id = "login_forgot_password")
    private WebElement forgotPasswordPageLink;

    @FindBy(xpath = "//h2[normalize-space()='Reset Your Password']")
    private WebElement resetYourPasswordText;

    @FindBy(xpath = "//a[normalize-space()='Try it here']")
    private WebElement signInPageLink;

    @FindBy(xpath = "//span[normalize-space()='Real Time Cloud Based Integration Platform']")
    private WebElement signInPageText;
    
    @FindBy(xpath = "//span[@class='profile-name']")
    private WebElement profileNameText;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Actions
    public void enterUsername(String usernameText) {
        loginUsername.sendKeys(usernameText);
    }

    public void enterPassword(String passwordText) {
        loginPassword.sendKeys(passwordText);
    }

    public void clickRememberMeCheckbox() {
        rememberMeCheckbox.click();
    }

    public void clickSubmitButton() {
        loginSubmitButton.click();
    }

    public boolean isOrganizationAdminDisplayed() {
        return verifyAccountEmailAlertBox.isDisplayed();
    }

    public void clickForgotPasswordPageLink() {
        forgotPasswordPageLink.click();
    }

    public boolean isResetYourPasswordTextDisplayed() {
        return resetYourPasswordText.isDisplayed();
    }

    public void clickSignInPageLink() {
        signInPageLink.click();
    }

    public boolean isSignInPageTextDisplayed() {
        return signInPageText.isDisplayed();
    }
    
    public boolean isProfileNameDisplayed() {
        return profileNameText.isDisplayed();
    }
}
