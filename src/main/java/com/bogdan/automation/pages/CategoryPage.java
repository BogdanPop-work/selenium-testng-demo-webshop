package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryPage extends BasePage {

	private By categoryTitle = By.cssSelector(".page-title h1");
	private By productItems = By.cssSelector(".product-item");
	private By subcategoryItems = By.cssSelector(".sub-category-item");
	private By firstProductTitle = By.cssSelector(".product-title a");

	public CategoryPage(WebDriver driver) {
		super(driver);
	}

	public String getCategoryTitle() {
		return getText(categoryTitle);
	}

	public int getProductCount() {
		return driver.findElements(productItems).size();
	}

	public int getSubcategoryCount() {
		return driver.findElements(subcategoryItems).size();
	}

	public boolean areProductsDisplayed() {
		return getProductCount() > 0;
	}

	public boolean areSubcategoriesDisplayed() {
		return getSubcategoryCount() > 0;
	}

	public void openSubcategoryByUrl(String subcategoryUrl) {
		click(By.cssSelector(".sub-category-item a[href='" + subcategoryUrl + "']"));
	}

	public String getFirstProductName() {
		return getText(firstProductTitle);
	}

	public void openFirstProductDetails() {
		click(firstProductTitle);
	}
}