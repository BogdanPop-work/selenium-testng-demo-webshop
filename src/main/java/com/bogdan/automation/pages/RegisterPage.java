package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Registration page of Demo Web Shop.
 */
public class RegisterPage extends BasePage {

	private By maleGenderRadioButton = By.id("gender-male");
	private By firstNameField = By.id("FirstName");
	private By lastNameField = By.id("LastName");
	private By emailField = By.id("Email");
	private By passwordField = By.id("Password");
	private By confirmPasswordField = By.id("ConfirmPassword");
	private By registerButton = By.id("register-button");
	private By firstNameValidationMessage = By.cssSelector("[data-valmsg-for='FirstName']");

	private By lastNameValidationMessage = By.cssSelector("[data-valmsg-for='LastName']");

	private By emailValidationMessage = By.cssSelector("[data-valmsg-for='Email']");

	private By passwordValidationMessage = By.cssSelector("[data-valmsg-for='Password']");

	private By confirmPasswordValidationMessage = By.cssSelector("[data-valmsg-for='ConfirmPassword']");

	private By registrationSuccessMessage = By.cssSelector(".result");
	private By registrationErrorMessage = By.cssSelector(".message-error");

	public RegisterPage(WebDriver driver) {
		super(driver);
	}

	public void selectMaleGender() {
		click(maleGenderRadioButton);
	}

	public void enterFirstName(String firstName) {
		type(firstNameField, firstName);
	}

	public void enterLastName(String lastName) {
		type(lastNameField, lastName);
	}

	public void enterEmail(String email) {
		type(emailField, email);
	}

	public void enterPassword(String password) {
		type(passwordField, password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		type(confirmPasswordField, confirmPassword);
	}

	public void clickRegisterButton() {
		click(registerButton);
	}

	public void registerUser(String firstName, String lastName, String email, String password, String confirmPassword) {

		selectMaleGender();
		enterFirstName(firstName);
		enterLastName(lastName);
		enterEmail(email);
		enterPassword(password);
		enterConfirmPassword(confirmPassword);
		clickRegisterButton();
	}

	public String getRegistrationSuccessMessage() {
		return getText(registrationSuccessMessage);
	}

	public String getRegistrationErrorMessage() {
		return getText(registrationErrorMessage);
	}

	public String getFirstNameValidationMessage() {
		return getText(firstNameValidationMessage);
	}

	public String getLastNameValidationMessage() {
		return getText(lastNameValidationMessage);
	}

	public String getEmailValidationMessage() {
		return getText(emailValidationMessage);
	}

	public String getPasswordValidationMessage() {
		return getText(passwordValidationMessage);
	}

	public String getConfirmPasswordValidationMessage() {
		return getText(confirmPasswordValidationMessage);
	}

	public void submitRegistration() {
		click(registerButton);
	}
}