package com.bogdan.automation.test.shoppingcart;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.models.ComputerConfiguration;
import com.bogdan.automation.models.ConfigurableProductData;
import com.bogdan.automation.models.GiftCardData;
import com.bogdan.automation.models.ProductData;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.BuildYourOwnComputerPage;
import com.bogdan.automation.pages.GiftCardPage;
import com.bogdan.automation.pages.LoginPage;
import com.bogdan.automation.pages.ProductPage;
import com.bogdan.automation.pages.SearchResultsPage;
import com.bogdan.automation.pages.ShoppingCartPage;
import com.bogdan.automation.pages.WishlistPage;
import com.bogdan.automation.utils.ConfigReader;
import com.bogdan.automation.utils.ProductDataReader;
import com.bogdan.automation.utils.Randomizer;

public class ShoppingCartTests extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartTests.class);

	private LoginPage loginPage;
	private ProductPage productPage;
	private ShoppingCartPage shoppingCartPage;
	private ApplicationPage applicationPage;
	private SearchResultsPage searchResultsPage;
	private BuildYourOwnComputerPage buildYourOwnComputerPage;
	private GiftCardPage giftCardPage;
	private ProductData simpleProduct;
	private WishlistPage wishlistPage;

	@BeforeMethod(alwaysRun = true)
	public void initializePages() {
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		shoppingCartPage = new ShoppingCartPage(driver);
		applicationPage = new ApplicationPage(driver);
		searchResultsPage = new SearchResultsPage(driver);
		buildYourOwnComputerPage = new BuildYourOwnComputerPage(driver);
		giftCardPage = new GiftCardPage(driver);
		wishlistPage = new WishlistPage(driver);

	}

	private void openConfigurableProduct(ConfigurableProductData product) {
		applicationPage.searchFromHeader(product.name());
		searchResultsPage.openProductByName(product.name());
	}

	private void openDownloadableProduct(ProductData product) {
		applicationPage.searchFromHeader(product.name());
		searchResultsPage.openProductByNameAndPrice(product.name(), product.price());
	}

	private void prepareCleanCart() {
		applicationPage.openShoppingCartFromHeader();
		shoppingCartPage.clearCartIfNotEmpty();
	}

	private void prepareCleanWishlist() {

		applicationPage.openWishlistFromHeader();

		wishlistPage.clearWishlist();
	}

	private void openGiftCard(String giftCardName) {
		applicationPage.searchFromHeader(giftCardName);
		searchResultsPage.openProductByName(giftCardName);
	}

	private void loginAsValidUser() {
		applicationPage.openLoginPage();
		loginPage.login(ConfigReader.getProperty("validEmail"), ConfigReader.getProperty("validPassword"));
	}

	private void openSimpleProduct(ProductData product) {
		applicationPage.searchFromHeader(product.name());

		searchResultsPage.openProductByNameAndPrice(product.name(), product.price());
	}

	private void completeGiftCardDetails(GiftCardData giftCard) {
		String recipientFirstName = Randomizer.getRandomFirstName();
		String recipientLastName = Randomizer.getRandomLastName();
		String recipientName = recipientFirstName + " " + recipientLastName;

		String recipientEmail = recipientFirstName.toLowerCase() + "." + recipientLastName.toLowerCase() + "@test.com";

		String senderName = ConfigReader.getProperty("validUserName");
		String senderEmail = ConfigReader.getProperty("validEmail");
		String message = "Happy birthday from automation!";

		if ("virtual".equalsIgnoreCase(giftCard.type())) {
			giftCardPage.completeGiftCardDetails(recipientName, recipientEmail, senderName, senderEmail, message);
		} else if ("physical".equalsIgnoreCase(giftCard.type())) {
			giftCardPage.completePhysicalGiftCardDetails(recipientName, senderName, message);
		} else {
			throw new IllegalArgumentException("Unsupported gift card type: " + giftCard.type());
		}
	}

	@Test(groups = { "cart", "regression" })
	public void verifyUserCanAddSimpleProductToCart() {

		simpleProduct = ProductDataReader.getRandomCartReadySimpleProduct();

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct(simpleProduct);

		productPage.clickAddToCartButton();

		Assert.assertEquals(productPage.getAddToCartSuccessMessage(),
				"The product has been added to your shopping cart", "Add to cart success message is incorrect");

		productPage.openShoppingCartFromSuccessMessage();

		Assert.assertEquals(shoppingCartPage.getPageTitleText(), "Shopping cart",
				"Shopping cart page title is incorrect");

		Assert.assertEquals(shoppingCartPage.getProductName(), simpleProduct.name(),
				"Product name in cart is incorrect");
		Assert.assertEquals(shoppingCartPage.getProductQuantity(), "1", "Product quantity is incorrect");

		Assert.assertEquals(shoppingCartPage.getUnitPrice(), simpleProduct.price(), "Product unit price is incorrect");

		Assert.assertEquals(shoppingCartPage.getSubtotal(), simpleProduct.price(), "Product subtotal is incorrect");

	}

	@Test(groups = { "cart", "regression" })
	public void verifyUserCanRemoveProductFromCart() {

		simpleProduct = ProductDataReader.getRandomCartReadySimpleProduct();

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct(simpleProduct);

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		shoppingCartPage.selectRemoveProductCheckbox();
		shoppingCartPage.clickUpdateCartButton();

		Assert.assertTrue(shoppingCartPage.getEmptyCartMessage().contains("Your Shopping Cart is empty!"),
				"Shopping cart empty message is not displayed");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyUserCanUpdateProductQuantity() {

		simpleProduct = ProductDataReader.getRandomCartReadySimpleProduct();

		int quantity = Randomizer.getRandomInt(2, 10);
		String expectedTotal = String.format(Locale.US, "%.2f", simpleProduct.price() * quantity);

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct(simpleProduct);

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		shoppingCartPage.updateQuantity(String.valueOf(quantity));
		shoppingCartPage.clickUpdateCartButton();

		Assert.assertEquals(shoppingCartPage.getProductQuantity(), String.valueOf(quantity),
				"Product quantity was not updated");

		Assert.assertEquals(shoppingCartPage.getProductSubtotal(), expectedTotal, "Product subtotal is incorrect");

		Assert.assertEquals(shoppingCartPage.getCartTotal(), expectedTotal, "Cart total is incorrect");

	}

	@Test(groups = { "cart", "regression" })
	public void verifyShoppingCartHeaderQuantityUpdatesAfterAddingProduct() {

		simpleProduct = ProductDataReader.getRandomCartReadySimpleProduct();

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct(simpleProduct);

		productPage.clickAddToCartButton();

		shoppingCartPage.waitUntilHeaderCartQuantityIs("(1)");

		Assert.assertEquals(shoppingCartPage.getHeaderCartQuantity(), "(1)",
				"Shopping cart header quantity was not updated");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyRandomConfigurableComputerCanBeAddedToCartWithCorrectPrice() {

		ConfigurableProductData configurableProduct = ProductDataReader.getRandomConfigurableProduct();

		loginAsValidUser();
		prepareCleanCart();
		openConfigurableProduct(configurableProduct);

		ComputerConfiguration configuration = buildYourOwnComputerPage.buildRandomComputer(configurableProduct);

		productPage.clickAddToCartButton();
		productPage.openShoppingCartFromSuccessMessage();

		String attributes = shoppingCartPage.getProductAttributes();

		Assert.assertEquals(shoppingCartPage.getProductName(), configurableProduct.name(),
				"Product name in cart is incorrect");

		Assert.assertTrue(attributes.contains(configuration.processor()),
				"Selected processor is not displayed in cart");

		Assert.assertTrue(attributes.contains(configuration.ram()), "Selected RAM is not displayed in cart");

		Assert.assertTrue(attributes.contains(configuration.hdd()), "Selected HDD is not displayed in cart");

		for (String software : configuration.software()) {
			Assert.assertTrue(attributes.contains(software), "Selected software is not displayed in cart: " + software);
		}

		Assert.assertEquals(shoppingCartPage.getUnitPrice(), configuration.expectedPrice(),
				"Computer unit price is incorrect");

		Assert.assertEquals(shoppingCartPage.getSubtotal(), configuration.expectedPrice(),
				"Computer subtotal is incorrect");

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), configuration.expectedPrice(), "Cart total is incorrect");

		logger.info("""

				===== CONFIGURABLE PRODUCT TEST =====

				Product     : {}
				Processor   : {}
				RAM         : {}
				HDD         : {}
				Software    : {}

				Attributes:
				{}

				Unit Price  : {}
				Subtotal    : {}
				Order Total : {}

				Expected Price : {}

				=====================================

				""", configurableProduct.name(), configuration.processor(), configuration.ram(), configuration.hdd(),
				configuration.software(), attributes, shoppingCartPage.getUnitPrice(), shoppingCartPage.getSubtotal(),
				shoppingCartPage.getOrderTotal(), configuration.expectedPrice());
	}

	@Test(groups = { "cart", "regression" })
	public void verifyGiftCardCanBeAddedToCart() {

		GiftCardData giftCard = ProductDataReader.getRandomGiftCard();

		loginAsValidUser();
		prepareCleanCart();
		openGiftCard(giftCard.name());

		completeGiftCardDetails(giftCard);

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		Assert.assertEquals(shoppingCartPage.getProductName(), giftCard.name(),
				"Gift card product name in cart is incorrect");

		Assert.assertEquals(shoppingCartPage.getUnitPrice(), giftCard.price(), "Gift card unit price is incorrect");

		Assert.assertEquals(shoppingCartPage.getSubtotal(), giftCard.price(), "Gift card subtotal is incorrect");

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), giftCard.price(), "Gift card cart total is incorrect");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyGiftCardRequiredFieldsValidation() {

		GiftCardData giftCard = ProductDataReader.getRandomGiftCard();

		loginAsValidUser();
		prepareCleanCart();
		openGiftCard(giftCard.name());

		productPage.clickAddToCartButton();

		if (Boolean.TRUE.equals(giftCard.requiresRecipientName())) {
			Assert.assertTrue(giftCardPage.isRecipientNameValidationDisplayed(),
					"Recipient name validation message is not displayed");
		}

		if (Boolean.TRUE.equals(giftCard.requiresRecipientEmail())) {
			Assert.assertTrue(giftCardPage.isRecipientEmailValidationDisplayed(),
					"Recipient email validation message is not displayed");
		}
	}

	@Test(groups = { "cart", "regression" })
	public void verifyVirtualGiftCardRejectsInvalidRecipientEmail() {

		GiftCardData giftCard = ProductDataReader.getRandomVirtualGiftCard();

		String recipientFirstName = Randomizer.getRandomFirstName();
		String recipientLastName = Randomizer.getRandomLastName();
		String recipientName = recipientFirstName + " " + recipientLastName;

		loginAsValidUser();
		prepareCleanCart();
		openGiftCard(giftCard.name());

		giftCardPage.enterRecipientName(recipientName);
		giftCardPage.enterRecipientEmail("invalid-email");

		productPage.clickAddToCartButton();

		Assert.assertTrue(giftCardPage.isRecipientEmailValidationDisplayed(),
				"Recipient email validation message is not displayed");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyVirtualGiftCardDetailsAreDisplayedInShoppingCart() {

		GiftCardData giftCard = ProductDataReader.getRandomVirtualGiftCard();

		String recipientFirstName = Randomizer.getRandomFirstName();
		String recipientLastName = Randomizer.getRandomLastName();
		String recipientName = recipientFirstName + " " + recipientLastName;

		String recipientEmail = recipientFirstName.toLowerCase() + "." + recipientLastName.toLowerCase() + "@test.com";

		String senderName = ConfigReader.getProperty("validUserName");
		String senderEmail = ConfigReader.getProperty("validEmail");
		String message = "Happy birthday from automation!";

		loginAsValidUser();

		prepareCleanCart();

		openGiftCard(giftCard.name());

		giftCardPage.completeGiftCardDetails(recipientName, recipientEmail, senderName, senderEmail, message);

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		String attributes = shoppingCartPage.getProductAttributes();

		Assert.assertTrue(attributes.contains("From: " + senderName + " <" + senderEmail + ">"),
				"Sender details are not displayed in cart");

		Assert.assertTrue(attributes.contains("For: " + recipientName + " <" + recipientEmail + ">"),
				"Recipient details are not displayed in cart");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyDownloadableProductCanBeAddedToCart() {

		ProductData downloadableProduct = ProductDataReader.getRandomDownloadableProduct();

		loginAsValidUser();

		prepareCleanCart();

		openDownloadableProduct(downloadableProduct);

		Assert.assertEquals(productPage.getProductPrice(), downloadableProduct.price(),
				"Incorrect downloadable product was opened");

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		Assert.assertEquals(shoppingCartPage.getProductName(), downloadableProduct.name(),
				"Downloadable product name in cart is incorrect");

		Assert.assertEquals(shoppingCartPage.getUnitPrice(), downloadableProduct.price(),
				"Downloadable product unit price is incorrect");

		Assert.assertEquals(shoppingCartPage.getSubtotal(), downloadableProduct.price(),
				"Downloadable product subtotal is incorrect");

		Assert.assertEquals(shoppingCartPage.getOrderTotal(), downloadableProduct.price(),
				"Downloadable product cart total is incorrect");
	}

	@Test(groups = { "wishlist", "regression" })
	public void verifyWishlistReadyProductCanBeAddedToWishlist() {

		ProductData product = ProductDataReader.getRandomWishlistReadySimpleProduct();

		loginAsValidUser();
		prepareCleanWishlist();

		openSimpleProduct(product);

		productPage.clickAddToWishlistButton();

		Assert.assertEquals(productPage.getAddToWishlistSuccessMessage(), "The product has been added to your wishlist",
				"Add to wishlist success message is incorrect");

		productPage.openWishlistFromSuccessMessage();

		Assert.assertEquals(wishlistPage.getPageTitleText(), "Wishlist", "Wishlist page title is incorrect");

		Assert.assertEquals(wishlistPage.getProductName(), product.name(), "Wishlist product name is incorrect");
	}

	@Test(groups = { "wishlist", "regression" })
	public void verifyWishlistReadyProductCanBeRemovedFromWishlist() {

		ProductData product = ProductDataReader.getRandomWishlistReadySimpleProduct();

		loginAsValidUser();
		prepareCleanWishlist();

		openSimpleProduct(product);

		productPage.clickAddToWishlistButton();
		productPage.openWishlistFromSuccessMessage();

		Assert.assertEquals(wishlistPage.getProductName(), product.name(), "Wishlist product name is incorrect");

		wishlistPage.selectRemoveProductCheckbox();
		wishlistPage.clickUpdateWishlistButton();

		Assert.assertTrue(wishlistPage.getWishlistContentText().contains("The wishlist is empty"),
				"Wishlist empty message is not displayed");
	}

	@Test(groups = { "catalog", "regression" })
	public void verifyViewOnlyProductDoesNotSupportShoppingActions() {

		ProductData product = ProductDataReader.getRandomViewOnlySimpleProduct();

		openSimpleProduct(product);

		logger.warn("""

				===== VIEW ONLY PRODUCT =====

				Product : {}
				Availability : {}
				Note    : {}

				=============================

				""", product.name(), product.availability(), product.note());

		Assert.assertFalse(productPage.isAddToCartButtonDisplayed(),
				"View-only product should not have Add to cart button");

		Assert.assertFalse(productPage.isAddToWishlistButtonDisplayed(),
				"View-only product should not have Add to wishlist button");
	}
}