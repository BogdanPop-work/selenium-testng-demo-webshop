package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WishlistPage extends BasePage {

	private By pageTitle = By.cssSelector(".page-title h1");

	private By productName = By.cssSelector(".cart-item-row .product a");

	private By removeProductCheckbox = By.name("removefromcart");

	private By updateWishlistButton = By.cssSelector(".update-wishlist-button");

	private By wishlistContent = By.cssSelector(".wishlist-content");
	private By wishlistItems = By.cssSelector(".cart-item-row");
	private By removeProductCheckboxes = By.name("removefromcart");

	public WishlistPage(WebDriver driver) {
		super(driver);
	}

	public String getPageTitleText() {
		return getText(pageTitle).trim();
	}

	public String getProductName() {
		return getText(productName).trim();
	}

	public void selectRemoveProductCheckbox() {
		click(removeProductCheckbox);
	}

	public void clickUpdateWishlistButton() {
		click(updateWishlistButton);
	}

	public String getWishlistContentText() {
		return getText(wishlistContent);
	}

	

	public void clearWishlist() {

	    if (isWishlistEmpty()) {
	        return;
	    }

	    driver.findElements(removeProductCheckboxes)
	            .forEach(WebElement::click);

	    clickUpdateWishlistButton();
	}
	
	public boolean isWishlistEmpty() {
	    return driver.findElements(removeProductCheckboxes).isEmpty();
	}
}