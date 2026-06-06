package com.bogdan.automation.driver;

import com.bogdan.automation.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

	public WebDriver initializeDriver() {

		String browser = ConfigReader.getProperty("browser");
		boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

		WebDriver driver = switch (browser.toLowerCase()) {

		case "chrome" -> {
			WebDriverManager.chromedriver().setup();

			ChromeOptions options = new ChromeOptions();

			if (headless) {
				options.addArguments("--headless=new");
				options.addArguments("--window-size=1920,1080");
			}

			yield new ChromeDriver(options);
		}

		case "firefox" -> {
			WebDriverManager.firefoxdriver().setup();

			FirefoxOptions options = new FirefoxOptions();

			if (headless) {
				options.addArguments("-headless");
			}

			yield new FirefoxDriver(options);
		}

		case "edge" -> {
			WebDriverManager.edgedriver().setup();

			EdgeOptions options = new EdgeOptions();

			if (headless) {
				options.addArguments("--headless=new");
				options.addArguments("--window-size=1920,1080");
			}

			yield new EdgeDriver(options);
		}

		default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
		};

		driver.manage().window().maximize();

		return driver;
	}
}