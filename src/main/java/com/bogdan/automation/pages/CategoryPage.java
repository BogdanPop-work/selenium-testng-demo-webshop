package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bogdan.automation.utils.ConfigReader;

public class CategoryPage extends BasePage {

	private By categoryTitle = By.cssSelector(".page-title h1");
	private By productItems = By.cssSelector(".product-item");
	private By subcategoryItems = By.cssSelector(".sub-category-item");
	private By firstProductTitle = By.cssSelector(".product-title a");
	private By pageSizeDropdown = By.id("products-pagesize");
	private By viewModeDropdown = By.id("products-viewmode");
	private By productGrid = By.cssSelector(".product-grid");
	private By productList = By.cssSelector(".product-list");

	public CategoryPage(WebDriver driver) {
		super(driver);
	}

	public void selectPageSize(String pageSize) {
		selectByVisibleText(pageSizeDropdown, pageSize);
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
	public void selectViewMode(String viewMode) {
	    selectByVisibleText(viewModeDropdown, viewMode);
	}
	public boolean isGridViewDisplayed() {
	    return driver.findElements(productGrid).size() > 0;
	}
	public boolean isListViewDisplayed() {
	    return driver.findElements(productList).size() > 0;
	}
	public void openCategory(String categoryUrl) {
	    driver.get(ConfigReader.getProperty("baseUrl") + categoryUrl);
	}

	public void openProduct(String productName) {
	    click(By.linkText(productName));
	}
}