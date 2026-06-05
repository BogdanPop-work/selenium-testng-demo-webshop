package com.bogdan.automation.pages;

import org.openqa.selenium.WebDriver;

/**
 * Base page containing common functionality shared by all page objects.
 */
public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void openUrl(String url) {
        driver.get(url);
    }
}