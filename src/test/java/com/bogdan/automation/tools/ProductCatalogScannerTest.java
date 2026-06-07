package com.bogdan.automation.tools;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.models.ProductData;
import com.bogdan.automation.models.SupportedProductAction;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.ProductPage;
import com.bogdan.automation.pages.SearchResultsPage;
import com.bogdan.automation.utils.ProductDataReader;

public class ProductCatalogScannerTest extends BaseTest {

    private ApplicationPage applicationPage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;

    @BeforeMethod(alwaysRun = true)
    public void initializePages() {
        applicationPage = new ApplicationPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void scanSimpleProducts() {

        for (ProductData product : ProductDataReader.getProductsTestData().simpleProducts()) {

            applicationPage.searchFromHeader(product.name());
            searchResultsPage.openProductByName(product.name());

            String availability = productPage.getAvailabilityText();
            boolean hasAddToCart = productPage.isAddToCartButtonDisplayed();
            boolean hasWishlist = productPage.isAddToWishlistButtonDisplayed();

            String supportedAction = determineSupportedAction(hasAddToCart, hasWishlist);
            String note = buildNote(hasAddToCart, hasWishlist, availability);

            System.out.println("""
                    {
                      "name": "%s",
                      "category": "%s",
                      "subcategory": %s,
                      "price": %.2f,
                      "oldPrice": %s,
                      "description": %s,
                      "availability": %s,
                      "supportedAction": "%s",
                      "note": %s
                    },
                    """.formatted(
                    product.name(),
                    product.category(),
                    toJsonNullableString(product.subcategory()),
                    product.price(),
                    product.oldPrice(),
                    toJsonNullableString(product.description()),
                    toJsonNullableString(availability),
                    supportedAction,
                    toJsonNullableString(note)));
        }
    }

    private String determineSupportedAction(boolean hasAddToCart, boolean hasWishlist) {
        if (hasAddToCart) {
            return SupportedProductAction.ADD_TO_CART;
        }

        if (hasWishlist) {
            return SupportedProductAction.ADD_TO_WISHLIST;
        }

        return SupportedProductAction.VIEW_ONLY;
    }

    private String buildNote(boolean hasAddToCart, boolean hasWishlist, String availability) {
        if (!hasAddToCart && !hasWishlist) {
            return "Product is shown as " + availability + " but Add to cart and Wishlist actions are not available.";
        }

        if (!hasAddToCart && hasWishlist) {
            return "Product cannot be added to cart but can be added to wishlist.";
        }

        return null;
    }

    private String toJsonNullableString(String value) {
        if (value == null || value.isBlank()) {
            return "null";
        }

        return "\"" + value.replace("\"", "\\\"") + "\"";
    }
}