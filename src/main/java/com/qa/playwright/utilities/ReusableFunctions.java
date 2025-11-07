package com.qa.playwright.utilities;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReusableFunctions {
    public void selectOption(Page page, String dropDown, String value) {
        page.locator(dropDown).selectOption(new SelectOption().setLabel(value));
    }

    public String getPrice(String input) {
        return input.replaceAll("[^\\d.]", "");
    }
}
