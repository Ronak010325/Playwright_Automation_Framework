package com.qa.playwright.base;

import java.util.Properties;

import com.qa.playwright.pages.AmazonTestCases.CartPage;
import com.qa.playwright.pages.AmazonTestCases.HomePage;
import com.qa.playwright.pages.PracticeHomePage;
import org.testng.annotations.*;

import com.microsoft.playwright.Page;
import com.qa.playwright.factory.PlaywrightFactory;
import com.qa.playwright.pages.LoginPage;

public class BaseTest {

    PlaywrightFactory pf;
    Page page;
    protected Properties prop;

//    Pages
    protected PracticeHomePage homePage;
    protected LoginPage loginPage;
    protected HomePage amazonPage;
    protected CartPage amazonCartPage;

    @Parameters({ "browser" })
    @BeforeClass
    public void setup(String browserName) {
        pf = new PlaywrightFactory();

        prop = pf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        page = pf.initBrowser(prop);
        homePage = new PracticeHomePage(page);
        amazonPage = new HomePage(page);
    }

    @AfterClass
    public void tearDown() {
        page.context().browser().close();
    }

}