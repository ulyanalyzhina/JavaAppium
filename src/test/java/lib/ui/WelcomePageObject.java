package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{
    private static final String
    STEP_LEARN_MORE_LINK = "Learn more about Wikipedia",
    STEP_NEW_WAYS_TO_EXPLORE_TEXT = "New ways to explore",
    STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "Add or edit preferred languages",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "Learn more about data collected",
    NEXT_LINK = "//XCUIElementTypeButton[@name='Next']",
    GET_STARTED_BUTTON = "Get started",
    SKIP = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink(){
        this.waitForElementPresentBy(
                STEP_LEARN_MORE_LINK,
                "Cannot find 'Learn more about Wikipedia' link",
                10
        );
    }

    public void clickNextButton(){
        this.waitForElementByAndClick(
                NEXT_LINK,
                "Cannot find and click 'Next' link",
                10
        );
    }

    public void waitForNewWayToExploreText(){
        this.waitForElementByAndClick(
                STEP_NEW_WAYS_TO_EXPLORE_TEXT,
                "Cannot find and click 'New ways to explore'",
                10
        );
    }

    public void waitForAddOrEditPreferredLangText(){
        this.waitForElementPresentBy(
                STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK,
                "Cannot find and click 'Add or edit preferred language'",
                10
        );
    }
    public void waitForLearnMoreAboutDataCollectedText(){
        this.waitForElementPresentBy(
                STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK,
                "Cannot find and click 'Learn more about data collected'",
                10
        );
    }

    public void clickGetStartedButton(){
        this.waitForElementByAndClick(
                GET_STARTED_BUTTON,
                "Cannot find and click 'Get started'",
                10
        );
    }

    public void clickSkip(){
        this.waitForElementByAndClick(SKIP, "Cannot find and click skip button", 5);
    }
}