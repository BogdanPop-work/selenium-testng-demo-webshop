package com.bogdan.automation.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

	private static final Random RANDOM = new Random();

	private Randomizer() {
	}

	public static <T> T getRandomItem(List<T> items) {
		return items.get(RANDOM.nextInt(items.size()));
	}

	public static <T> T getRandomKey(Map<T, ?> map) {
		List<T> keys = new ArrayList<>(map.keySet());
		return getRandomItem(keys);
	}

	public static <T> List<T> getRandomItems(List<T> items) {
		List<T> selectedItems = new ArrayList<>();

		for (T item : items) {
			if (RANDOM.nextBoolean()) {
				selectedItems.add(item);
			}
		}

		return selectedItems;
	}

	public static int getRandomInt(int minInclusive, int maxInclusive) {
		return ThreadLocalRandom.current().nextInt(minInclusive, maxInclusive + 1);
	}

	private static final List<String> FIRST_NAMES = List.of("John", "Michael", "David", "James", "Robert", "William",
			"Daniel", "Thomas", "Mark", "Richard");

	private static final List<String> LAST_NAMES = List.of("Smith", "Johnson", "Williams", "Brown", "Jones", "Miller",
			"Davis", "Wilson", "Taylor", "Clark");

	public static String getRandomFirstName() {
		return getRandomItem(FIRST_NAMES);
	}

	public static String getRandomLastName() {
		return getRandomItem(LAST_NAMES);
	}

	public static <T> List<T> getRandomItems(List<T> items, int count) {

		if (items == null || items.isEmpty()) {
			throw new IllegalArgumentException("Items list cannot be null or empty");
		}

		if (count > items.size()) {
			throw new IllegalArgumentException("Requested item count is greater than available items. Requested: "
					+ count + ", available: " + items.size());
		}

		List<T> shuffledItems = new ArrayList<>(items);
		Collections.shuffle(shuffledItems);

		return shuffledItems.stream().limit(count).toList();
	}
}