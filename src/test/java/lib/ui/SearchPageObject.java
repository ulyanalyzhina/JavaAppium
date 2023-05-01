package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject {
    protected static String
            SEARCH_INIT_ELEMENT;
    protected static String SEARCH_INPUT;
    protected static String SEARCH_CANCEL_BUTTON;
    public static String SEARCH_RESULT_BY_SUBSTRING_TPL;
    protected static String SEARCH_RESULT_ELEMENT;
    protected static String SEARCH_EMPTY_RESULT_ELEMENT;
    protected static String SEARCH_RESULT_LIST;
    protected static String SEARCH_RESULT_LIST_ITEM;
    protected static String SEARCH_RESULT_LIST_ITEM_TITLE;
    protected static String SEARCH_RESULT_LIST_ITEM_DESCRIPTION;
    protected static String SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION;
    /*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getSearchResultByTitleAndDescription(String title, String description){
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION
                .replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }


    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Initializing the search field")
    public void initSearchInput() {
        this.waitForElementPresentBy(
                SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element",
                15
        );
        this.waitForElementByAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element", 5
        );
    }
    @Step("Typing '{search_line}' to the search line")
    public void typeSearchLine(String search_line) {
        this.waitForElementByAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                5
        );
    }
    @Step("Waiting for search result")
    public void waitForSearchResult(String substring) {
        String search_result = getResultSearchElement(substring);

        this.waitForElementPresent(search_result, "Cannot find search result " + substring);
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresentBy(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button!",
                5
        );
    }
    @Step("Waiting for button to disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button is still present!",
                5
        );
    }

    public void clickCancelSearch() {
//        this.waitForElementToBeClickable( SEARCH_CANCEL_BUTTON,
//                "Element "  + SEARCH_CANCEL_BUTTON + " is not clickable",
//                15);
        this.waitForElementByAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                15
        );
    }
    @Step("Waiting for search result and select an article by substring in article")
    public void clickByArticleWithSubString(String substring) {
        String search_result = getResultSearchElement(substring);
        System.out.println(search_result);

        this.waitForElementByAndClick(
                search_result,
                "Cannot find and click search result",
                15
        );
    }
    @Step("Getting amount of found articles")
    public int getAmountOfFoundArticles() {
        this.waitForElementPresentBy(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request " + SEARCH_RESULT_ELEMENT,
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }
    @Step("Waiting for empty result label" )
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresentBy(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15);
    }
    @Step("Making sure there are no result for the search")
    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public void assertSearchInputHasText() {
        this.assertElementHasText(
                SEARCH_INIT_ELEMENT,
                "Search Wikipedia",
                "There is now field 'Search Wikipedia' ");
    }

    public List<WebElement> assertSearchedResultsAreMoreThanOne() {
        this.waitForElementPresentBy(
                SEARCH_RESULT_LIST,
                "Cannot find search results list",
                15
        );
        //2.2 Убеждаемся, что статей в контейнере больше чем 1
        return this.waitForElementsListPresentAndToBeMoreThan(
               SEARCH_RESULT_LIST_ITEM,
                "The number of elements is not the number we were waiting for",
                10,
                1
        );
    }

    public void assertSearchResultNotPresent() {
        this.waitForElementNotPresent(
                SEARCH_RESULT_LIST,
                "Cannot find search results list",
                5
        );
    }

    public void assertAllTitlesHaveText(List<WebElement> elements, String match) {
        this.assertAllElementsHaveText(elements, match, SEARCH_RESULT_LIST_ITEM_TITLE);
    }

    public void assertSearchedItemHasTitleAndDescription(
            List<WebElement> els,
            int index,
            String title,
            String description
    ){
       this.assertItemHasTitleAndDescription(els, index, title, description,
               SEARCH_RESULT_LIST_ITEM_TITLE,
               SEARCH_RESULT_LIST_ITEM_DESCRIPTION);
    }



    public void waitForElementByTitleAndDescription(String title, String description){
        this.waitForElementPresentBy(
                getSearchResultByTitleAndDescription(title,  description),
                "Cannot find article title",
                5);
    }

    public List<WebElement> getAllSearchItems(){
        return this.waitForElementsListPresentAndToBeMoreThan(
                SEARCH_RESULT_LIST_ITEM,
                "There is no items in the search",
                5,
                2);
    }
}
