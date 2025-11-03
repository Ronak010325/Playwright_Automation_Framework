package com.qa.playwright.tests.AmazonTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.pages.AmazonTestCases.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void homePageAddToCart() {
        amazonPage.addToCart();
        Assert.assertTrue(amazonPage.verifyItemAdded());
    }
}
