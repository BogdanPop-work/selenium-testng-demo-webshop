package com.bogdan.automation.utils;

public class TestDataGenerator {

    public static String generateUniqueEmail() {

        return "bogdan.automation."
                + System.currentTimeMillis()
                + "@test.com";
    }
}