package com.qa.playwright.base;

import java.util.Properties;

import com.qa.playwright.pages.AmazonPages.CartPage;
import com.qa.playwright.pages.AmazonPages.HomePage;
import com.qa.playwright.pages.PracticeHomePage;
import com.qa.playwright.pages.SwagLabPages.swagLabHomePage;
import com.qa.playwright.pages.SwagLabPages.swagLabLoginPage;
import org.testng.annotations.*;

import com.microsoft.playwright.Page;
import com.qa.playwright.factory.PlaywrightFactory;
import com.qa.playwright.pages.LoginPage;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    PlaywrightFactory pf;
    Page page;
    protected static Properties prop;
    protected SoftAssert softAssert;
//    Pages
    protected PracticeHomePage homePage;
    protected LoginPage loginPage;
    protected HomePage amazonPage;
    protected CartPage amazonCartPage;
    protected swagLabLoginPage swagloginPage;
    protected swagLabHomePage swagLabHomePage;

    @Parameters({ "browser" })
    @BeforeClass
    public void setup(String browserName) {
        pf = new PlaywrightFactory();

        prop = pf.init_prop();

        softAssert = new SoftAssert();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        page = pf.initBrowser(prop);
        homePage = new PracticeHomePage(page);
        amazonPage = new HomePage(page);
        swagloginPage = new swagLabLoginPage(page);
    }

    @AfterClass
    public void tearDown() {
        page.context().browser().close();
    }

}