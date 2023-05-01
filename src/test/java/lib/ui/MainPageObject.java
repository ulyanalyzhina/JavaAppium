package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import lib.CoreTestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject extends CoreTestCase {


    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }


    public int getAmountOfElements(String locator) {
        By by = this.getLocatorWithByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public List<WebElement> waitForElementsListPresentAndToBeMoreThan(String locator, String error_message, long timeout_in_seconds, int numberOfElements) {
        By by = this.getLocatorWithByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, numberOfElements)
        );
    }

    public WebElement waitForElementPresentBy(String locator, String error_message, long timeout_in_seconds) {
        By by = this.getLocatorWithByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementToBeClickable(String locator, String error_message, long timeout_in_seconds) {
        By by = this.getLocatorWithByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.elementToBeClickable(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_message) {
        By by = this.getLocatorWithByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementByAndClick(String locator, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(locator, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    public WebElement waitForElementByAndDoubleClick(String locator, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(locator, error_message, timeout_in_seconds);
        element.click();
        element.click();
        return element;
    }

    public WebElement waitForElementByAndSendKeys(String locator, String value, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresentBy(locator, error_message, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        By by = this.getLocatorWithByString(locator);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public void assertElementHasText(String locator, String expectedValue, String error_message) {
        WebElement elementWithText = waitForElementPresentBy(locator, error_message, 5);
        String text;
        if(Platform.getInstance().isAndroid()){
            text = elementWithText.getAttribute("text");
        }  else if (Platform.getInstance().isIOS()) {
            text = elementWithText.getAttribute("name");
        } else {
            text = elementWithText.getAttribute("innerHTML");
        }

        Assert.assertEquals(
                error_message,
                expectedValue,
                text
        );
    }

    public void swipeUp(int timeOfSwipe) {
        if(driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver)driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);
            action.press(PointOption.point(x, start_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, end_y)).release().perform();
        } else {
            System.out.println("Method swipeUp() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorWithByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresentBy(locator, "Cannot find Element by swiping. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void scrollWebPageUp(){
        if(Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for mobile_web");
        }
    }

//    public void scrollWebPageTillWebElementNotVisible(String locator, String error_message, int max_swipes){
//        int already_swiped = 0;
//
//        WebElement element = this.waitForElementPresentBy(locator, error_message, 10);
//        while(!this.isElementLocatedOnTheScreen(locator)){
//            System.out.println(!this.isElementLocatedOnTheScreen(locator)+"is located?");
//            scrollWebPageUp();
//            System.out.println(already_swiped);
//            ++already_swiped;
//            if (already_swiped > max_swipes){
//                System.out.println(element.isDisplayed());
//                Assert.assertTrue(error_message, element.isDisplayed());
//            }
//        }
//    }

    public void scrollWebPageTitleElementNotVisible(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        WebElement element = this.waitForElementPresent(locator, error_message);
        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++already_swiped;
            if (already_swiped > max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentBy(
                locator,
                error_message,
                timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeElementToLeft(String locator, String error_message) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresentBy(
                    locator,
                    error_message,
                    10);

            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            System.out.println(left_x + " :left_x");
            System.out.println(right_x + " :right_x");
            System.out.println(upper_y + " :upper_y");
            System.out.println(lower_y + " :lower_y");
            System.out.println(middle_y + " :middle_y");
            System.out.println(element.getSize().getWidth() + " :left_x");

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.press(PointOption.point(right_x, middle_y));
            action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)));
            if (Platform.getInstance().isAndroid()) {
                action.moveTo(PointOption.point(left_x, middle_y));
            } else {
                int offset_x = (-1 * element.getSize().getWidth());
                System.out.println(offset_x + " :offset_x");
                System.out.println(locator + " :element");
                action.moveTo(PointOption.point(left_x / 2, 283));
            }

            action.release();
            action.perform();

        } else {
            System.out.println("Method swipeUp() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void assertElementNotPresent(String locator, String error_message) {
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements > 0) {
            String default_message = "An element ' " + locator + " 'supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public void assertElementPresent(String locator, String error_message) {

        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements == 0) {
            String default_message = "An element ' " + locator + " 'supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        } else if (amount_of_elements > 1) {
            String default_message = "An element ' " + locator + " 'supposed to be only one but found more then one";
            throw new AssertionError(default_message + " " + error_message);
        }
    }
    private By getLocatorWithByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);

        if(exploded_locator.length == 2){
            String by_type = exploded_locator[0];
            String locator = exploded_locator[1];
            if (by_type.equals("xpath")) {
                return By.xpath(locator);
            } else if (by_type.equals("id")) {
                return By.id(locator);
            } else if (by_type.equals("css")) {
                return By.cssSelector(locator);
            } else {
                throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
            }
        } else {
            throw new IllegalArgumentException("The type of locator you set was incorrect" + locator_with_type);
        }
    }

    public void assertAllElementsHaveText(List<WebElement> elements, String match, String locator) {
        By by = this.getLocatorWithByString(locator);
        elements.forEach(el -> {
            String text = el.findElement(
                    (by)
            ).getText();
            Assert.assertTrue(text.toLowerCase().contains(match.toLowerCase()));
        });
    }

    public void assertItemHasTitleAndDescription(
            List<WebElement> els,
            int index,
            String title,
            String description,
            String locatorTitle,
            String locatorDescription
    ) {

        if(Platform.getInstance().isMW()){
            String  byDescriptionStr= locatorDescription.replace("{replace}", new StringBuilder().append(index).toString());
            String byTitleStr = locatorTitle.replace("{replace}", new StringBuilder().append(index).toString());

            By byDescription = this.getLocatorWithByString(byDescriptionStr);
            By byTitle = this.getLocatorWithByString(byTitleStr);

            Assert.assertEquals(description, driver.findElement(byDescription).getText());
            Assert.assertEquals(title, driver.findElement(byTitle).getAttribute("title"));
        } else {
            By byTitle = this.getLocatorWithByString(locatorTitle);
            Assert.assertEquals((title), els.get(index)
                    .findElement(byTitle)
                    .getText());
            By byDescription = this.getLocatorWithByString(locatorDescription);
            Assert.assertEquals((description), els.get(index)
                    .findElement(byDescription)
                    .getText());
       }
    }

    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes ){
        int already_swiped = 0;
        //this.waitForElementPresentBy(locator, "Element" + locator + "not found", 60);
        while (!this.isElementLocatedOnTheScreen(locator)){
            System.out.println(!this.isElementLocatedOnTheScreen(locator) + "located?");
            if(already_swiped > max_swipes){
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

//    public boolean isElementLocatedOnTheScreen(String locator) {
//        int element_location_by_y = this.waitForElementPresentBy(
//                locator, "Cannot find Element by locator(for ios isElementLocatedOnTheScreen)" + locator, 15
//        ).getLocation().getY();
//        if(Platform.getInstance().isMW()){
//            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
//            Object js_result = JSExecutor.executeScript("return window.pageXOffset");
//            element_location_by_y -= Integer.parseInt(js_result.toString());
//        }
//        int screen_size_by_y = driver.manage().window().getSize().getHeight();
//        return element_location_by_y < screen_size_by_y;
//    }

    public boolean isElementLocatedOnTheScreen (String locator)
    {
        int element_location_by_y = this.waitForElementPresentBy(
                        locator,
                        "Cannot find element by locator", 10)
                .getLocation()
                .getY();

        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y-= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void clickElementToTheRightUpperConner(String locator, String error_message) {
        if(driver instanceof AppiumDriver) {

            WebElement element = this.waitForElementPresent(locator + "/..", error_message);
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;
            int width = element.getSize().getWidth();
            int point_to_click_x = (right_x + width) - 3;
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.tap(PointOption.point(point_to_click_x, point_to_click_y)).perform();
        } else {
            System.out.println("Method swipeUp() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public boolean isElementPresent(String locator)
    {
        return getAmountOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempts(String locator, String error_message, int amount_of_attempts)
    {
        int current_attempts = 0;
        boolean need_more_attemts = true;

        while (need_more_attemts) {
            try {
                this.waitForElementByAndClick(locator, error_message, 1);
                need_more_attemts = false;
            } catch (Exception e) {
                if (current_attempts > amount_of_attempts) {
                    this.waitForElementByAndClick(locator, error_message, 1);
                }
            }
            ++ current_attempts;
        }
    }

    public String takeScreenshot(String name){
        TakesScreenshot ts = (TakesScreenshot)this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken: " + path);
        } catch (Exception e) {
           System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch(IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return  bytes;
    }
}