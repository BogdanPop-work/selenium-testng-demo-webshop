package com.bogdan.automation.utils;

import java.io.InputStream;

import com.bogdan.automation.models.ConfigurableProductData;
import com.bogdan.automation.models.GiftCardData;
import com.bogdan.automation.models.ProductData;
import com.bogdan.automation.models.ProductsTestData;
import com.bogdan.automation.models.SupportedProductAction;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductDataReader {

	private static final String PRODUCTS_FILE = "testdata/products.json";

	private ProductDataReader() {
	}

	public static ProductsTestData getProductsTestData() {

		try (InputStream inputStream = ProductDataReader.class.getClassLoader().getResourceAsStream(PRODUCTS_FILE)) {

			if (inputStream == null) {
				throw new RuntimeException("Products test data file was not found: " + PRODUCTS_FILE);
			}

			return new ObjectMapper().readValue(inputStream, ProductsTestData.class);

		} catch (Exception exception) {
			throw new RuntimeException("Failed to read products test data file", exception);
		}

	}

	public static ProductData getRandomDownloadableProduct() {
		return Randomizer.getRandomItem(getProductsTestData().downloadableProducts());
	}

	public static ProductData getRandomSimpleProduct() {
		return Randomizer.getRandomItem(getProductsTestData().simpleProducts());
	}

	public static ProductData getRandomCartReadySimpleProduct() {
		return Randomizer.getRandomItem(getProductsTestData().simpleProducts().stream()
				.filter(product -> SupportedProductAction.ADD_TO_CART.equals(product.supportedAction())).toList());
	}

	public static ProductData getRandomWishlistReadySimpleProduct() {
		return Randomizer.getRandomItem(getProductsTestData().simpleProducts().stream()
				.filter(product -> SupportedProductAction.ADD_TO_WISHLIST.equals(product.supportedAction())).toList());
	}

	public static ProductData getRandomViewOnlySimpleProduct() {
		return Randomizer.getRandomItem(getProductsTestData().simpleProducts().stream()
				.filter(product -> SupportedProductAction.VIEW_ONLY.equals(product.supportedAction())).toList());
	}

	public static GiftCardData getRandomGiftCard() {
		return Randomizer.getRandomItem(getProductsTestData().giftCards());
	}

	public static GiftCardData getRandomVirtualGiftCard() {
		return Randomizer.getRandomItem(getProductsTestData().giftCards().stream()
				.filter(giftCard -> "virtual".equalsIgnoreCase(giftCard.type())).toList());
	}

	public static ConfigurableProductData getRandomConfigurableProduct() {
		return Randomizer.getRandomItem(getProductsTestData().configurableProducts());
	}

}