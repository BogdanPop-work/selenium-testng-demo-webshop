package com.bogdan.automation.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bogdan.automation.models.ComputerConfiguration;
import com.bogdan.automation.utils.Randomizer;

public class BuildYourOwnComputerPage extends ProductPage {

	private static final Logger logger = LoggerFactory.getLogger(BuildYourOwnComputerPage.class);
	private Map<String, By> processors = Map.of("Slow", By.id("product_attribute_74_5_26_80"), "Medium",
			By.id("product_attribute_74_5_26_81"), "Fast", By.id("product_attribute_74_5_26_82"));

	private Map<String, By> ramOptions = Map.of("2 GB", By.id("product_attribute_74_6_27_83"), "4GB",
			By.id("product_attribute_74_6_27_84"), "8GB", By.id("product_attribute_74_6_27_85"));

	private Map<String, By> hddOptions = Map.of("320 GB", By.id("product_attribute_74_3_28_86"), "400 GB",
			By.id("product_attribute_74_3_28_87"));

	private Map<String, By> softwareOptions = Map.of("Image Viewer", By.id("product_attribute_74_8_29_88"),
			"Office Suite", By.id("product_attribute_74_8_29_89"), "Other Office Suite",
			By.id("product_attribute_74_8_29_90"));

	private Map<String, Double> processorPrices = Map.of("Slow", 0.0, "Medium", 15.0, "Fast", 100.0);

	private Map<String, Double> ramPrices = Map.of("2 GB", 0.0, "4GB", 20.0, "8GB", 60.0);

	private Map<String, Double> hddPrices = Map.of("320 GB", 0.0, "400 GB", 100.0);

	private Map<String, Double> softwarePrices = Map.of("Image Viewer", 5.0, "Office Suite", 100.0,
			"Other Office Suite", 40.0);
	private static final double BASE_PRICE = 1800.0;

	public BuildYourOwnComputerPage(WebDriver driver) {
		super(driver);
	}

	public void selectProcessor(String processor) {
		click(processors.get(processor));
	}

	public void selectRam(String ram) {
		click(ramOptions.get(ram));
	}

	public void selectHdd(String hdd) {
		click(hddOptions.get(hdd));
	}

	public void selectSoftware(String software) {
		click(softwareOptions.get(software));
	}

	public ComputerConfiguration buildComputer(String processor, String ram, String hdd, List<String> software) {

		selectProcessor(processor);
		selectRam(ram);
		selectHdd(hdd);

		for (String softwareOption : software) {
			selectSoftware(softwareOption);
		}

		double expectedPrice = BASE_PRICE;

		expectedPrice += processorPrices.get(processor);
		expectedPrice += ramPrices.get(ram);
		expectedPrice += hddPrices.get(hdd);

		for (String softwareOption : software) {
			expectedPrice += softwarePrices.get(softwareOption);
		}

		return new ComputerConfiguration(processor, ram, hdd, software, expectedPrice);
	}

	public ComputerConfiguration buildRandomComputer() {

		String processor = Randomizer.getRandomKey(processors);
		String ram = Randomizer.getRandomKey(ramOptions);
		String hdd = Randomizer.getRandomKey(hddOptions);

		List<String> software = Randomizer.getRandomItems(new ArrayList<>(softwareOptions.keySet()));

		ComputerConfiguration configuration = buildComputer(processor, ram, hdd, software);

		logger.info("""

				===== RANDOM COMPUTER CONFIGURATION =====
				Processor: {}
				RAM: {}
				HDD: {}
				Software: {}
				Expected Price: {}
				=========================================

				""", configuration.processor(), configuration.ram(), configuration.hdd(),
				configuration.software(), configuration.expectedPrice());

		return configuration;
	}

}