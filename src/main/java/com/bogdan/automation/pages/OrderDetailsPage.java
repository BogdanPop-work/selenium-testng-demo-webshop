package com.bogdan.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderDetailsPage extends BasePage {

    private By pageTitle = By.cssSelector(".page-title h1");
    private By orderNumber = By.cssSelector(".order-overview .order-number strong");
    private By orderStatus = By.cssSelector(".order-overview .order-details span:nth-child(2)");
    private By orderTotal = By.cssSelector(".order-overview .order-total strong");
    private By productsSection = By.cssSelector(".section.products");
    private By billingAddress = By.cssSelector(".billing-info");
    private By shippingAddress = By.cssSelector(".shipping-info");

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitleText() {
        return getText(pageTitle);
    }

    public String getOrderNumberText() {
        return getText(orderNumber);
    }

    public String getOrderStatusText() {
        return getText(orderStatus);
    }

    public String getOrderTotalText() {
        return getText(orderTotal);
    }

    public boolean isProductsSectionDisplayed() {
        return isDisplayed(productsSection);
    }

    public boolean isBillingAddressDisplayed() {
        return isDisplayed(billingAddress);
    }

    public boolean isShippingAddressDisplayed() {
        return isDisplayed(shippingAddress);
    }
}