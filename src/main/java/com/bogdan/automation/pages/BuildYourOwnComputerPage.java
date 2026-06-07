package com.bogdan.automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bogdan.automation.models.ComputerConfiguration;
import com.bogdan.automation.models.ConfigurableProductData;
import com.bogdan.automation.models.ProductAttributes;
import com.bogdan.automation.models.ProductOption;
import com.bogdan.automation.utils.Randomizer;

public class BuildYourOwnComputerPage extends ProductPage {

	public BuildYourOwnComputerPage(WebDriver driver) {
		super(driver);
	}

	private By optionByLabel(String optionName) {
		return By.xpath("//label[contains(normalize-space(),'" + optionName + "')]/preceding-sibling::input");
	}

	private void selectOption(String optionName) {
		click(optionByLabel(optionName));
	}

	public ComputerConfiguration buildRandomComputer(ConfigurableProductData configurableProduct) {

		ProductAttributes attributes = configurableProduct.attributes();

		ProductOption processor = Randomizer.getRandomItem(attributes.processors());
		ProductOption ram = Randomizer.getRandomItem(attributes.ramOptions());
		ProductOption hdd = Randomizer.getRandomItem(attributes.hddOptions());

		List<ProductOption> selectedSoftwareOptions = Randomizer.getRandomItems(attributes.softwareOptions());

		selectOption(processor.name());
		selectOption(ram.name());
		selectOption(hdd.name());

		List<String> selectedSoftwareNames = new ArrayList<>();

		for (ProductOption software : selectedSoftwareOptions) {
			selectOption(software.name());
			selectedSoftwareNames.add(software.name());
		}

		double expectedPrice = configurableProduct.basePrice() + processor.price() + ram.price() + hdd.price();

		for (ProductOption software : selectedSoftwareOptions) {
			expectedPrice += software.price();
		}

		ComputerConfiguration configuration = new ComputerConfiguration(processor.name(), ram.name(), hdd.name(),
				selectedSoftwareNames, expectedPrice);

		return configuration;
	}
}