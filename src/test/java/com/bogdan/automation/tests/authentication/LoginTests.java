package com.bogdan.automation.tests.authentication;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.HomePage;
import com.bogdan.automation.pages.LoginPage;
import com.bogdan.automation.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

	private ApplicationPage applicationPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod(alwaysRun = true)
	public void initializePages() {

		applicationPage = new ApplicationPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@Test(groups = { "authentication", "regression" })
	public void verifyUserCanLoginSuccessfully() {

		applicationPage.openLoginPage();

		loginPage.login(ConfigReader.getProperty("validEmail"), ConfigReader.getProperty("validPassword"));

		Assert.assertTrue(homePage.isAccountLinkDisplayed(), "Account link is not displayed after login");

		Assert.assertEquals(homePage.getLoggedInAccountEmail(), ConfigReader.getProperty("validEmail"),
				"Logged in email does not match expected email");
	}

	@Test(groups = { "authentication", "regression" })
	public void verifyUserCannotLoginWithInvalidPassword() {

		applicationPage.openLoginPage();

		loginPage.login(ConfigReader.getProperty("validEmail"), ConfigReader.getProperty("invalidPassword"));

		Assert.assertTrue(loginPage.getLoginErrorMessage().contains("The credentials provided are incorrect"),
				"Login error message is not displayed for invalid password");
	}

	@Test(groups = { "authentication", "regression" })
	public void verifyUserCanLogoutSuccessfully() {

		applicationPage.openLoginPage();

		loginPage.login(ConfigReader.getProperty("validEmail"), ConfigReader.getProperty("validPassword"));

		Assert.assertTrue(homePage.isLogoutLinkDisplayed(), "Logout link is not displayed after login");

		homePage.clickLogout();

		Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link is not displayed after logout");
	}

	@Test(groups = { "authentication", "regression" })
	public void verifyUserCannotLoginWithInvalidEmail() {

		applicationPage.openLoginPage();

		loginPage.login(ConfigReader.getProperty("invalidEmail"), ConfigReader.getProperty("validPassword"));

		Assert.assertTrue(loginPage.getLoginErrorMessage().contains("No customer account found"),
				"Login error message is not displayed for invalid email");
	}

	@Test(groups = { "authentication", "regression" })
	public void verifyUserCannotLoginWithEmptyEmail() {

		applicationPage.openLoginPage();

		loginPage.enterPassword(ConfigReader.getProperty("validPassword"));
		loginPage.clickLoginButton();

		Assert.assertTrue(loginPage.getLoginErrorMessage().contains("No customer account found"),
				"Expected 'No customer account found' message was not displayed");
	}

	@Test(groups = { "authentication", "regression" })
	public void verifyUserCannotLoginWithEmptyPassword() {

		applicationPage.openLoginPage();

		loginPage.enterEmail(ConfigReader.getProperty("validEmail"));
		loginPage.clickLoginButton();

		Assert.assertTrue(loginPage.getLoginErrorMessage().contains("The credentials provided are incorrect"),
				"Expected 'The credentials provided are incorrect' message was not displayed");
	}
}