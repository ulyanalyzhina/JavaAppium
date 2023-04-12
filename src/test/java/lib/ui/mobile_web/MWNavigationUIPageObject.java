package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUIPageObject extends NavigationUI {
    static {
        MY_LISTS_LINK = "css:a.menu__item--watchlist";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";
    }
    public MWNavigationUIPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}