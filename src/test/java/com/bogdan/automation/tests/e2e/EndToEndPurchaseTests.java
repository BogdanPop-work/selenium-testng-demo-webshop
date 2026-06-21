package com.bogdan.automation.tests.e2e;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.clients.WarehouseApiResponse;
import com.bogdan.automation.models.ProductData;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.CheckoutPage;
import com.bogdan.automation.pages.LoginPage;
import com.bogdan.automation.pages.OrderDetailsPage;
import com.bogdan.automation.pages.ProductPage;
import com.bogdan.automation.pages.RegisterPage;
import com.bogdan.automation.pages.SearchResultsPage;
import com.bogdan.automation.pages.ShoppingCartPage;
import com.bogdan.automation.services.OrderLifecycleService;
import com.bogdan.automation.utils.ConfigReader;
import com.bogdan.automation.utils.FakeCreditCardGenerator;
import com.bogdan.automation.utils.ProductDataReader;
import com.bogdan.automation.utils.Randomizer;
import com.bogdan.automation.utils.TestDataGenerator;

public class EndToEndPurchaseTests extends BaseTest {

	private RegisterPage registerPage;
	private LoginPage loginPage;
	private ProductPage productPage;
	private ShoppingCartPage shoppingCartPage;
	private ApplicationPage applicationPage;
	private SearchResultsPage searchResultsPage;
	private CheckoutPage checkoutPage;
	private OrderDetailsPage orderDetailsPage;

	@BeforeMethod(alwaysRun = true)
	public void initializePages() {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		shoppingCartPage = new ShoppingCartPage(driver);
		applicationPage = new ApplicationPage(driver);
		searchResultsPage = new SearchResultsPage(driver);
		checkoutPage = new CheckoutPage(driver);
		orderDetailsPage = new OrderDetailsPage(driver);
	}

	@Test(groups = { "e2e", "regression" })
	public void verifyNewlyRegisteredUserCanPurchaseMultipleProductsEndToEnd() {

		String firstName = Randomizer.getRandomFirstName();
		String lastName = Randomizer.getRandomLastName();
		String email = TestDataGenerator.generateUniqueEmail();
		String password = ConfigReader.getProperty("validPassword");

		applicationPage.openRegisterPage();

		registerPage.registerUser(firstName, lastName, email, password, password);

		Assert.assertEquals(registerPage.getRegistrationSuccessMessage(), "Your registration completed",
				"Registration should be completed successfully");

		applicationPage.openLoginPage();
		loginPage.login(email, password);

		ProductData firstProduct = ProductDataReader.getRandomCartReadySimpleProduct();
		ProductData secondProduct = ProductDataReader.getRandomCartReadySimpleProduct();

		applicationPage.searchFromHeader(firstProduct.name());
		searchResultsPage.openProductByNameAndPrice(firstProduct.name(), firstProduct.price());
		productPage.clickAddToCartButton();
		productPage.openShoppingCartFromSuccessMessage();

		applicationPage.searchFromHeader(secondProduct.name());
		searchResultsPage.openProductByNameAndPrice(secondProduct.name(), secondProduct.price());
		productPage.clickAddToCartButton();
		productPage.openShoppingCartFromSuccessMessage();

		Assert.assertTrue(shoppingCartPage.hasProducts(), "Shopping cart should contain products before checkout");

		shoppingCartPage.acceptTermsOfService();
		shoppingCartPage.clickCheckout();

		String billingEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@test.com";

		checkoutPage.fillBillingAddress(firstName, lastName, billingEmail, "United States", "New York",
				"Automation Street 10", "10001", "1234567890");

		checkoutPage.clickBillingContinueButton();

		checkoutPage.clickShippingContinueButton();

		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		checkoutPage.selectCreditCardPaymentMethod();
		checkoutPage.clickPaymentMethodContinueButton();

		checkoutPage.fillCreditCardInformation("Visa", firstName + " " + lastName,
				FakeCreditCardGenerator.generateVisa(), FakeCreditCardGenerator.generateExpirationMonth(),
				FakeCreditCardGenerator.generateExpirationYear(), FakeCreditCardGenerator.generateCardCode("Visa"));

		checkoutPage.clickPaymentInfoContinueButton();

		Assert.assertTrue(checkoutPage.isConfirmOrderStepDisplayed(), "Confirm Order step should be displayed");

		checkoutPage.clickConfirmOrderButton();

		Assert.assertEquals(checkoutPage.getOrderCompletedMessage(), "Your order has been successfully processed!",
				"Order completed message is incorrect");

		checkoutPage.openOrderDetailsPage();

		Assert.assertEquals(orderDetailsPage.getPageTitleText(), "Order information",
				"Order details page title is incorrect");

		String orderNumber = orderDetailsPage.getOrderNumberText().replace("Order #", "").trim();

		OrderLifecycleService orderService = new OrderLifecycleService();

		orderService.saveCompletedOrder(orderNumber, firstName, lastName, email, "Credit Card", "Ground");

		Assert.assertTrue(orderService.orderExists(orderNumber), "Completed order was not saved in the database");
		WarehouseApiResponse warehouseResponse = orderService.sendOrderToWarehouse(orderNumber);

		Assert.assertEquals(warehouseResponse.statusCode(), 202, "Warehouse API should accept the order");

		Assert.assertEquals(warehouseResponse.status(), "ACCEPTED", "Warehouse status is incorrect");

		Assert.assertTrue(orderService.orderWasSentToWarehouse(orderNumber),
				"Order was not marked as sent to warehouse");

		System.out.println("Warehouse Reference: " + warehouseResponse.warehouseReference());
		System.out.println("Order Number: " + orderNumber);

		Assert.assertFalse(orderNumber.isBlank(), "Order number should not be empty");

		Assert.assertTrue(orderDetailsPage.getOrderStatusText().contains("Order Status:"),
				"Order status should be displayed");

		Assert.assertTrue(orderDetailsPage.isProductsSectionDisplayed(),
				"Products section should be displayed on order details page");

		Assert.assertTrue(orderDetailsPage.isBillingAddressDisplayed(),
				"Billing address should be displayed on order details page");

		Assert.assertTrue(orderDetailsPage.isShippingAddressDisplayed(),
				"Shipping address should be displayed on order details page");
		//new OrderRepository().printOrders();
	}
}