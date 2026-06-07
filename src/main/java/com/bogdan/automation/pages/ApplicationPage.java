package com.bogdan.automation.pages;

import com.bogdan.automation.utils.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents application-level navigation actions used by smoke tests.
 */
public class ApplicationPage extends BasePage {

	private By shoppingCartLink = By.cssSelector("a.ico-cart");
	private By searchBox = By.id("small-searchterms");
	private By searchButton = By.cssSelector("input.search-box-button");
	private By wishlistLink = By.cssSelector(".ico-wishlist");

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

	public void openSearchPage() {
		driver.get(ConfigReader.getProperty("baseUrl") + "/search");
	}

	public void openShoppingCartFromHeader() {
		click(shoppingCartLink);
	}

	public void searchFromHeader(String keyword) {
		type(searchBox, keyword);
		click(searchButton);
	}

	public void openWishlistFromHeader() {
		click(wishlistLink);
	}
}