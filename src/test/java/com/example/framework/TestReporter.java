package com.example.framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating test reports using ExtentReports
 */
public class TestReporter {
    
    private static final Logger logger = LoggerFactory.getLogger(TestReporter.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    /**
     * Initialize ExtentReports
     */
    public static void initReports() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String reportPath = "test-output/ExtentReport_" + timestamp + ".html";
            
            // Create directory if it doesn't exist
            File reportDir = new File("test-output");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Selenium Test Report");
            sparkReporter.config().setReportName("Data-Driven Test Execution Report");
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            
            logger.info("ExtentReports initialized. Report will be saved to: {}", reportPath);
        }
    }
    
    /**
     * Create a new test in the report
     */
    public static void createTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
        logger.debug("Created test: {} - {}", testName, description);
    }
    
    /**
     * Log info message to the report
     */
    public static void logInfo(String message) {
        if (test.get() != null) {
            test.get().log(Status.INFO, message);
        }
        logger.info(message);
    }
    
    /**
     * Log pass message to the report
     */
    public static void logPass(String message) {
        if (test.get() != null) {
            test.get().log(Status.PASS, message);
        }
        logger.info("PASS: {}", message);
    }
    
    /**
     * Log fail message to the report
     */
    public static void logFail(String message) {
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
        }
        logger.error("FAIL: {}", message);
    }
    
    /**
     * Log skip message to the report
     */
    public static void logSkip(String message) {
        if (test.get() != null) {
            test.get().log(Status.SKIP, message);
        }
        logger.warn("SKIP: {}", message);
    }
    
    /**
     * Log warning message to the report
     */
    public static void logWarning(String message) {
        if (test.get() != null) {
            test.get().log(Status.WARNING, message);
        }
        logger.warn("WARNING: {}", message);
    }
    
    /**
     * Add screenshot to the report
     */
    public static void addScreenshot(String screenshotPath, String description) {
        if (test.get() != null) {
            test.get().addScreenCaptureFromPath(screenshotPath, description);
        }
        logger.debug("Added screenshot to report: {}", screenshotPath);
    }
    
    /**
     * Assign category to the current test
     */
    public static void assignCategory(String category) {
        if (test.get() != null) {
            test.get().assignCategory(category);
        }
        logger.debug("Assigned category: {}", category);
    }
    
    /**
     * Assign author to the current test
     */
    public static void assignAuthor(String author) {
        if (test.get() != null) {
            test.get().assignAuthor(author);
        }
        logger.debug("Assigned author: {}", author);
    }
    
    /**
     * Flush the reports
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed");
        }
    }
    
    /**
     * Get current test instance
     */
    public static ExtentTest getCurrentTest() {
        return test.get();
    }
}
