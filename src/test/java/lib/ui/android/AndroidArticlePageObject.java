package lib.ui.android;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "id:org.wikipedia:id/view_page_title_text";
                FOOTER_ELEMENT = "xpath://*['View page in browser']";
                //FOOTER_ELEMENT == "xpath://XCUIElementTypeStaticText[@name='View article in browser']";
                OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
                OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath:/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView";
                ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
                MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
                MY_LIST_NAME_INPUT_ERROR = "id:org.wikipedia:id/textinput_error";
                MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
                CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton";
                ARTICLE_TITLE_IN_LIST_TPL = "xpath://*[@text='{SUBSTRING}']";
                TITLE_TPL = "xpath://*[@text='{SUBSTRING}']";
    }
    public AndroidArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
