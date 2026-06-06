package com.bogdan.automation.tests.catalog;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.pages.CategoryPage;
import com.bogdan.automation.pages.HomePage;
import com.bogdan.automation.pages.ProductPage;

public class ProductCatalogTests extends BaseTest {

	private HomePage homePage;
	private CategoryPage categoryPage;
	private ProductPage productPage;

	@BeforeMethod(alwaysRun = true)
	public void initializePages() {
		homePage = new HomePage(driver);
		categoryPage = new CategoryPage(driver);
		productPage = new ProductPage(driver);
	}

	@DataProvider(name = "categories")
	public Object[][] categories() {
		return new Object[][] { { "Books", "/books", "products" }, { "Computers", "/computers", "subcategories" },
				{ "Electronics", "/electronics", "subcategories" }, { "Apparel & Shoes", "/apparel-shoes", "products" },
				{ "Digital downloads", "/digital-downloads", "products" }, { "Jewelry", "/jewelry", "products" },
				{ "Gift Cards", "/gift-cards", "products" } };
	}

	@Test(dataProvider = "categories", groups = { "catalog", "regression" })
	public void verifyUserCanNavigateToCategory(String categoryName, String categoryUrl, String expectedContentType) {

		homePage.openCategoryByUrl(categoryUrl);

		Assert.assertEquals(categoryPage.getCategoryTitle(), categoryName, "Category page title is incorrect");

		if (expectedContentType.equals("products")) {
			Assert.assertTrue(categoryPage.areProductsDisplayed(),
					"Products are not displayed in category: " + categoryName);
		}

		if (expectedContentType.equals("subcategories")) {
			Assert.assertTrue(categoryPage.areSubcategoriesDisplayed(),
					"Subcategories are not displayed in category: " + categoryName);
		}
	}

	@DataProvider(name = "subcategories")
	public Object[][] subcategories() {
		return new Object[][] { { "/computers", "Desktops", "/desktops" }, { "/computers", "Notebooks", "/notebooks" },
				{ "/computers", "Accessories", "/accessories" }, { "/electronics", "Camera, photo", "/camera-photo" },
				{ "/electronics", "Cell phones", "/cell-phones" } };
	}

	@Test(dataProvider = "subcategories", groups = { "catalog", "regression" })
	public void verifyUserCanNavigateToSubcategory(String parentCategoryUrl, String subcategoryName,
			String subcategoryUrl) {

		homePage.openCategoryByUrl(parentCategoryUrl);

		categoryPage.openSubcategoryByUrl(subcategoryUrl);

		Assert.assertEquals(categoryPage.getCategoryTitle(), subcategoryName, "Subcategory page title is incorrect");

		Assert.assertTrue(categoryPage.areProductsDisplayed(),
				"Products are not displayed in subcategory: " + subcategoryName);
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyUserCanOpenProductDetailsPage() {

		homePage.openCategoryByUrl("/books");

		String selectedProductName = categoryPage.getFirstProductName();

		categoryPage.openFirstProductDetails();

		Assert.assertEquals(productPage.getProductName(), selectedProductName,
				"Product details page name is incorrect");

		Assert.assertTrue(productPage.isProductPriceDisplayed(), "Product price is not displayed");

		Assert.assertTrue(productPage.isAddToCartButtonDisplayed(), "Add to cart button is not displayed");
	}

	@DataProvider(name = "pageSizes")
	public Object[][] pageSizes() {
		return new Object[][] { { "4", 4 }, { "8", 8 }, { "12", 12 } };
	}

	@Test(dataProvider = "pageSizes", groups = { "catalog", "regression" })
	public void verifyUserCanChangeProductDisplayCount(String pageSize, int expectedMaximumProducts) {
		homePage.openCategoryByUrl("/apparel-shoes");

		categoryPage.selectPageSize(pageSize);

		Assert.assertTrue(categoryPage.getProductCount() <= expectedMaximumProducts,
				"More products are displayed than expected for page size: " + pageSize);
	}

	@DataProvider(name = "viewModes")
	public Object[][] viewModes() {
		return new Object[][] { { "Grid" }, { "List" } };
	}

	@Test(dataProvider = "viewModes", groups = { "catalog", "regression" })
	public void verifyUserCanChangeProductViewMode(String viewMode) {

		homePage.openCategoryByUrl("/apparel-shoes");

		categoryPage.selectViewMode(viewMode);

		if (viewMode.equals("Grid")) {

			Assert.assertTrue(categoryPage.isGridViewDisplayed(), "Grid view is not displayed");
		} else {

			Assert.assertTrue(categoryPage.isListViewDisplayed(), "List view is not displayed");
		}
	}

}