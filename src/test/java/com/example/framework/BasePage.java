package com.example.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base page class that provides common functionality for all page objects
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Wait for element to be visible and return it
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable and return it
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be present and return it
     */
    protected WebElement waitForElementToBePresent(By locator) {
        logger.debug("Waiting for element to be present: {}", locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Click on element after waiting for it to be clickable
     */
    protected void clickElement(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        element.click();
        logger.debug("Clicked element: {}", locator);
    }
    
    /**
     * Type text into element after waiting for it to be visible
     */
    protected void typeText(By locator, String text) {
        WebElement element = waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
        logger.debug("Typed text '{}' into element: {}", text, locator);
    }
    
    /**
     * Get text from element after waiting for it to be visible
     */
    protected String getText(By locator) {
        WebElement element = waitForElementToBeVisible(locator);
        String text = element.getText();
        logger.debug("Got text '{}' from element: {}", text, locator);
        return text;
    }
    
    /**
     * Select dropdown option by visible text
     */
    protected void selectByVisibleText(By locator, String text) {
        WebElement element = waitForElementToBeVisible(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
        logger.debug("Selected option '{}' from dropdown: {}", text, locator);
    }
    
    /**
     * Select dropdown option by value
     */
    protected void selectByValue(By locator, String value) {
        WebElement element = waitForElementToBeVisible(locator);
        Select select = new Select(element);
        select.selectByValue(value);
        logger.debug("Selected option with value '{}' from dropdown: {}", value, locator);
    }
    
    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean displayed = element.isDisplayed();
            logger.debug("Element {} is displayed: {}", locator, displayed);
            return displayed;
        } catch (Exception e) {
            logger.debug("Element {} is not displayed", locator);
            return false;
        }
    }
    
    /**
     * Get all elements matching the locator
     */
    protected List<WebElement> getElements(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        logger.debug("Found {} elements for locator: {}", elements.size(), locator);
        return elements;
    }
    
    /**
     * Hover over element
     */
    protected void hoverOverElement(By locator) {
        WebElement element = waitForElementToBeVisible(locator);
        actions.moveToElement(element).perform();
        logger.debug("Hovered over element: {}", locator);
    }
    
    /**
     * Double click on element
     */
    protected void doubleClickElement(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        actions.doubleClick(element).perform();
        logger.debug("Double clicked element: {}", locator);
    }
    
    /**
     * Right click on element
     */
    protected void rightClickElement(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        actions.contextClick(element).perform();
        logger.debug("Right clicked element: {}", locator);
    }
    
    /**
     * Scroll to element
     */
    protected void scrollToElement(By locator) {
        WebElement element = waitForElementToBePresent(locator);
        actions.moveToElement(element).perform();
        logger.debug("Scrolled to element: {}", locator);
    }
    
    /**
     * Get current page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.debug("Current page title: {}", title);
        return title;
    }
    
    /**
     * Get current page URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.debug("Current page URL: {}", url);
        return url;
    }
}
