package com.bogdan.automation.base;

import com.bogdan.automation.driver.DriverFactory;
import com.bogdan.automation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

	protected WebDriver driver;

	@BeforeMethod
	public void setUp() {
		DriverFactory driverFactory = new DriverFactory();
		driver = driverFactory.initializeDriver();
		driver.get(ConfigReader.getProperty("baseUrl"));
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}