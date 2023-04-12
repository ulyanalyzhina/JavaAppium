package lib.ui.ios;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "id:Saved";
        CLOSE_POP_UP_BUTTON = "xpath://XCUIElementTypeButton[@name='Close']";
    }
    public IOSNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
