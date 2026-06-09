package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

	private By checkoutSteps = By.id("checkout-steps");

	private By billingStep = By.id("opc-billing");
	private By shippingStep = By.id("opc-shipping");
	private By shippingMethodStep = By.id("opc-shipping_method");
	private By paymentMethodStep = By.id("opc-payment_method");
	private By paymentInfoStep = By.id("opc-payment_info");
	private By confirmOrderStep = By.id("opc-confirm_order");
	private By billingFirstName = By.id("BillingNewAddress_FirstName");
	private By billingLastName = By.id("BillingNewAddress_LastName");
	private By billingEmail = By.id("BillingNewAddress_Email");
	private By billingCountry = By.id("BillingNewAddress_CountryId");
	private By billingCity = By.id("BillingNewAddress_City");
	private By billingAddress1 = By.id("BillingNewAddress_Address1");
	private By billingZipPostalCode = By.id("BillingNewAddress_ZipPostalCode");
	private By billingPhoneNumber = By.id("BillingNewAddress_PhoneNumber");
	private By billingContinueButton = By.cssSelector(".new-address-next-step-button");
	private By billingStepContent = By.id("checkout-step-billing");
	private By shippingStepContent = By.id("checkout-step-shipping");
	private By billingAddressDropdown = By.id("billing-address-select");
	private By shippingAddressDropdown = By.id("shipping-address-select");
	private By shippingContinueButton = By.cssSelector("#shipping-buttons-container .new-address-next-step-button");
	private By shippingMethodContent = By.id("checkout-step-shipping-method");
	private By groundShipping = By.id("shippingoption_0");
	private By shippingMethodContinueButton = By.cssSelector(".shipping-method-next-step-button");
	private By paymentMethodContent = By.id("checkout-step-payment-method");

	private By cashOnDeliveryOption = By.id("paymentmethod_0");
	private By checkMoneyOrderOption = By.id("paymentmethod_1");
	private By creditCardOption = By.id("paymentmethod_2");
	private By purchaseOrderOption = By.id("paymentmethod_3");

	private By paymentMethodContinueButton = By.cssSelector(".payment-method-next-step-button");
	private By paymentInfoStepContent = By.id("checkout-step-payment-info");
	private By paymentInfoText = By.cssSelector("#checkout-payment-info-load .info");

	private By paymentInfoContinueButton = By.cssSelector(".payment-info-next-step-button");

	private By creditCardType = By.id("CreditCardType");
	private By cardholderName = By.id("CardholderName");
	private By cardNumber = By.id("CardNumber");
	private By expireMonth = By.id("ExpireMonth");
	private By expireYear = By.id("ExpireYear");
	private By cardCode = By.id("CardCode");
	private By confirmOrderButton = By.cssSelector(".confirm-order-next-step-button");
	private By orderCompletedMessage = By.cssSelector(".section.order-completed .title strong");
	private By orderNumber = By.cssSelector(".section.order-completed .details li");

	private By purchaseOrderNumber = By.id("PurchaseOrderNumber");

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	public boolean isCheckoutStepsDisplayed() {
		return isDisplayed(checkoutSteps);
	}

	public boolean isBillingStepDisplayed() {
		return isDisplayed(billingStep);
	}

	public boolean isShippingStepDisplayed() {
		return isDisplayed(shippingStep);
	}

	public boolean isShippingMethodStepDisplayed() {
		return isDisplayed(shippingMethodStep);
	}

	public boolean isPaymentMethodStepDisplayed() {
		return isDisplayed(paymentMethodStep);
	}

	public boolean isPaymentInfoStepDisplayed() {
		return isDisplayed(paymentInfoStep);
	}

	public boolean isConfirmOrderStepDisplayed() {
		return isDisplayed(confirmOrderStep);
	}

	public boolean isBillingStepContentDisplayed() {
		return isDisplayed(billingStepContent);
	}

	public boolean isBillingAddressDropdownDisplayed() {
		return driver.findElements(billingAddressDropdown).size() > 0;
	}

	public void fillBillingAddress(String firstName, String lastName, String email, String country, String city,
			String address1, String zipPostalCode, String phoneNumber) {

		type(billingFirstName, firstName);
		type(billingLastName, lastName);
		type(billingEmail, email);
		selectByVisibleText(billingCountry, country);
		type(billingCity, city);
		type(billingAddress1, address1);
		type(billingZipPostalCode, zipPostalCode);
		type(billingPhoneNumber, phoneNumber);
	}

	public void clickBillingContinueButton() {
		click(billingContinueButton);
	}

	public boolean isShippingStepContentDisplayed() {
		return isDisplayed(shippingStepContent);
	}

	public void waitForBillingFirstNameField() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(billingFirstName));
	}

	public void continueBillingAddress() {

		if (isBillingAddressDropdownDisplayed()) {
			clickBillingContinueButton();
			return;
		}

		throw new IllegalStateException("Billing address form flow not implemented yet");
	}

	public boolean isShippingAddressDropdownDisplayed() {
		return driver.findElements(shippingAddressDropdown).size() > 0;
	}

	public void clickShippingContinueButton() {
		click(shippingContinueButton);
	}

	public boolean isShippingMethodContentDisplayed() {
		return isDisplayed(shippingMethodContent);
	}

	public void selectGroundShippingMethod() {
		click(groundShipping);
	}

	public void clickShippingMethodContinueButton() {
		click(shippingMethodContinueButton);
	}

	public void fillCreditCardInformation(String cardType, String holderName, String number, String month, String year,
			String code) {

		selectByVisibleText(creditCardType, cardType);
		type(cardholderName, holderName);
		type(cardNumber, number);
		selectByVisibleText(expireMonth, month);
		selectByVisibleText(expireYear, year);
		type(cardCode, code);
	}

	public void fillPurchaseOrderNumber(String poNumber) {
		type(purchaseOrderNumber, poNumber);
	}

	public void clickPaymentInfoContinueButton() {
		click(paymentInfoContinueButton);
	}

	public boolean isPaymentMethodContentDisplayed() {
		return isDisplayed(paymentMethodContent);
	}

	public void selectCashOnDeliveryPaymentMethod() {
		click(cashOnDeliveryOption);
	}

	public void selectCheckMoneyOrderPaymentMethod() {
		click(checkMoneyOrderOption);
	}

	public void selectCreditCardPaymentMethod() {
		click(creditCardOption);
	}

	public void selectPurchaseOrderPaymentMethod() {
		click(purchaseOrderOption);
	}

	public void clickPaymentMethodContinueButton() {
		click(paymentMethodContinueButton);
	}

	public boolean isPaymentInfoStepContentDisplayed() {
		return isDisplayed(paymentInfoStepContent);
	}

	public String getPaymentInfoText() {
		return getText(paymentInfoText);
	}

	public void clickConfirmOrderButton() {
		click(confirmOrderButton);
	}

	public String getOrderCompletedMessage() {
		return getText(orderCompletedMessage);
	}

	public String getOrderNumberText() {
		return getText(orderNumber);
	}

	public String getPaymentAdditionalFee() {
		return getText(By.xpath(
				"//span[contains(text(),'Payment method additional fee')]/ancestor::td/following-sibling::td//span[@class='product-price']"));
	}
}