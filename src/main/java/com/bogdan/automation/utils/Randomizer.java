package com.bogdan.automation.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
}