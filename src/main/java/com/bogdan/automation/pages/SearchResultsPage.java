package com.bogdan.automation.pages;

import java.util.ArrayList;
import java.util.Collections;
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
	private By productTitles = By.cssSelector(".search-results .product-title a");
	private By noResultsMessage = By.cssSelector(".search-results .result");
	private By priceFromField = By.id("Pf");
	private By priceToField = By.id("Pt");
	private By productPrices = By.cssSelector(".search-results .actual-price");
	private By sortDropdown = By.id("products-orderby");
	private By productItems = By.cssSelector(".product-item");

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

	public boolean doAllResultTitlesContain(String keyword) {
		List<WebElement> titles = driver.findElements(productTitles);

		for (WebElement title : titles) {
			if (!title.getText().toLowerCase().contains(keyword.toLowerCase())) {
				return false;
			}
		}

		return true;
	}

	public void enableAdvancedSearch() {
		click(advancedSearchCheckbox);
	}

	public void selectCategory(String categoryName) {
		selectByVisibleText(categoryDropdown, categoryName);
	}

	public void enableSearchSubcategories() {
		click(searchSubcategoriesCheckbox);
	}

	public void enableSearchInDescriptions() {
		click(searchDescriptionsCheckbox);
	}

	public void advancedSearchByCategory(String keyword, String categoryName) {
		type(searchKeywordField, keyword);
		enableAdvancedSearch();
		selectCategory(categoryName);
		enableSearchSubcategories();
		click(searchButton);
	}

	public void advancedSearchInDescriptions(String keyword) {
		type(searchKeywordField, keyword);
		enableAdvancedSearch();
		selectCategory("All");
		enableSearchInDescriptions();
		click(searchButton);
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

	public void selectSortOption(String option) {
		selectByVisibleText(sortDropdown, option);
	}

	public List<String> getProductTitles() {
		List<WebElement> titleElements = driver.findElements(productTitles);
		List<String> titles = new ArrayList<>();

		for (WebElement titleElement : titleElements) {
			titles.add(titleElement.getText().trim());
		}

		return titles;
	}

	public boolean areProductsSortedAscending() {
		List<String> actualTitles = getProductTitles();
		List<String> expectedTitles = new ArrayList<>(actualTitles);

		Collections.sort(expectedTitles);

		return actualTitles.equals(expectedTitles);
	}

	public boolean areProductsSortedDescending() {
		List<String> actualTitles = getProductTitles();
		List<String> expectedTitles = new ArrayList<>(actualTitles);

		expectedTitles.sort(Collections.reverseOrder());

		return actualTitles.equals(expectedTitles);
	}

	public List<Double> getProductPrices() {

		List<WebElement> priceElements = driver.findElements(productPrices);

		List<Double> prices = new ArrayList<>();

		for (WebElement priceElement : priceElements) {
			prices.add(Double.parseDouble(priceElement.getText().trim()));
		}

		return prices;
	}

	public boolean arePricesSortedAscending() {

		List<Double> actualPrices = getProductPrices();

		List<Double> expectedPrices = new ArrayList<>(actualPrices);

		Collections.sort(expectedPrices);

		return actualPrices.equals(expectedPrices);
	}

	public boolean arePricesSortedDescending() {

		List<Double> actualPrices = getProductPrices();

		List<Double> expectedPrices = new ArrayList<>(actualPrices);

		expectedPrices.sort(Collections.reverseOrder());

		return actualPrices.equals(expectedPrices);
	}

	public void openProductByName(String productName) {
		click(By.linkText(productName));
	}

	public void openProductByNameAndPrice(String productName, double expectedPrice) {

		List<WebElement> products = driver.findElements(productItems);

		for (WebElement product : products) {

			String name = product.findElement(By.cssSelector(".product-title a")).getText().trim();

			double price = Double.parseDouble(product.findElement(By.cssSelector(".actual-price")).getText().trim());

			if (name.equals(productName) && price == expectedPrice) {
				product.findElement(By.cssSelector(".product-title a")).click();
				return;
			}
		}

		throw new RuntimeException(
				"Product was not found in search results: " + productName + " with price " + expectedPrice);
	}
	


}