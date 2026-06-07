package com.bogdan.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return null;
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String fileName = testName + "_" + timestamp + ".png";
        Path targetPath = Path.of("target", "screenshots", fileName);

        try {
            Files.createDirectories(targetPath.getParent());
            Files.copy(screenshot.toPath(), targetPath);
            return targetPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }
}