package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents a product details page.
 */
public class ProductPage extends BasePage {

    private By productName = By.cssSelector(".product-name h1");
    private By productPrice = By.cssSelector(".product-price span[itemprop='price']");
    private By addToCartButton = By.cssSelector("input.add-to-cart-button");
    private By addToCartSuccessMessage = By.cssSelector("#bar-notification .content");
    private By shoppingCartLinkInSuccessMessage = By.cssSelector("#bar-notification .content a");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName).trim();
    }

    public boolean isProductPriceDisplayed() {
        return isDisplayed(productPrice);
    }

    public boolean isAddToCartButtonDisplayed() {
        return isDisplayed(addToCartButton);
    }

    public void clickAddToCartButton() {
        click(addToCartButton);
    }

    public String getAddToCartSuccessMessage() {
        return getText(addToCartSuccessMessage);
    }

    public void openShoppingCartFromSuccessMessage() {
        click(shoppingCartLinkInSuccessMessage);
    }
}