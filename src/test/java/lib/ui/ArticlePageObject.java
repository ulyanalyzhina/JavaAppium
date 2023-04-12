package lib.ui;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            TITLE_TPL,
            TITLE_SECOND_ARTICLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            DELETE_FROM_SAVED_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_NAME_INPUT_ERROR,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            ARTICLE_TITLE_IN_LIST_TPL,
            ADD_TO_MY_LIST_NOTIFICATION,
            ADD_TO_MY_LIST_NOTIFICATION_TPL,
            ADD_TO_MY_LIST_ICON,
            ADDED_ARTICLE_NOTIFICATION_IN_HEADER,
            ADD_TO_MY_LIST_BUTTON,
            CREATE_LIST_BUTTON,
            HOME_BUTTON,
            TITLE_TPL_ON_PAGE,
            GO_LOGO,
    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON;

    private static String getTitleFromTPL(String substring) {
        return TITLE_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getTitleFromTPLOnPage(String substring) {
        return TITLE_TPL_ON_PAGE.replace("{SUBSTRING}", substring);
    }

    private static String getNotificationAboutAddingArticle(String title) {
        return ADD_TO_MY_LIST_NOTIFICATION_TPL.replace("{SUBSTRING}", title);
    }

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresentBy(TITLE,
                "Cannot find article title on page", 15);
    }

    public WebElement waitForTitleElement(String title) {
        return this.waitForElementPresentBy(getTitleFromTPL(title),
                "Cannot find article title on page", 15);
    }

    public WebElement waitForImgElement() {
        return this.waitForElementPresentBy(GO_LOGO,
                "Cannot find article image on page", 15);
    }


    public void clickOnArticle(String article_title) {
        this.waitForElementByAndClick(
                getTitleFromTPL(article_title),
                "Cannot find element",
                5
        );
    }


    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return titleElement.getAttribute("name");
        } else {
            return titleElement.getText();
        }
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    50
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article(ios)",
                    50
            );
        } else {
            this.scrollWebPageTitleElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    10
            );
        }

    }

    public void assertArticlePageTitlePresent(String title) {
        this.assertElementPresent(getTitleFromTPLOnPage(title), "Something goes wrong with article title");
    }

    public void addArticleToMySaved(String name_of_folder) {
        this.waitForElementByAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
        this.waitForElementPresentBy(
                ADD_TO_MY_LIST_NOTIFICATION,
                "Cannot find notification about adding article to reading list",
                15);
        this.tapPointOnTheScreen(26, 786);//15,720
        this.waitForElementPresentBy(ADDED_ARTICLE_NOTIFICATION_IN_HEADER,
                "There is no notification about adding article on the creating list page",
                15);

        this.waitForElementByAndClick(
                ADD_TO_MY_LIST_BUTTON,
                "Cannot find button to create list",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );
        this.waitForElementByAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementByAndClick(
                CREATE_LIST_BUTTON,
                "Cannot find or click 'Create reading list' button",
                5
        );
    }

    public void tapPointOnTheScreen(int x, int y) {
        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
        touchAction.tap(PointOption.point(x, y)).perform();
    }

    public void addArticleToMyListAndCreateFolder(String name_of_folder) {
        this.waitForElementByAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementByAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "cannot find option to add article to reading list",
                5
        );


        this.waitForElementByAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementPresentBy(
                MY_LIST_NAME_INPUT_ERROR,
                "Input was not been cleared and we cannot see validation error 'Please enter a title'",
                5
        );

        this.waitForElementByAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementByAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void assertElementHasTitle(String title) {
        this.assertElementHasText(
                getTitleFromTPLOnPage(title),
                title,
                "Cannot find title " + title + " as article title");
    }


    public void addArticleToMyList(String name_of_folder, String title) {
        if (Platform.getInstance().isAndroid()) {
            this.waitForElementByAndClick(
                    OPTIONS_BUTTON,
                    "Cannot find button to open article options",
                    5
            );

            this.waitForElementByAndClick(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "cannot find option to add article to reading list",
                    15
            );

            this.waitForElementByAndClick(
                    "xpath://android.widget.TextView[@text='" + name_of_folder + "']",
                    "Cannot find created folder",
                    5
            );
        } else {
            this.waitForElementByAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find option to add article to reading list",
                    5);
            this.waitForElementPresentBy(
                    getNotificationAboutAddingArticle(title),
                    "Cannot find notification about adding article to reading list",
                    15);
            this.tapPointOnTheScreen(26, 786);
        }
    }

    public void closeArticle() {
        if (Platform.getInstance().isAndroid()) {
            this.waitForElementByAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );
        } else if((Platform.getInstance().isIOS())){
            this.waitForElementByAndClick(
                    HOME_BUTTON,
                    "Cannot find 'W' button to close article",
                    5
            );
        }  else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void deleteArticleFromReadingList() {
        this.waitForElementByAndClick(DELETE_FROM_SAVED_LIST_BUTTON, "Cannot find button to delete article from saved", 5);
        this.waitForElementPresentBy(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
    }

    public void removeArticleFromSavedIfItAdded()
    {
        System.out.println(this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON));
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementByAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    5
            );


            this.waitForElementPresentBy(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before",
                    5
            );
        }

    }

    public void addArticlesToMySaved()
    {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }

        this.waitForElementByAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
    }
}