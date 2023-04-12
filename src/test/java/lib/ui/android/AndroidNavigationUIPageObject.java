package lib.ui.android;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUIPageObject extends NavigationUI {
    static {
        MY_LISTS_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
    }
    public AndroidNavigationUIPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}