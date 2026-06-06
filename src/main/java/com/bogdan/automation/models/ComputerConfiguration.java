package com.bogdan.automation.models;

import java.util.List;

public class ComputerConfiguration {

	private String processor;
	private String ram;
	private String hdd;
	private List<String> software;
	private double expectedPrice;

	public ComputerConfiguration(String processor, String ram, String hdd, List<String> software,
			double expectedPrice) {

		this.processor = processor;
		this.ram = ram;
		this.hdd = hdd;
		this.software = software;
		this.expectedPrice = expectedPrice;
	}

	public String getProcessor() {
		return processor;
	}

	public String getRam() {
		return ram;
	}

	public String getHdd() {
		return hdd;
	}

	public List<String> getSoftware() {
		return software;
	}

	public double getExpectedPrice() {
		return expectedPrice;
	}
}