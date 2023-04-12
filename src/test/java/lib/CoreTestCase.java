package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase extends TestCase {

    protected RemoteWebDriver driver;

    protected Platform Platform;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomePageForIOSApp();
        this.openWikiWebPageForMobileWeb();
    }

    protected void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void rotateScreenLandscape() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void backgroundUp(int seconds) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofMillis(seconds));
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for " + Platform.getInstance().getPlatformVar());
        }
    }

    protected void openWikiWebPageForMobileWeb()
    {
        if (Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org/");
        } else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    public void skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            WelcomePageObject WelcomePageObject = new WelcomePageObject((AppiumDriver) driver);
            WelcomePageObject.clickSkip();
        }
    }
}
