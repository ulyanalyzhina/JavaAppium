package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for lists")
public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";
    private static final String login = "Uliana7777", password = "Uliana7778";

    @Test
    @Features(value= {@Feature("Search"), @Feature(value = "Article"), @Feature(value = "Auth"), @Feature(value = "Save to list")})
    @DisplayName("Save article to my list")
    @Description("We find article, save it to list, verify the list and than delete it from the list")
    @Step("Starting test testSaveFirstArticleToMyList")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        String searchText = "Java";
        String articleTitle = "Java (programming language)";
        String articleDescription = "bject-oriented programming language";

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        findArticleAndClickToAddToFolder(searchText, articleTitle, articleDescription);
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyListAndCreateFolder(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticleToMySaved(name_of_folder);
        } else {
            System.out.println("It was scenario for mobile web");
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyList();


        MyListsPageObject MyListsPageObject = MyListsFactory.get(driver);
        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isIOS())) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isMW())) {
            MyListsPageObject.swipeByArticleToDelete(articleTitle);
        } else {
            MyListsPageObject.goToArticle(articleTitle);
            ArticlePageObject.deleteArticleFromReadingList();
            ArticlePageObject.closeArticle();

            MyListsPageObject.verifyThatArticleDisappearFromFolder(name_of_folder, articleTitle);
        }
    }

    @Test
    @Features(value= {@Feature("Search"), @Feature(value = "Article"), @Feature(value = "Auth"), @Feature(value = "Save to list")})
    @DisplayName("Save article to my list")
    @Description("We find  2 articles, save it to list, verify the list and than delete it from the list")
    @Step("Starting test testSaveTwoArticles")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveTwoArticles() throws InterruptedException {
        String search_text_first_article = "Java";
        String search_text_second_article = "Golang";
        String first_article_title = "Java (programming language)";
        String second_article_title = "Go (programming language)";
        String description_first_article = "bject-oriented programming language";
        String description_second_article = "rogramming language";

        findArticleAndClickToAddToFolder(search_text_first_article, first_article_title, description_first_article);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyListAndCreateFolder(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticleToMySaved(name_of_folder);
        } else {
            System.out.println("It was scenario for mobile web");
        }

        ArticlePageObject.closeArticle();

        findArticleAndClickToAddToFolder(search_text_second_article, second_article_title, description_second_article);
        if (Platform.getInstance().isAndroid() || (Platform.getInstance().isIOS())) {
            ArticlePageObject.addArticleToMyList(name_of_folder, second_article_title);
        }

        MyListsPageObject MyListsPageObject = MyListsFactory.get(driver);
        if (Platform.getInstance().isIOS()) {
            MyListsPageObject.clickMyFolder(name_of_folder);
        }
        ArticlePageObject.closeArticle();
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyList();
        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isIOS())) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        if (Platform.getInstance().isAndroid() || (Platform.getInstance().isMW())) {
            MyListsPageObject.swipeByArticleToDelete(first_article_title);
        } else {
            MyListsPageObject.goToArticle(first_article_title);
            ArticlePageObject.deleteArticleFromReadingList();
            ArticlePageObject.closeArticle();
        }

        ArticlePageObject.clickOnArticle(second_article_title);
        //first variant -> with title
        ArticlePageObject.waitForTitleElement(second_article_title);
        ArticlePageObject.assertArticlePageTitlePresent(second_article_title);
        ArticlePageObject.assertElementHasTitle(second_article_title);
        //second variant -> without title
        ArticlePageObject.waitForImgElement();
    }

    private void findArticleAndClickToAddToFolder(String article_text, String article_title, String description) {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(article_text);
        if ((Platform.getInstance().isIOS()) || (Platform.getInstance().isAndroid())) {
            SearchPageObject.clickByArticleWithSubString(article_title);
        } else {
            SearchPageObject.clickByArticleWithSubString(description);
        }

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isIOS()) {
            ArticlePageObject.waitForTitleElement(article_title);
        } else {
            ArticlePageObject.waitForTitleElement();
        }
        if (Platform.getInstance().isMW()) {
            ArticlePageObject.addArticlesToMySaved();
        } else {
            ArticlePageObject.addArticleToMySaved(name_of_folder);
        }

        AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
        if (Platform.getInstance().isMW() && Auth.isAuthButtonPresent()) {
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            String url = driver.getCurrentUrl();
            String new_url = url.substring(0, 11) + "m." + url.substring(11);
            driver.get(new_url);

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );
        }
    }
}