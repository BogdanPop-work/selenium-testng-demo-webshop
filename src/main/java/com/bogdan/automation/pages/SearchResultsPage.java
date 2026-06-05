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
    private By searchButton = By.cssSelector("input.search-button");
    private By pageTitle = By.cssSelector(".page-title h1");
    private By productItems = By.cssSelector(".search-results .product-item");
    private By productTitles = By.cssSelector(".search-results .product-title a");
    private By noResultsMessage = By.cssSelector(".search-results .result");

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
}