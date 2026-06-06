package com.bogdan.automation.tests.catalog;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.SearchResultsPage;

public class SearchTests extends BaseTest {

	private ApplicationPage applicationPage;
	private SearchResultsPage searchResultsPage;

	@BeforeMethod(alwaysRun = true)
	public void initializePages() {
		applicationPage = new ApplicationPage(driver);
		searchResultsPage = new SearchResultsPage(driver);
	}

	@Test(groups = { "catalog", "regression" })
	public void verifySearchReturnsRelevantProducts() {

		applicationPage.openSearchPage();

		searchResultsPage.searchForProduct("Com");

		Assert.assertEquals(searchResultsPage.getPageTitleText(), "Search", "Search page title is incorrect");

		Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(), "No search results are displayed");

		Assert.assertTrue(searchResultsPage.doAllResultTitlesContain("Com"),
				"Not all search results contain the search keyword");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifySearchWithInvalidKeywordShowsNoResults() {

		applicationPage.openSearchPage();

		searchResultsPage.searchForProduct("zzzzzzzzzzzzzzzzzz");

		Assert.assertEquals(searchResultsPage.getNoResultsMessage(),
				"No products were found that matched your criteria.", "No results message is incorrect");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyAdvancedSearchByCategoryReturnsProducts() {

		applicationPage.openSearchPage();

		searchResultsPage.advancedSearchByCategory("Computer", "Computers");

		Assert.assertEquals(searchResultsPage.getPageTitleText(), "Search", "Search page title is incorrect");

		Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
				"No search results are displayed for advanced search by category");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyAdvancedSearchInProductDescriptionsReturnsProducts() {

		applicationPage.openSearchPage();

		searchResultsPage.advancedSearchInDescriptions("more than 100 tips");

		Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
				"No search results are displayed when searching in product descriptions");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyAdvancedSearchByPriceRangeReturnsProductsWithinRange() {

		applicationPage.openSearchPage();

		searchResultsPage.advancedSearchByPriceRange("gift", "0", "20");

		Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
				"No products were returned for the selected price range");

		Assert.assertTrue(searchResultsPage.areAllPricesWithinRange(0, 20),
				"Products outside the requested price range were returned");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyProductsCanBeSortedByNameAscending() {

		applicationPage.openSearchPage();

		searchResultsPage.searchForProduct("gift");

		searchResultsPage.selectSortOption("Name: A to Z");

		Assert.assertTrue(searchResultsPage.areProductsSortedAscending(), "Products are not sorted by name ascending");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyProductsCanBeSortedByNameDescending() {

		applicationPage.openSearchPage();

		searchResultsPage.searchForProduct("gift");

		searchResultsPage.selectSortOption("Name: Z to A");

		Assert.assertTrue(searchResultsPage.areProductsSortedDescending(),
				"Products are not sorted by name descending");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyProductsCanBeSortedByPriceAscending() {

		applicationPage.openSearchPage();

		searchResultsPage.searchForProduct("gift");

		searchResultsPage.selectSortOption("Price: Low to High");

		Assert.assertTrue(searchResultsPage.arePricesSortedAscending(), "Products are not sorted by price ascending");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyProductsCanBeSortedByPriceDescending() {

		applicationPage.openSearchPage();

		searchResultsPage.searchForProduct("gift");

		searchResultsPage.selectSortOption("Price: High to Low");

		Assert.assertTrue(searchResultsPage.arePricesSortedDescending(), "Products are not sorted by price descending");
	}
}