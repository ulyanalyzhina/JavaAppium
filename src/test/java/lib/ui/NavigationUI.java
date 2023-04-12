package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {
    protected static String
            MY_LISTS_LINK,
            CLOSE_POP_UP_BUTTON,
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementByAndClick(
                    OPEN_NAVIGATION,
                    "Cannot find and click open navigation button",
                    5
            );
        } else {
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }


    public void clickMyList() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to my list",
                    5
            );
        }
//        this.waitForElementByAndClick(
//                MY_LISTS_LINK,
//                "Cannot find navigation button to my lists",
//                5
//        );
        if (Platform.getInstance().isIOS()) {
            this.waitForElementByAndDoubleClick(
                    CLOSE_POP_UP_BUTTON,
                    "Cannot find 'X' button to close pop-up",
                    15
            );
        }
    }
}
