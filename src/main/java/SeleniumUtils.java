import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;

public class SeleniumUtils
{
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;

    // Launch browser and portal
    public void launchPortal(String url)
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // default explicit wait
        System.out.println("Portal launched: " + url);
    }

    public void closeBrowser()
    {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed");
        }
    }

    // ------------------- Basic Actions -------------------

    public void click(By locator)
    {
        waitUntilClickable(locator).click();
        System.out.println("Clicked element: " + locator);
    }

    public void type(By locator, String text)
    {
        WebElement element = waitUntilVisible(locator);
        element.clear();
        element.sendKeys(text);
        System.out.println("Typed '" + text + "' in " + locator);
    }

    public String getText(By locator)
    {
        String text = waitUntilVisible(locator).getText();
        System.out.println("Text from " + locator + ": " + text);
        return text;
    }

    public void selectDropdownByText(By locator, String text)
    {
        Select dropdown = new Select(waitUntilVisible(locator));
        dropdown.selectByVisibleText(text);
        System.out.println("Selected '" + text + "' from dropdown " + locator);
    }

    public void selectDropdownByIndex(By locator, int index)
    {
        Select dropdown = new Select(waitUntilVisible(locator));
        dropdown.selectByIndex(index);
        System.out.println("Selected index '" + index + "' from dropdown " + locator);
    }

    public boolean isDisplayed(By locator)
    {
        boolean displayed = waitUntilVisible(locator).isDisplayed();
        System.out.println("Element " + locator + " displayed: " + displayed);
        return displayed;
    }

    public List<WebElement> getElements(By locator)
    {
        return driver.findElements(locator);
    }

    // ------------------- Alert Handling -------------------

    public void acceptAlert()
    {
        waitUntilAlertPresent().accept();
        System.out.println("Alert accepted");
    }
    public void dismissAlert()
    {
        waitUntilAlertPresent().dismiss();
        System.out.println("Alert dismissed");
    }
    public String getAlertText()
    {
        String text = waitUntilAlertPresent().getText();
        System.out.println("Alert text: " + text); return text;
    }

    // ------------------- Navigation -------------------

    public void navigateBack()
    {
        driver.navigate().back();
        System.out.println("Navigated back");
    }
    public void navigateForward()
    {
        driver.navigate().forward();
        System.out.println("Navigated forward");
    }
    public void refreshPage()
    {
        driver.navigate().refresh();
        System.out.println("Page refreshed");
    }

    // ------------------- Wait Utilities -------------------

    // Explicit wait until element is visible
    public WebElement waitUntilVisible(By locator)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Explicit wait until element is clickable
    public WebElement waitUntilClickable(By locator)
    {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait until alert is present
    public Alert waitUntilAlertPresent()
    {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    // Fluent Wait (customizable)
    public WebElement fluentWait(By locator, int timeoutSeconds, int pollingSeconds)
    {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofSeconds(pollingSeconds))
                .ignoring(NoSuchElementException.class);

        return fluentWait.until(new Function<WebDriver, WebElement>()
        {
            public WebElement apply(WebDriver driver)
            {
                return driver.findElement(locator);
            }
        });
    }

    // ------------------- Advanced Actions -------------------

    public void mouseHover(By locator)
    {
        actions.moveToElement(waitUntilVisible(locator)).perform();
        System.out.println("Hovered over element: " + locator);
    }

    public void dragAndDrop(By source, By target)
    {
        actions.dragAndDrop(waitUntilVisible(source), waitUntilVisible(target)).perform();
        System.out.println("Dragged " + source + " to " + target);
    }

    public void rightClick(By locator)
    {
        actions.contextClick(waitUntilVisible(locator)).perform();
        System.out.println("Right clicked on element: " + locator);
    }

    public void doubleClick(By locator)
    {
        actions.doubleClick(waitUntilVisible(locator)).perform();
        System.out.println("Double clicked on element: " + locator);
    }

    public void pressEnter(By locator)
    {
        waitUntilVisible(locator).sendKeys(Keys.ENTER);
        System.out.println("Pressed Enter on element: " + locator);
    }

    public void pressTab(By locator)
    {
        waitUntilVisible(locator).sendKeys(Keys.TAB);
        System.out.println("Pressed Tab on element: " + locator);
    }

    // ------------------- Screenshots -------------------

    public void takeScreenshot(String filePath)
    {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        File destFile = new File(filePath);
        try
        {
            FileUtils.copyFile(srcFile, destFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Screenshot saved to: " + filePath);
    }

    // ------------------- Utility -------------------

    public void waitFor(int seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000L);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver()
    {
        return driver;
    }
}
