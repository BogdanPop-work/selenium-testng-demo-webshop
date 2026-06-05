package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Demo Web Shop home page.
 */
public class HomePage extends BasePage {

	private By registerLink = By.cssSelector("a.ico-register");
	private By loginLink = By.cssSelector("a.ico-login");
	private By shoppingCartLink = By.cssSelector("a.ico-cart");
	private By wishlistLink = By.cssSelector("a.ico-wishlist");
	private By searchBox = By.id("small-searchterms");

	private By booksCategory = By.cssSelector(".top-menu a[href='/books']");
	private By computersCategory = By.cssSelector(".top-menu a[href='/computers']");
	private By electronicsCategory = By.cssSelector(".top-menu a[href='/electronics']");
	private By apparelShoesCategory = By.cssSelector(".top-menu a[href='/apparel-shoes']");
	private By digitalDownloadsCategory = By.cssSelector(".top-menu a[href='/digital-downloads']");
	private By jewelryCategory = By.cssSelector(".top-menu a[href='/jewelry']");
	private By giftCardsCategory = By.cssSelector(".top-menu a[href='/gift-cards']");

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public boolean isRegisterLinkDisplayed() {
		return driver.findElement(registerLink).isDisplayed();
	}

	public boolean isLoginLinkDisplayed() {
		return driver.findElement(loginLink).isDisplayed();
	}

	public boolean isShoppingCartLinkDisplayed() {
		return driver.findElement(shoppingCartLink).isDisplayed();
	}

	public boolean isWishlistLinkDisplayed() {
		return driver.findElement(wishlistLink).isDisplayed();
	}

	public boolean isSearchBoxDisplayed() {
		return driver.findElement(searchBox).isDisplayed();
	}

	public boolean isBooksCategoryDisplayed() {
		return driver.findElement(booksCategory).isDisplayed();
	}

	public boolean isComputersCategoryDisplayed() {
		return driver.findElement(computersCategory).isDisplayed();
	}

	public boolean isElectronicsCategoryDisplayed() {
		return driver.findElement(electronicsCategory).isDisplayed();
	}

	public boolean isApparelShoesCategoryDisplayed() {
		return driver.findElement(apparelShoesCategory).isDisplayed();
	}

	public boolean isDigitalDownloadsCategoryDisplayed() {
		return driver.findElement(digitalDownloadsCategory).isDisplayed();
	}

	public boolean isJewelryCategoryDisplayed() {
		return driver.findElement(jewelryCategory).isDisplayed();
	}

	public boolean isGiftCardsCategoryDisplayed() {
		return driver.findElement(giftCardsCategory).isDisplayed();
	}

	public void clickLoginLink() {
		driver.findElement(loginLink).click();
	}
}