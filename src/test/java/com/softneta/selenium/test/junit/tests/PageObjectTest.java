package com.softneta.selenium.test.junit.tests;

import com.softneta.selenium.test.junit.rules.ScreenShotOnFailRule;
import com.softneta.selenium.test.pages.YouTubePage;
import com.softneta.selenium.test.pages.YouTubeSearchResultsPage;
import com.softneta.selenium.test.webtestsbase.WebDriverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sidelnikov Mikhail on 19.09.14.
 * Uses JUnit test framework
 * Test demonstrates work with Page Object Pattern(https://code.google.com/p/selenium/wiki/PageObjects)
 */
public class PageObjectTest {

    @Rule
    public ScreenShotOnFailRule screenShotOnFailRule = new ScreenShotOnFailRule();

    @Before
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
    }

    @Test
    public void testSearch() {
        String toSearch = "Selenium";
        YouTubePage youTubePage = new YouTubePage();
        youTubePage.insertSearchString(toSearch);
        YouTubeSearchResultsPage resultsPage = youTubePage.doSearch();
        assertTrue("No results were found on results page", resultsPage.hasResults());
    }


    @After
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }
}
