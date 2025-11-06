package com.qa.playwright.tests.SwagLabTc;

import com.qa.playwright.base.BaseTest;
import com.qa.playwright.utilities.DataProviders;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {
    @Test(dataProvider = "swagLabData", dataProviderClass = DataProviders.class)
    public void testLogin(String username, String password, String status) {
        swagloginPage.enterUsername(username);
        swagloginPage.enterPassword(password);
        swagloginPage.clickLogin();
//        This will check that the login is successful and also the credentials were valid
        boolean loginStatus = swagloginPage.verifyLogin();
        boolean validLogin = swagloginPage.verifyStatus(status);

        if(loginStatus) {
            swagLabHomePage = swagloginPage.navigateToHomePage();
            swagLabHomePage.logOut();
        }

        Assert.assertEquals(loginStatus, validLogin);
    }
}
