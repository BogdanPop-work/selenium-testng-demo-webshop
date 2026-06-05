package com.bogdan.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the Search page and search results.
 */
public class SearchResultsPage extends BasePage {

	private By searchKeywordField = By.id("Q");
	private By advancedSearchCheckbox = By.id("As");
	private By categoryDropdown = By.id("Cid");
	private By searchDescriptionsCheckbox = By.id("Sid");
	private By searchSubcategoriesCheckbox = By.id("Isc");
	private By searchButton = By.cssSelector("input.search-button");
	private By pageTitle = By.cssSelector(".page-title h1");
	private By productItems = By.cssSelector(".search-results .product-item");
	private By productTitles = By.cssSelector(".search-results .product-title a");
	private By noResultsMessage = By.cssSelector(".search-results .result");
	private By priceFromField = By.id("Pf");
	private By priceToField = By.id("Pt");
	private By productPrices = By.cssSelector(".actual-price");

	public SearchResultsPage(WebDriver driver) {
		super(driver);
	}

	public void searchForProduct(String keyword) {
		type(searchKeywordField, keyword);
		click(searchButton);
	}

	public String getPageTitleText() {
		return getText(pageTitle);
	}

	public int getSearchResultCount() {
		return driver.findElements(productItems).size();
	}

	public boolean areSearchResultsDisplayed() {
		return getSearchResultCount() > 0;
	}

	public String getFirstSearchResultTitle() {
		return getText(productTitles);
	}

	public String getNoResultsMessage() {
		return getText(noResultsMessage);
	}

	public void enableAdvancedSearch() {
		click(advancedSearchCheckbox);
	}

	public void selectCategory(String categoryName) {
		selectByVisibleText(categoryDropdown, categoryName);
	}

	public boolean doAllResultTitlesContain(String keyword) {
		List<WebElement> titles = driver.findElements(productTitles);

		for (WebElement title : titles) {
			if (!title.getText().toLowerCase().contains(keyword.toLowerCase())) {
				return false;
			}
		}

		return true;
	}

	public void advancedSearchByCategory(String keyword, String categoryName) {
		type(searchKeywordField, keyword);
		enableAdvancedSearch();
		selectCategory(categoryName);
		enableSearchSubcategories();
		click(searchButton);
	}

	public void enableSearchInDescriptions() {
		click(searchDescriptionsCheckbox);
	}

	public void advancedSearchInDescriptions(String keyword) {
		type(searchKeywordField, keyword);
		enableAdvancedSearch();
		selectCategory("All");
		enableSearchInDescriptions();
		click(searchButton);
	}

	public void enableSearchSubcategories() {
		click(searchSubcategoriesCheckbox);
	}

	public void advancedSearchByPriceRange(String keyword, String minPrice, String maxPrice) {

		type(searchKeywordField, keyword);

		enableAdvancedSearch();

		type(priceFromField, minPrice);
		type(priceToField, maxPrice);

		click(searchButton);
	}

	public boolean areAllPricesWithinRange(double minPrice, double maxPrice) {

		List<WebElement> prices = driver.findElements(productPrices);

		for (WebElement price : prices) {

			double value = Double.parseDouble(price.getText().trim());

			if (value < minPrice || value > maxPrice) {
				return false;
			}
		}

		return true;
	}
}