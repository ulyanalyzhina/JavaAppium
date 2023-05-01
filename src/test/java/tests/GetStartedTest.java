package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests, that pass through welcome page")
public class GetStartedTest extends CoreTestCase {
    @Test
    @Features(value= {@Feature("Welcome screen ios")})
    @DisplayName("Pass trough the welcome screen")
    @Description("We pass through the welcome screen that appeared only on ios")
    @Step("Starting test testPassThroughWelcome")
    @Severity(value = SeverityLevel.MINOR)
    public void testPassThroughWelcome(){
        if ((Platform.getInstance().isAndroid())|| (Platform.getInstance().isMW())) {
            return;
        }
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForNewWayToExploreText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForAddOrEditPreferredLangText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForLearnMoreAboutDataCollectedText();
        WelcomePage.clickGetStartedButton();
    }
}