package com.bogdan.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShoppingCartPage extends BasePage {

	private By pageTitle = By.cssSelector(".page-title h1");
	private By productName = By.cssSelector(".cart-item-row .product-name");
	private By productUnitPrice = By.cssSelector(".cart-item-row .product-unit-price");
	private By productQuantity = By.cssSelector(".cart-item-row .qty-input");
	private By productSubtotal = By.cssSelector(".cart-item-row .product-subtotal");
	private By removeFromCartCheckbox = By.cssSelector(".cart-item-row .remove-from-cart input");
	private By updateCartButton = By.cssSelector("input.update-cart-button");
	private By emptyCartMessage = By.cssSelector(".order-summary-content");
	private By quantityField = By.cssSelector(".qty-input");
	private By cartTotal = By.cssSelector(".order-total strong");
	private By cartRows = By.cssSelector(".cart-item-row");
	private By shoppingCartQuantity = By.cssSelector("span.cart-qty");
	private By unitPrice = By.cssSelector(".product-unit-price");
	private By subtotal = By.cssSelector(".product-subtotal");
	private By orderTotal = By.cssSelector(".order-total");
	private By productAttributes = By.cssSelector(".cart-item-row .attributes");
	private By removeCheckboxes = By.cssSelector("input[name='removefromcart']");

	public ShoppingCartPage(WebDriver driver) {
		super(driver);
	}

	public String getProductAttributes() {
		return getText(productAttributes);
	}

	public String getPageTitleText() {
		return getText(pageTitle);
	}

	public String getProductName() {
		return getText(productName);
	}

	public String getProductUnitPrice() {
		return getText(productUnitPrice);
	}

	public String getProductQuantity() {
		return getAttribute(productQuantity, "value");
	}

	public String getProductSubtotal() {
		return getText(productSubtotal);
	}

	public void selectRemoveProductCheckbox() {
		click(removeFromCartCheckbox);
	}

	public void clickUpdateCartButton() {
		click(updateCartButton);
	}

	public String getEmptyCartMessage() {
		return getText(emptyCartMessage);
	}

	public void updateQuantity(String quantity) {
		type(quantityField, quantity);
	}

	public String getCartTotal() {
		return getText(cartTotal);
	}

	public boolean hasProducts() {
		return driver.findElements(cartRows).size() > 0;
	}

	public void clearCartIfNotEmpty() {

		if (hasProducts()) {

			List<WebElement> checkboxes = driver.findElements(removeCheckboxes);

			for (WebElement checkbox : checkboxes) {
				if (!checkbox.isSelected()) {
					checkbox.click();
				}
			}

			click(updateCartButton);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(cartRows));
		}
	}

	public String getHeaderCartQuantity() {
		return getText(shoppingCartQuantity);
	}

	public void waitUntilHeaderCartQuantityIs(String expectedQuantity) {
		wait.until(ExpectedConditions.textToBe(shoppingCartQuantity, expectedQuantity));
	}

	public double getUnitPrice() {
		return Double.parseDouble(getText(unitPrice));
	}

	public double getSubtotal() {
		return Double.parseDouble(getText(subtotal));
	}

	public double getOrderTotal() {
		return Double.parseDouble(getText(orderTotal));
	}

}