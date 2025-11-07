package com.qa.playwright.pages.SwagLabPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.playwright.utilities.ReusableFunctions;

import java.util.ArrayList;
import java.util.List;

public class swagLabHomePage {
    private Page page;
    private ReusableFunctions _reuse;

    public swagLabHomePage(Page page, ReusableFunctions _reuse) {
        this.page = page;
        this._reuse = _reuse;
    }

    String menuBtn = "//button[@id='react-burger-menu-btn']";
    String logoutBtn = "//a[@id='logout_sidebar_link']";
    String filterDropDown = "//select[@class='product_sort_container']";
    String itemPrices = "//div[@class='pricebar']/div";

    public void selectOption() {
        _reuse.selectOption(page, filterDropDown, "Price (low to high)");
    }

    public boolean verifyFilter() {
        boolean flag = true;
        Locator prices = page.locator(itemPrices);
        List<String> list = prices.allTextContents();
        String priceOfFirst =_reuse.getPrice(list.get(0));
        for(int i = 1 ; i < list.size() ; i++) {
            String price = _reuse.getPrice(list.get(i));
            if(Double.parseDouble(priceOfFirst) <= Double.parseDouble(price)) {
                flag = true;
                priceOfFirst = price;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public void logOut() {
        page.click(menuBtn);
        page.click(logoutBtn);
    }
}
