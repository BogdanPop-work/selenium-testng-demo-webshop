package com.bogdan.automation.pages;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Represents a product details page.
 */
public class ProductPage extends BasePage {

	private By productName = By.cssSelector(".product-name h1");
	private By productPrice = By.cssSelector(".product-price span[itemprop='price']");

	private By addToCartButton = By.cssSelector("input.add-to-cart-button");
	private By addToWishlistButton = By.cssSelector("input.add-to-wishlist-button");

	private By addToCartSuccessMessage = By.cssSelector("#bar-notification .content");
	private By addToWishlistSuccessMessage = By.cssSelector("#bar-notification .content");

	private By shoppingCartLinkInSuccessMessage = By.cssSelector("#bar-notification .content a");
	private By wishlistLinkInSuccessMessage = By.cssSelector("#bar-notification .content a");

	private By availabilityValue = By.cssSelector(".stock .value");
	private By closeNotificationButton = By.cssSelector("#bar-notification .close");

	public ProductPage(WebDriver driver) {
		super(driver);
	}

	public String getProductName() {
		return getText(productName).trim();
	}

	public double getProductPrice() {
		return Double.parseDouble(getText(productPrice).trim());
	}

	public boolean isProductPriceDisplayed() {
		return isDisplayed(productPrice);
	}

	public boolean isAddToCartButtonDisplayed() {
		return !driver.findElements(addToCartButton).isEmpty();
	}

	public boolean isAddToWishlistButtonDisplayed() {
		return !driver.findElements(addToWishlistButton).isEmpty();
	}

	public void clickAddToCartButton() {
		click(addToCartButton);
	}

	public void clickAddToWishlistButton() {
		click(addToWishlistButton);
	}

	public String getAddToCartSuccessMessage() {
		return getText(addToCartSuccessMessage);
	}

	public String getAddToWishlistSuccessMessage() {
		return getText(addToWishlistSuccessMessage);
	}

	public void openShoppingCartFromSuccessMessage() {
	    wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartLinkInSuccessMessage));
	    click(shoppingCartLinkInSuccessMessage);
	}

	public void openWishlistFromSuccessMessage() {
		click(wishlistLinkInSuccessMessage);
	}

	public String getAvailabilityText() {

		if (driver.findElements(availabilityValue).isEmpty()) {
			return null;
		}

		return getText(availabilityValue).trim();
	}

	public void waitForAddToCartSuccessMessage() {
		findVisible(addToCartSuccessMessage);
	}

	public void closeSuccessNotification() {
		click(closeNotificationButton);
	}
}