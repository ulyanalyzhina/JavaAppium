package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject
{

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:.header-action button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";

        SEARCH_RESULT_LIST = "id:org.wikipedia:id/search_results_list";
        SEARCH_RESULT_LIST_ITEM = "xpath://div[@class='results-list-container view-border-box']//ul[@class='page-list thumbs actionable']//li";
        SEARCH_RESULT_LIST_ITEM_TITLE = "xpath:(//div[@class='results-list-container view-border-box']//ul[@class='page-list thumbs actionable']//li)[{replace}]";
        SEARCH_RESULT_LIST_ITEM_DESCRIPTION = "xpath:(//div[@class='results-list-container view-border-box']//ul[@class='page-list thumbs actionable']//li)[{replace}]//div[@class='wikidata-description']";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION =
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                        "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']/" +
                        "../*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{DESCRIPTION}']";
    }

    public MWSearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}