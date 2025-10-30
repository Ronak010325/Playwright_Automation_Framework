package com.qa.playwright.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.constants.AppConstants;

public class HomePageTest extends BaseTest {

    @Test
    public void homePageTitleTest() {
        String actualTitle = homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
    }

    @Test(enabled = false)
    public void homePageURLTest() {
        String actualURL = homePage.getHomePageURL();
        Assert.assertEquals(actualURL, prop.getProperty("url"));
    }

    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                { "Macbook" },
                { "iMac" },
                { "Samsung" }
        };
    }

    @Test(dataProvider = "getProductData", enabled = false)
    public void searchTest(String productName) throws InterruptedException {
        Thread.sleep(5000);
        String actualSearchHeader = homePage.doSearch(productName);
        Assert.assertEquals(actualSearchHeader, "Search - " + productName);
    }

}
