package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GiftCardPage extends ProductPage {

	private By recipientNameField = By.cssSelector(".recipient-name");
	private By recipientEmailField = By.cssSelector(".recipient-email");
	private By senderNameField = By.cssSelector(".sender-name");
	private By senderEmailField = By.cssSelector(".sender-email");
	private By messageField = By.cssSelector(".message");
	private By validationMessages = By.cssSelector("#bar-notification .content");

	public GiftCardPage(WebDriver driver) {
		super(driver);
	}

	public void enterRecipientName(String recipientName) {
		type(recipientNameField, recipientName);
	}

	public void enterRecipientEmail(String recipientEmail) {
		type(recipientEmailField, recipientEmail);
	}

	public void enterSenderName(String senderName) {
		type(senderNameField, senderName);
	}

	public void enterSenderEmail(String senderEmail) {
		type(senderEmailField, senderEmail);
	}

	public void enterMessage(String message) {
		type(messageField, message);
	}

	public void completeGiftCardDetails(String recipientName, String recipientEmail, String senderName,
			String senderEmail, String message) {

		enterRecipientName(recipientName);
		enterRecipientEmail(recipientEmail);
		enterSenderName(senderName);
		enterSenderEmail(senderEmail);
		enterMessage(message);
	}

	private String getValidationText() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(validationMessages));

		return driver.findElements(validationMessages).stream().map(element -> element.getText().trim())
				.reduce("", (first, second) -> first + " " + second).trim();
	}

	public boolean isRecipientNameValidationDisplayed() {
		return getValidationText().contains("Enter valid recipient name");
	}

	public boolean isRecipientEmailValidationDisplayed() {
		return getValidationText().contains("Enter valid recipient email");
	}

}