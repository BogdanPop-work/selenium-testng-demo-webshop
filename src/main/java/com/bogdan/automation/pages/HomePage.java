package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Demo Web Shop homepage.
 */
public class HomePage extends BasePage {

	private By registerLink = By.cssSelector("a.ico-register");
	private By loginLink = By.cssSelector("a.ico-login");
	private By logoutLink = By.cssSelector("a.ico-logout");
	private By accountLink = By.cssSelector("a.account");
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
		return isDisplayed(registerLink);
	}

	public boolean isLoginLinkDisplayed() {
		return isDisplayed(loginLink);
	}

	public boolean isLogoutLinkDisplayed() {
		return isDisplayed(logoutLink);
	}

	public boolean isAccountLinkDisplayed() {
		return isDisplayed(accountLink);
	}

	public boolean isShoppingCartLinkDisplayed() {
		return isDisplayed(shoppingCartLink);
	}

	public boolean isWishlistLinkDisplayed() {
		return isDisplayed(wishlistLink);
	}

	public boolean isSearchBoxDisplayed() {
		return isDisplayed(searchBox);
	}

	public boolean isBooksCategoryDisplayed() {
		return isDisplayed(booksCategory);
	}

	public boolean isComputersCategoryDisplayed() {
		return isDisplayed(computersCategory);
	}

	public boolean isElectronicsCategoryDisplayed() {
		return isDisplayed(electronicsCategory);
	}

	public boolean isApparelShoesCategoryDisplayed() {
		return isDisplayed(apparelShoesCategory);
	}

	public boolean isDigitalDownloadsCategoryDisplayed() {
		return isDisplayed(digitalDownloadsCategory);
	}

	public boolean isJewelryCategoryDisplayed() {
		return isDisplayed(jewelryCategory);
	}

	public boolean isGiftCardsCategoryDisplayed() {
		return isDisplayed(giftCardsCategory);
	}

	public String getLoggedInAccountEmail() {
		return getText(accountLink);
	}

	public void clickLoginLink() {
		click(loginLink);
	}

	public void clickLogout() {
		click(logoutLink);
	}

	
	public void openCategoryByUrl(String categoryUrl) {
	    click(By.cssSelector(".top-menu a[href='" + categoryUrl + "']"));
	}
}