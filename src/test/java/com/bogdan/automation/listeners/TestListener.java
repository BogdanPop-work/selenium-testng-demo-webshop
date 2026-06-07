package com.bogdan.automation.listeners;

import com.bogdan.automation.driver.DriverManager;
import com.bogdan.automation.utils.ScreenshotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("STARTED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("PASSED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        logger.error("FAILED: {}", testName, result.getThrowable());

        String screenshotPath = ScreenshotUtils.takeScreenshot(
                DriverManager.getDriver(),
                testName
        );

        logger.error("Screenshot saved at: {}", screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("SKIPPED: {}", result.getMethod().getMethodName());
    }
}