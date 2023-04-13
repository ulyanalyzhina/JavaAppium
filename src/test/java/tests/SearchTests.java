package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assume.assumeFalse;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    public void testCancelSearch() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();

        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void  testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "sdsdsdrwdfwewe";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }


    @Test
    public void testFieldForSearchContainsText() {
        assumeFalse(Platform.getInstance().isMW());
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.assertSearchInputHasText();
    }

    @Test
    public void testCancelSearchOfArticles() {
        assumeFalse(Platform.getInstance().isMW());
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("winter");
        SearchPageObject.assertSearchedResultsAreMoreThanOne();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertSearchResultNotPresent();
    }

    @Test
    public void testVerifyTheWorldInTheResultSearch() {
        assumeFalse(Platform.getInstance().isMW());
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        List<WebElement> els = SearchPageObject.assertSearchedResultsAreMoreThanOne();
        SearchPageObject.assertAllTitlesHaveText(els, "Java");
    }

    @Test
    public void testVerifyTitleAndDescriptionInSearch(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        List<WebElement> els = SearchPageObject
                .getAllSearchItems();
        if(Platform.getInstance().isAndroid()){
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 0, "Java", "Island of Indonesia, Southeast Asia");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 1, "JavaScript", "High-level programming language");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 2, "Java (programming language)", "Object-oriented programming language");
        } else if ((Platform.getInstance().isIOS())){
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 1, "Java", "Island in Indonesia");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 2, "JavaScript", "High-level programming language");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 3, "Java (programming language)", "Object-oriented programming language");
        } else {
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 0, "Java", "Island in Indonesia");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 1, "JavaScript", "High-level programming language");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 2, "Java (programming language)", "Object-oriented programming language");
        }
    }
}
