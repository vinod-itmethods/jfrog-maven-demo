package com.example.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Base test class that provides WebDriver setup and teardown functionality
 */
public class BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    private String browserName;
    private boolean headless;
    
    @BeforeMethod
    @Parameters({"browser", "headless"})
    public void setUp(@Optional("chrome") String browser, @Optional("false") String headlessMode) {
        this.browserName = browser.toLowerCase();
        this.headless = Boolean.parseBoolean(headlessMode);
        
        logger.info("Setting up WebDriver for browser: {} (headless: {})", browserName, headless);
        
        driver = createWebDriver(browserName, headless);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
        }
    }
    
    /**
     * Creates WebDriver instance based on browser type
     */
    private WebDriver createWebDriver(String browserName, boolean headless) {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(chromeOptions);
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                return new EdgeDriver(edgeOptions);
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
    }
    
    /**
     * Get current WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }
}
