package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://a[@title='Watch']|//a[@title='Add this page to your watchlist']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://a[@title='Unwatch']|//a[@title='Remove this page from your watchlist']";
        TITLE_TPL = "xpath://*[contains(text(),'{SUBSTRING}')]";
        TITLE_TPL_ON_PAGE = "xpath://span[contains(text(),'{SUBSTRING}')]";

    }
    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
