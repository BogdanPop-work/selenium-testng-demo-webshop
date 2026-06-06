package com.bogdan.automation.test.shoppingcart;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.models.ComputerConfiguration;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.BuildYourOwnComputerPage;
import com.bogdan.automation.pages.GiftCardPage;
import com.bogdan.automation.pages.LoginPage;
import com.bogdan.automation.pages.ProductPage;
import com.bogdan.automation.pages.SearchResultsPage;
import com.bogdan.automation.pages.ShoppingCartPage;
import com.bogdan.automation.utils.ConfigReader;

public class ShoppingCartTests extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartTests.class);

	private LoginPage loginPage;
	private ProductPage productPage;
	private ShoppingCartPage shoppingCartPage;
	private ApplicationPage applicationPage;
	private SearchResultsPage searchResultsPage;
	private BuildYourOwnComputerPage buildYourOwnComputerPage;
	private GiftCardPage giftCardPage;

	@BeforeMethod(alwaysRun = true)
	public void initializePages() {
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		shoppingCartPage = new ShoppingCartPage(driver);
		applicationPage = new ApplicationPage(driver);
		searchResultsPage = new SearchResultsPage(driver);
		buildYourOwnComputerPage = new BuildYourOwnComputerPage(driver);
		giftCardPage = new GiftCardPage(driver);

	}

	private void prepareCleanCart() {
		applicationPage.openShoppingCartFromHeader();
		shoppingCartPage.clearCartIfNotEmpty();
	}

	private void openGiftCard(String giftCardName) {
		applicationPage.searchFromHeader(giftCardName);
		searchResultsPage.openProductByName(giftCardName);
	}

	private void loginAsValidUser() {
		applicationPage.openLoginPage();
		loginPage.login(ConfigReader.getProperty("validEmail"), ConfigReader.getProperty("validPassword"));
	}

	private void openSimpleProduct() {
		applicationPage.searchFromHeader("Computing and Internet");
		searchResultsPage.openProductByName("Computing and Internet");
	}

	private void openBuildYourOwnExpensiveComputer() {
		applicationPage.searchFromHeader("Build your own expensive computer");
		searchResultsPage.openProductByName("Build your own expensive computer");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyUserCanAddSimpleProductToCart() {

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct();

		productPage.clickAddToCartButton();

		Assert.assertEquals(productPage.getAddToCartSuccessMessage(),
				"The product has been added to your shopping cart", "Add to cart success message is incorrect");

		productPage.openShoppingCartFromSuccessMessage();

		Assert.assertEquals(shoppingCartPage.getPageTitleText(), "Shopping cart",
				"Shopping cart page title is incorrect");

		Assert.assertEquals(shoppingCartPage.getProductName(), "Computing and Internet",
				"Product name in cart is incorrect");

		Assert.assertEquals(shoppingCartPage.getProductUnitPrice(), "10.00", "Product unit price is incorrect");

		Assert.assertEquals(shoppingCartPage.getProductQuantity(), "1", "Product quantity is incorrect");

		Assert.assertEquals(shoppingCartPage.getProductSubtotal(), "10.00", "Product subtotal is incorrect");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyUserCanRemoveProductFromCart() {

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct();

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		shoppingCartPage.selectRemoveProductCheckbox();
		shoppingCartPage.clickUpdateCartButton();

		Assert.assertTrue(shoppingCartPage.getEmptyCartMessage().contains("Your Shopping Cart is empty!"),
				"Shopping cart empty message is not displayed");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyUserCanUpdateProductQuantity() {

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct();

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		shoppingCartPage.updateQuantity("2");
		shoppingCartPage.clickUpdateCartButton();

		Assert.assertEquals(shoppingCartPage.getProductQuantity(), "2", "Product quantity was not updated");

		Assert.assertEquals(shoppingCartPage.getProductSubtotal(), "20.00", "Product subtotal is incorrect");

		Assert.assertEquals(shoppingCartPage.getCartTotal(), "20.00", "Cart total is incorrect");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyShoppingCartHeaderQuantityUpdatesAfterAddingProduct() {

		loginAsValidUser();

		prepareCleanCart();

		openSimpleProduct();

		productPage.clickAddToCartButton();

		shoppingCartPage.waitUntilHeaderCartQuantityIs("(1)");

		Assert.assertEquals(shoppingCartPage.getHeaderCartQuantity(), "(1)",
				"Shopping cart header quantity was not updated");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyConfiguredComputerCanBeAddedToCartWithCorrectPrice() {

		loginAsValidUser();

		prepareCleanCart();

		openBuildYourOwnExpensiveComputer();

		ComputerConfiguration configuration = buildYourOwnComputerPage.buildComputer("Fast", "8GB", "400 GB",
				List.of("Image Viewer", "Office Suite", "Other Office Suite"));

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		String attributes = shoppingCartPage.getProductAttributes();

		Assert.assertEquals(shoppingCartPage.getProductName(), "Build your own expensive computer",
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
	}

	@Test(groups = { "cart", "regression" })
	public void verifyRandomComputerConfigurationCanBeAddedToCartWithCorrectPrice() {

		loginAsValidUser();

		prepareCleanCart();

		openBuildYourOwnExpensiveComputer();

		ComputerConfiguration configuration = buildYourOwnComputerPage.buildRandomComputer();

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		String attributes = shoppingCartPage.getProductAttributes();

		logger.info("""

				===== CART DATA =====

				Attributes:
				{}

				Unit Price : {}
				Subtotal   : {}
				Order Total: {}

				=====================

				""", attributes, shoppingCartPage.getUnitPrice(), shoppingCartPage.getSubtotal(),
				shoppingCartPage.getOrderTotal());

		Assert.assertTrue(attributes.contains(configuration.processor()));
		Assert.assertTrue(attributes.contains(configuration.ram()));
		Assert.assertTrue(attributes.contains(configuration.hdd()));

		for (String software : configuration.software()) {
			Assert.assertTrue(attributes.contains(software));
		}

		Assert.assertEquals(shoppingCartPage.getUnitPrice(), configuration.expectedPrice());
		Assert.assertEquals(shoppingCartPage.getSubtotal(), configuration.expectedPrice());
		Assert.assertEquals(shoppingCartPage.getOrderTotal(), configuration.expectedPrice());

	}

	@Test(groups = { "cart", "regression" })
	public void verifyVirtualGiftCardCanBeAddedToCart() {

		String giftCardName = "$5 Virtual Gift Card";

		loginAsValidUser();

		prepareCleanCart();

		openGiftCard(giftCardName);

		giftCardPage.completeGiftCardDetails("John Recipient", "john.recipient@test.com", "Automation Tester",
				"automation_tester@test.com", "Happy birthday from automation!");

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		Assert.assertEquals(shoppingCartPage.getProductName(), giftCardName,
				"Gift card product name in cart is incorrect");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyVirtualGiftCardRequiredFieldsValidation() {

		String giftCardName = "$5 Virtual Gift Card";

		loginAsValidUser();

		prepareCleanCart();

		openGiftCard(giftCardName);

		productPage.clickAddToCartButton();

		Assert.assertTrue(giftCardPage.isRecipientNameValidationDisplayed(),
				"Recipient name validation message is not displayed");

		Assert.assertTrue(giftCardPage.isRecipientEmailValidationDisplayed(),
				"Recipient email validation message is not displayed");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyVirtualGiftCardRejectsInvalidRecipientEmail() {

		String giftCardName = "$5 Virtual Gift Card";

		loginAsValidUser();

		prepareCleanCart();

		openGiftCard(giftCardName);

		giftCardPage.enterRecipientName("John Recipient");
		giftCardPage.enterRecipientEmail("invalid-email");

		productPage.clickAddToCartButton();

		Assert.assertTrue(giftCardPage.isRecipientEmailValidationDisplayed(),
				"Recipient email validation message is not displayed");
	}

	@Test(groups = { "cart", "regression" })
	public void verifyVirtualGiftCardDetailsAreDisplayedInShoppingCart() {

		String giftCardName = "$5 Virtual Gift Card";
		String recipientName = "John Recipient";
		String recipientEmail = "john.recipient@test.com";
		String senderName = "Automation Tester";
		String senderEmail = "automation_tester@test.com";
		String message = "Happy birthday from automation!";

		loginAsValidUser();

		prepareCleanCart();

		openGiftCard(giftCardName);

		giftCardPage.completeGiftCardDetails(recipientName, recipientEmail, senderName, senderEmail, message);

		productPage.clickAddToCartButton();

		productPage.openShoppingCartFromSuccessMessage();

		String attributes = shoppingCartPage.getProductAttributes();
		Assert.assertTrue(attributes.contains("From: " + senderName + " <" + senderEmail + ">"),
				"Sender details are not displayed in cart");

		Assert.assertTrue(attributes.contains("For: " + recipientName + " <" + recipientEmail + ">"),
				"Recipient details are not displayed in cart");
	}

}