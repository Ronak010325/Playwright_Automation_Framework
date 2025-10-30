package com.qa.playwright.base;

import java.util.Properties;

import org.testng.annotations.*;

import com.microsoft.playwright.Page;
import com.qa.playwright.factory.PlaywrightFactory;
import com.qa.playwright.pages.HomePage;
import com.qa.playwright.pages.LoginPage;

public class BaseTest {

    PlaywrightFactory pf;
    Page page;
    protected Properties prop;

//    Pages
    protected HomePage homePage;
    protected LoginPage loginPage;

    @Parameters({ "browser" })
    @BeforeClass
    public void setup(String browserName) {
        pf = new PlaywrightFactory();

        prop = pf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        page = pf.initBrowser(prop);
        homePage = new HomePage(page);
    }

    @AfterClass
    public void tearDown() {
        page.context().browser().close();
    }

}