package com.bogdan.automation.pages;

import com.bogdan.automation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;

/**
 * Represents application-level navigation actions used by smoke tests.
 */
public class ApplicationPage extends BasePage {

	public ApplicationPage(WebDriver driver) {
		super(driver);
	}

	public void openHomePage() {
		openUrl(ConfigReader.getProperty("baseUrl"));
	}

	public void openLoginPage() {
		openUrl(ConfigReader.getProperty("baseUrl") + "/login");
	}

	public void openRegisterPage() {
		openUrl(ConfigReader.getProperty("baseUrl") + "/register");
	}
}