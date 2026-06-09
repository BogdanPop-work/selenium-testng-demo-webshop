package com.bogdan.automation.tests.checkout;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.models.ProductData;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.CheckoutPage;
import com.bogdan.automation.pages.LoginPage;
import com.bogdan.automation.pages.ProductPage;
import com.bogdan.automation.pages.SearchResultsPage;
import com.bogdan.automation.pages.ShoppingCartPage;
import com.bogdan.automation.utils.ConfigReader;
import com.bogdan.automation.utils.FakeCreditCardGenerator;
import com.bogdan.automation.utils.ProductDataReader;
import com.bogdan.automation.utils.PurchaseOrderGenerator;
import com.bogdan.automation.utils.Randomizer;

public class CheckoutTests extends BaseTest {

	private LoginPage loginPage;
	private ProductPage productPage;
	private ShoppingCartPage shoppingCartPage;
	private ApplicationPage applicationPage;
	private SearchResultsPage searchResultsPage;
	private CheckoutPage checkoutPage;

	private ProductData simpleProduct;

	@BeforeMethod(alwaysRun = true)
	public void initializePages() {
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		shoppingCartPage = new ShoppingCartPage(driver);
		applicationPage = new ApplicationPage(driver);
		searchResultsPage = new SearchResultsPage(driver);
		checkoutPage = new CheckoutPage(driver);
	}

	private void loginAsValidUser() {
		applicationPage.openLoginPage();
		loginPage.login(ConfigReader.getProperty("validEmail"), ConfigReader.getProperty("validPassword"));
	}

	private void prepareCleanCart() {
		applicationPage.openShoppingCartFromHeader();
		shoppingCartPage.clearCartIfNotEmpty();
	}

	private void openSimpleProduct(ProductData product) {
		applicationPage.searchFromHeader(product.name());
		searchResultsPage.openProductByNameAndPrice(product.name(), product.price());
	}

	private void addSimpleProductToCart() {
		simpleProduct = ProductDataReader.getRandomCartReadySimpleProduct();

		loginAsValidUser();
		prepareCleanCart();
		openSimpleProduct(simpleProduct);

		productPage.clickAddToCartButton();
		productPage.openShoppingCartFromSuccessMessage();

		Assert.assertTrue(shoppingCartPage.hasProducts(), "Cart should contain at least one product before checkout");
	}

	private void openCheckoutWithProductInCart() {

		addSimpleProductToCart();

		shoppingCartPage.acceptTermsOfService();
		shoppingCartPage.clickCheckout();
	}

	private void submitValidBillingAddress(String firstName, String lastName) {

		Assert.assertTrue(checkoutPage.isBillingStepContentDisplayed(), "Billing step content should be displayed");

		if (checkoutPage.isBillingAddressDropdownDisplayed()) {
			checkoutPage.clickBillingContinueButton();
			return;
		}

		String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@test.com";

		checkoutPage.fillBillingAddress(firstName, lastName, email, "United States", "New York", "Automation Street 10",
				"10001", "1234567890");

		checkoutPage.clickBillingContinueButton();
	}

	private String[] generateCustomerData() {

		String firstName = Randomizer.getRandomFirstName();
		String lastName = Randomizer.getRandomLastName();

		return new String[] { firstName, lastName };
	}

