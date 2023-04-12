package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "xpath:*//XCUIElementTypeStaticText[@name='Java (programming language)']";
        TITLE_TPL = "xpath:*//XCUIElementTypeStaticText[@name='{SUBSTRING}']";
        TITLE_SECOND_ARTICLE = "xpath:*//XCUIElementTypeStaticText[@name='Go (programming language)']";
        FOOTER_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='View article in browser']";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        DELETE_FROM_SAVED_LIST_BUTTON = "xpath://XCUIElementTypeButton[@name='Saved. Activate to unsave.']";
        ADD_TO_MY_LIST_NOTIFICATION = "xpath://XCUIElementTypeStaticText[@name='Add “Java (programming language)” to a reading list?']";
        ADD_TO_MY_LIST_NOTIFICATION_TPL = "xpath://XCUIElementTypeStaticText[@name='Add “{SUBSTRING}” to a reading list?']";
        ADDED_ARTICLE_NOTIFICATION_IN_HEADER = "xpath://XCUIElementTypeStaticText[@name='Add 1 article to reading list']";
        ADD_TO_MY_LIST_ICON = "xpath://XCUIElementTypeImage[@width='26']";
        ADD_TO_MY_LIST_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Create a new list']";
        MY_LIST_NAME_INPUT = "xpath:(//XCUIElementTypeTextField)[1]";
        HOME_BUTTON = "xpath://XCUIElementTypeButton[@name='W']";
        CREATE_LIST_BUTTON = "xpath://XCUIElementTypeButton[@name='Create reading list']";
    }
    public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}