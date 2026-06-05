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

    @BeforeMethod
    public void initializePages() {
        applicationPage = new ApplicationPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
    }

    @Test(groups = { "catalog", "regression" })
    public void verifySearchReturnsRelevantProducts() {

    	applicationPage.openSearchPage();

        searchResultsPage.searchForProduct("Com");

        Assert.assertEquals(
                searchResultsPage.getPageTitleText(),
                "Search",
                "Search page title is incorrect");

        Assert.assertTrue(
                searchResultsPage.areSearchResultsDisplayed(),
                "No search results are displayed");

        Assert.assertTrue(
                searchResultsPage.doAllResultTitlesContain("Com"),
                "Not all search results contain the search keyword");
    }

    @Test(groups = { "catalog", "regression" })
    public void verifySearchWithInvalidKeywordShowsNoResults() {

        applicationPage.openUrl("https://demowebshop.tricentis.com/search");

        searchResultsPage.searchForProduct("zzzzzzzzzzzzzzzzzz");

        Assert.assertEquals(
                searchResultsPage.getNoResultsMessage(),
                "No products were found that matched your criteria.",
                "No results message is incorrect");
    }
}