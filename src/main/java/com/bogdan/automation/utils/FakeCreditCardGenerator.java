package com.bogdan.automation.utils;

import java.time.Year;
import java.util.Random;

public class FakeCreditCardGenerator {

	private static final Random RANDOM = new Random();

	public static String generateVisa() {
		return generateCardNumber("4", 16);
	}

	public static String generateMasterCard() {
		return generateCardNumber("5", 16);
	}

	public static String generateDiscover() {
		return generateCardNumber("6", 16);
	}

	public static String generateAmex() {
		return generateCardNumber("37", 15);
	}

	private static String generateCardNumber(String prefix, int length) {

		StringBuilder cardNumber = new StringBuilder(prefix);

		while (cardNumber.length() < length - 1) {
			cardNumber.append(RANDOM.nextInt(10));
		}

		int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());

		cardNumber.append(checkDigit);

		return cardNumber.toString();
	}

	private static int calculateLuhnCheckDigit(String number) {

		int sum = 0;
		boolean alternate = true;

		for (int i = number.length() - 1; i >= 0; i--) {

			int digit = Character.getNumericValue(number.charAt(i));

			if (alternate) {
				digit *= 2;

				if (digit > 9) {
					digit -= 9;
				}
			}

			sum += digit;
			alternate = !alternate;
		}

		return (10 - (sum % 10)) % 10;
	}

	public static String generateCardCode(String cardType) {

		if ("Amex".equalsIgnoreCase(cardType)) {
			return String.valueOf(1000 + RANDOM.nextInt(9000));
		}

		return String.valueOf(100 + RANDOM.nextInt(900));
	}

	public static String generateExpirationMonth() {
		return String.format("%02d", 1 + RANDOM.nextInt(12));
	}

	public static String generateExpirationYear() {
		return String.valueOf(Year.now().getValue() + 1 + RANDOM.nextInt(5));
	}

}