package com.example.framework;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TestNG listener for handling test events and reporting
 */
public class TestListener implements ITestListener {
    
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();
        String description = result.getMethod().getDescription();
        
        TestReporter.createTest(testName, description != null ? description : "Test: " + testName);
        TestReporter.assignCategory(className);
        TestReporter.logInfo("Test started: " + testName);
        
        logger.info("Starting test: {}.{}", className, testName);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        TestReporter.logPass("Test passed: " + testName);
        logger.info("Test passed: {}", testName);
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        
        TestReporter.logFail("Test failed: " + testName);
        if (throwable != null) {
            TestReporter.logFail("Error: " + throwable.getMessage());
        }
        
        // Take screenshot on failure
        takeScreenshot(result);
        
        logger.error("Test failed: {}", testName, throwable);
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        
        TestReporter.logSkip("Test skipped: " + testName);
        if (throwable != null) {
            TestReporter.logSkip("Reason: " + throwable.getMessage());
        }
        
        logger.warn("Test skipped: {}", testName);
    }
    
    /**
     * Take screenshot on test failure
     */
    private void takeScreenshot(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = null;
        
        // Try to get WebDriver from test class
        if (testClass instanceof BaseTest) {
            driver = ((BaseTest) testClass).getDriver();
        }
        
        if (driver != null) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
                
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
                String testName = result.getMethod().getMethodName();
                String screenshotName = testName + "_" + timestamp + ".png";
                String screenshotPath = "test-output/screenshots/" + screenshotName;
                
                // Create screenshots directory if it doesn't exist
                File screenshotDir = new File("test-output/screenshots");
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdirs();
                }
                
                Files.write(Paths.get(screenshotPath), screenshotBytes);
                TestReporter.addScreenshot(screenshotPath, "Screenshot on failure");
                
                logger.info("Screenshot saved: {}", screenshotPath);
                
            } catch (IOException e) {
                logger.error("Failed to take screenshot", e);
            }
        }
    }
}
