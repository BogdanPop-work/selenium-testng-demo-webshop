package com.bogdan.automation.tests.registration;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.RegisterPage;
import com.bogdan.automation.utils.TestDataGenerator;

public class RegistrationTests extends BaseTest {

	private ApplicationPage applicationPage;
	private RegisterPage registerPage;

	@BeforeMethod
	public void initializePages() {
		applicationPage = new ApplicationPage(driver);
		registerPage = new RegisterPage(driver);
	}

	@Test(groups = { "registration", "regression" })
	public void verifyUserCanRegisterSuccessfully() {

		String email = TestDataGenerator.generateUniqueEmail();

		applicationPage.openRegisterPage();

		registerPage.registerUser("Bogdan", "Automation", email, "Password123", "Password123");

		Assert.assertEquals(registerPage.getRegistrationSuccessMessage(), "Your registration completed",
				"Registration success message is incorrect");
	}

	@Test(groups = { "registration", "regression" })
	public void verifyUserCannotRegisterWithExistingEmail() {

		String email = TestDataGenerator.generateUniqueEmail();

		applicationPage.openRegisterPage();

		registerPage.registerUser("Bogdan", "Automation", email, "Password123", "Password123");

		driver.manage().deleteAllCookies();
		driver.navigate().refresh();

		applicationPage.openRegisterPage();

		registerPage.registerUser("Bogdan", "Automation", email, "Password123", "Password123");

		Assert.assertTrue(registerPage.getRegistrationErrorMessage().contains("The specified email already exists"),
				"Duplicate email error message is not displayed");
	}

	@Test(groups = { "registration", "regression" })
	public void verifyUserCannotRegisterWithEmptyRequiredFields() {

		applicationPage.openRegisterPage();

		registerPage.submitRegistration();

		Assert.assertEquals(registerPage.getFirstNameValidationMessage(), "First name is required.");

		Assert.assertEquals(registerPage.getLastNameValidationMessage(), "Last name is required.");

		Assert.assertEquals(registerPage.getEmailValidationMessage(), "Email is required.");

		Assert.assertEquals(registerPage.getPasswordValidationMessage(), "Password is required.");

		Assert.assertEquals(registerPage.getConfirmPasswordValidationMessage(), "Password is required.");
	}

	@Test(groups = { "registration", "regression" })
	public void verifyUserCannotRegisterWithPasswordMismatch() {

		String email = TestDataGenerator.generateUniqueEmail();

		applicationPage.openRegisterPage();

		registerPage.registerUser("Bogdan", "Automation", email, "Password123", "Password456");

		Assert.assertEquals(registerPage.getConfirmPasswordValidationMessage(),
				"The password and confirmation password do not match.",
				"Password mismatch validation message is incorrect");
	}

	@Test(groups = { "registration", "regression" })
	public void verifyUserCannotRegisterWithShortPassword() {

		String email = TestDataGenerator.generateUniqueEmail();

		applicationPage.openRegisterPage();

		registerPage.registerUser("Bogdan", "Automation", email, "12345", "12345");

		Assert.assertEquals(registerPage.getPasswordValidationMessage(),
				"The password should have at least 6 characters.", "Password length validation message is incorrect");
	}
}