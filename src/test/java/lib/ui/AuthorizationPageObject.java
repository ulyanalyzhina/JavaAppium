package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {
    private static final String
            LOGIN_BUTTON = "xpath://a[text()='Log in']",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "css:#wpLoginAttempt";


    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton() {
        this.waitForElementPresentBy(
                LOGIN_BUTTON,
                "Cannot find auth button",
                5
        );

        this.waitForElementByAndClick(
                LOGIN_BUTTON,
                "cannot find and click auth button",
                5
        );
    }

    public Boolean isAuthButtonPresent() {
       return getAmountOfElements(LOGIN_BUTTON) > 0;
    }


    public void enterLoginData(String login, String password) {
        this.waitForElementByAndSendKeys(
                LOGIN_INPUT,
                login,
                "Cannot find and put a login to the login input",
                5
        );

        this.waitForElementByAndSendKeys(
                PASSWORD_INPUT,
                password,
                "Cannot find and put a login to the login input",
                5
        );
    }

    public void submitForm() {
        this.waitForElementByAndClick(
                SUBMIT_BUTTON,
                "Cannot find submit button",
                5
        );
    }
}