	private void openCheckoutAsGuestWithProductInCart() {

		applicationPage.openHomePage();

		if (applicationPage.isLogoutLinkDisplayed()) {
			applicationPage.clickLogout();
		}

		simpleProduct = ProductDataReader.getRandomCartReadySimpleProduct();

		openSimpleProduct(simpleProduct);

		productPage.clickAddToCartButton();
		productPage.openShoppingCartFromSuccessMessage();

		shoppingCartPage.acceptTermsOfService();
		shoppingCartPage.clickCheckout();

		loginPage.clickCheckoutAsGuestButton();
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyCheckoutBlockedWithoutTermsAcceptance() {

		addSimpleProductToCart();

		shoppingCartPage.clickCheckout();

		Assert.assertTrue(shoppingCartPage.isTermsWarningDisplayed(), "Terms warning popup should be displayed");

		Assert.assertEquals(shoppingCartPage.getTermsWarningText(),
				"Please accept the terms of service before the next step.", "Terms warning message is incorrect");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyCheckoutPageStructureIsDisplayed() {

		addSimpleProductToCart();

		shoppingCartPage.acceptTermsOfService();
		shoppingCartPage.clickCheckout();

		Assert.assertTrue(checkoutPage.isBillingStepDisplayed(), "Billing step should be displayed");
		Assert.assertTrue(checkoutPage.isShippingStepDisplayed(), "Shipping step should be displayed");
		Assert.assertTrue(checkoutPage.isShippingMethodStepDisplayed(), "Shipping method step should be displayed");
		Assert.assertTrue(checkoutPage.isPaymentMethodStepDisplayed(), "Payment method step should be displayed");
		Assert.assertTrue(checkoutPage.isPaymentInfoStepDisplayed(), "Payment information step should be displayed");
		Assert.assertTrue(checkoutPage.isConfirmOrderStepDisplayed(), "Confirm order step should be displayed");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyRegisteredUserCanOpenCheckoutAfterAcceptingTerms() {

		addSimpleProductToCart();

		shoppingCartPage.acceptTermsOfService();
		shoppingCartPage.clickCheckout();

		Assert.assertTrue(checkoutPage.isCheckoutStepsDisplayed(), "Checkout steps container should be displayed");

		Assert.assertTrue(checkoutPage.isBillingStepDisplayed(), "Billing address step should be displayed");

		Assert.assertTrue(checkoutPage.isBillingStepContentDisplayed(),
				"Billing address content should be active after opening checkout");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyRegisteredUserCanCompleteAddressAndShippingMethodSteps() {

		openCheckoutWithProductInCart();

		String[] customer = generateCustomerData();

		String firstName = customer[0];
		String lastName = customer[1];

		submitValidBillingAddress(firstName, lastName);

		Assert.assertTrue(checkoutPage.isShippingStepContentDisplayed(),
				"Shipping address step should be displayed after billing address is accepted");

		checkoutPage.clickShippingContinueButton();

		Assert.assertTrue(checkoutPage.isShippingMethodContentDisplayed(),
				"Shipping method step should be displayed after shipping address is accepted");

		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		Assert.assertTrue(checkoutPage.isPaymentMethodStepDisplayed(),
				"Payment method step should be displayed after shipping method is selected");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyRegisteredUserCanCompleteCashOnDeliveryPayment() {

		openCheckoutWithProductInCart();

		String[] customer = generateCustomerData();
		submitValidBillingAddress(customer[0], customer[1]);

		checkoutPage.clickShippingContinueButton();
		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		checkoutPage.selectCashOnDeliveryPaymentMethod();
		checkoutPage.clickPaymentMethodContinueButton();

		Assert.assertTrue(checkoutPage.getPaymentInfoText().contains("You will pay by COD"),
				"COD payment message should be displayed");

		checkoutPage.clickPaymentInfoContinueButton();

		Assert.assertTrue(checkoutPage.isConfirmOrderStepDisplayed(), "Confirm Order step should be displayed");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyRegisteredUserCanCompleteCheckMoneyOrderPayment() {

		openCheckoutWithProductInCart();

		String[] customer = generateCustomerData();
		submitValidBillingAddress(customer[0], customer[1]);

		checkoutPage.clickShippingContinueButton();
		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		checkoutPage.selectCheckMoneyOrderPaymentMethod();
		checkoutPage.clickPaymentMethodContinueButton();

		String paymentInfoText = checkoutPage.getPaymentInfoText();

		Assert.assertTrue(paymentInfoText.contains("Tricentis GmbH"), "Company name should be displayed");
		Assert.assertTrue(paymentInfoText.contains("Leonard-Bernstein-Straße 10"),
				"Street address should be displayed");
		Assert.assertTrue(paymentInfoText.contains("1220 Vienna"), "City and postal code should be displayed");
		Assert.assertTrue(paymentInfoText.contains("Austria"), "Country should be displayed");

		checkoutPage.clickPaymentInfoContinueButton();

		Assert.assertTrue(checkoutPage.isConfirmOrderStepDisplayed(), "Confirm Order step should be displayed");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyRegisteredUserCanCompleteCreditCardPayment() {

		openCheckoutWithProductInCart();

		String[] customer = generateCustomerData();
		String firstName = customer[0];
		String lastName = customer[1];

		submitValidBillingAddress(firstName, lastName);

		checkoutPage.clickShippingContinueButton();
		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		checkoutPage.selectCreditCardPaymentMethod();
		checkoutPage.clickPaymentMethodContinueButton();

		String cardType = "Visa";

		checkoutPage.fillCreditCardInformation(cardType, firstName + " " + lastName,
				FakeCreditCardGenerator.generateVisa(), FakeCreditCardGenerator.generateExpirationMonth(),
				FakeCreditCardGenerator.generateExpirationYear(), FakeCreditCardGenerator.generateCardCode(cardType));

		checkoutPage.clickPaymentInfoContinueButton();

		Assert.assertTrue(checkoutPage.isConfirmOrderStepDisplayed(), "Confirm Order step should be displayed");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyRegisteredUserCanCompletePurchaseOrderPayment() {

		openCheckoutWithProductInCart();

		String[] customer = generateCustomerData();
		submitValidBillingAddress(customer[0], customer[1]);

		checkoutPage.clickShippingContinueButton();
		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		checkoutPage.selectPurchaseOrderPaymentMethod();
		checkoutPage.clickPaymentMethodContinueButton();

		checkoutPage.fillPurchaseOrderNumber(PurchaseOrderGenerator.generatePurchaseOrderNumber());

		checkoutPage.clickPaymentInfoContinueButton();

		Assert.assertTrue(checkoutPage.isConfirmOrderStepDisplayed(), "Confirm Order step should be displayed");
	}

	@Test(groups = { "checkout", "regression" })
	public void verifyRegisteredUserCanConfirmOrder() {

		openCheckoutWithProductInCart();

		String[] customer = generateCustomerData();
		String firstName = customer[0];
		String lastName = customer[1];

		submitValidBillingAddress(firstName, lastName);

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

		Assert.assertTrue(checkoutPage.getOrderNumberText().contains("Order number:"),
				"Order number should be displayed");
	}

	@DataProvider(name = "offlinePaymentFees")
	public Object[][] offlinePaymentFees() {
		return new Object[][] { { "Cash On Delivery", "7.00" }, { "Check / Money Order", "5.00" } };
	}

	@Test(groups = { "checkout", "regression" }, dataProvider = "offlinePaymentFees")
	public void verifyOfflinePaymentMethodsApplyCorrectAdditionalFees(String paymentMethod, String expectedFee) {

		openCheckoutWithProductInCart();

		String[] customer = generateCustomerData();
		submitValidBillingAddress(customer[0], customer[1]);

		checkoutPage.clickShippingContinueButton();
		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		if (paymentMethod.equals("Cash On Delivery")) {
			checkoutPage.selectCashOnDeliveryPaymentMethod();
		} else {
			checkoutPage.selectCheckMoneyOrderPaymentMethod();
		}

		checkoutPage.clickPaymentMethodContinueButton();
		checkoutPage.clickPaymentInfoContinueButton();

		Assert.assertEquals(checkoutPage.getPaymentAdditionalFee(), expectedFee,
				paymentMethod + " should apply correct payment fee");
	}

	@Test(groups = { "checkout", "guest", "regression" })
	public void verifyGuestUserCanCompleteCheckout() {

		openCheckoutAsGuestWithProductInCart();

		String[] customer = generateCustomerData();
		String firstName = customer[0];
		String lastName = customer[1];

		submitValidBillingAddress(firstName, lastName);

		Assert.assertTrue(
				checkoutPage.isShippingStepContentDisplayed() || checkoutPage.isShippingMethodContentDisplayed(),
				"Guest checkout should continue to shipping address or shipping method after billing");

		if (checkoutPage.isShippingStepContentDisplayed()) {
			checkoutPage.clickShippingContinueButton();
		}

		checkoutPage.selectGroundShippingMethod();
		checkoutPage.clickShippingMethodContinueButton();

		checkoutPage.selectCreditCardPaymentMethod();
		checkoutPage.clickPaymentMethodContinueButton();

		checkoutPage.fillCreditCardInformation("Visa", firstName + " " + lastName,
				FakeCreditCardGenerator.generateVisa(), FakeCreditCardGenerator.generateExpirationMonth(),
				FakeCreditCardGenerator.generateExpirationYear(), FakeCreditCardGenerator.generateCardCode("Visa"));

		checkoutPage.clickPaymentInfoContinueButton();

		Assert.assertTrue(checkoutPage.isConfirmOrderStepDisplayed(),
				"Confirm Order step should be displayed for guest checkout");

		checkoutPage.clickConfirmOrderButton();

		Assert.assertEquals(checkoutPage.getOrderCompletedMessage(), "Your order has been successfully processed!",
				"Guest order completed message is incorrect");

		Assert.assertTrue(checkoutPage.getOrderNumberText().contains("Order number:"),
				"Guest order number should be displayed");
	}
}