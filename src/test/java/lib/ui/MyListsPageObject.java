package lib.ui;


import org.openqa.selenium.remote.RemoteWebDriver;

import static java.lang.Thread.sleep;

public class MyListsPageObject extends MainPageObject {
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            READING_LIST_TAB,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String name_of_folder) {
            return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementByAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                15
        );
    }

    private static String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }


    public void swipeByArticleToDelete(String article_title) throws InterruptedException {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        if ((Platform.getInstance().isIOS()) || (Platform.getInstance().isAndroid())) {
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article"

            );
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementByAndClick(
                    remove_locator,
                    "Cannot click button to remove article from savedA",
                    5
            );
        }

        if(Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperConner(article_xpath, "Cannot find saved article");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }


   public void goToArticle(String article_title){
       String article_xpath = getSavedArticleXpathByTitle(article_title);
       this.waitForElementByAndClick(article_xpath, "Cannot find article title on the page",5);
   }

    public void verifyThatArticleDisappearFromFolder(String name_of_folder, String article_title){
        this.waitForElementByAndClick(READING_LIST_TAB, "There is no reading list tab on the page", 5);
        this.openFolderByName(name_of_folder);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForArticleToDisappearByTitle(article_xpath);
    }

    public void waitForArticleToDisappearByTitle(String article_title)  {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Article still present on the page " + article_xpath,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresentBy(
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }

    public void clickMyFolder(String name_of_folder) {
        this.waitForElementByAndClick(
                getFolderXpathByName(name_of_folder),
                "Cannot find folder",
                5
        );
    }
}
