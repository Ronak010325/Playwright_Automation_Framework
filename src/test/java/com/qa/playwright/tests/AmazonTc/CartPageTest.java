package com.qa.playwright.tests.AmazonTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.pages.AmazonTestCases.CartPage;
import com.qa.playwright.pages.AmazonTestCases.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {
    @Test
    public void verifyCartDisplayItem() {
        amazonPage.addToCart();
        amazonCartPage = amazonPage.navigateToCart();
        Assert.assertTrue(amazonCartPage.verifyItemPresent());
    }
}
