package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assume.assumeFalse;

@Epic("Tests for search")
public class SearchTests extends CoreTestCase {
    @Test
    @Features(value= {@Feature("Search")})
    @DisplayName("Search article")
    @Description("We search article by 'Java' and wait for searching results to appear")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Features(value= {@Feature("Search")})
    @DisplayName("Cancel button appears")
    @Description("We init search for verify if cancel button appeared")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();

        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }
    @Test
    @Features(value= {@Feature("Search")})
    @DisplayName("Amount of not empty search article")
    @Description("We enter 'Linkin Park Discography' and verify that amount of search is > 0")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    @Features(value= {@Feature("Search")})
    @DisplayName("Amount of empty search article")
    @Description("We enter \"sdsdsdrwdfwewe\" and verify that amount of search == 0")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void  testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "sdsdsdrwdfwewe";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Features(value= {@Feature("Search")})
    @DisplayName("Test field for search contains text 'Search Wikipedia'")
    @Description("We verify that input contains text 'Search Wikipedia'")
    @Step("Starting test testFieldForSearchContainsText")
    @Severity(value = SeverityLevel.MINOR)
    public void testFieldForSearchContainsText() {
        assumeFalse(Platform.getInstance().isMW());
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.assertSearchInputHasText();
    }

    @Test
    @Features(value= {@Feature("Search")})
    @DisplayName("Cancel search of articles")
    @Description("We search article and verify that searchh result not present after clicking cancel button")
    @Step("Starting test testCancelSearchOfArticles")
    @Severity(value = SeverityLevel.NORMAL)
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
    @Features(value= {@Feature("Search")})
    @DisplayName("We assert that searched world exist in all results")
    @Description("We search by 'Java' and verify that all titles contains this world")
    @Step("Starting test testVerifyTheWorldInTheResultSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testVerifyTheWorldInTheResultSearch() {
        assumeFalse(Platform.getInstance().isMW());
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        List<WebElement> els = SearchPageObject.assertSearchedResultsAreMoreThanOne();
        SearchPageObject.assertAllTitlesHaveText(els, "Java");
    }

    @Test
    @Features(value= {@Feature("Search")})
    @DisplayName("We assert the right title and description of the first 3 results")
    @Description("We search by 'Java' and verify that all titles and descriptions are right")
    @Step("Starting test testVerifyTitleAndDescriptionInSearch")
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
        } else {
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 1, "Java", "Island in Indonesia");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 2, "JavaScript", "High-level programming language");
            SearchPageObject.assertSearchedItemHasTitleAndDescription(els, 3, "Java (programming language)", "Object-oriented programming language");
        }
    }
}
