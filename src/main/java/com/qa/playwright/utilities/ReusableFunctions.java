package com.qa.playwright.utilities;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReusableFunctions {
    public void selectOption(Page page, String dropDown, String value) {
        page.locator(dropDown).selectOption(new SelectOption().setLabel(value));
    }

    public String getPrice(String input) {
        return input.replaceAll("[^\\d.]", "");
    }

    public void hoverOver(Page page, String locator) {
        Locator element = page.locator(locator);
        element.hover();
    }

    public String OTPExtract(String text) {
        String otp = "";
        // Regex to match 6 digits
        String regex = "\\b\\d{6}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // Find and print the 6-digit OTP
        if (matcher.find()) {
            otp = matcher.group();
            System.out.println("Extracted OTP: " + otp);
        } else {
            System.out.println("No OTP found in the text.");
        }
        return otp;
    }

    public void keyPress(Page page, String key) {
        page.keyboard().press(key);
    }

    public void sendKeys(Page page, String locator, String value) {
        try {
            page.click(locator);
            page.fill(locator, value);
        } catch (Exception e) {
            System.out.println("Error While Sending Keys");
        }
    }

    public String getCurrentTabTitle(Page page) {
        return page.title();
    }

    public void closeCurrentTab(Page page) {
        // Close the current tab
        page.close();
        // Get all open tabs (pages) from the same context
        List<Page> pages = page.context().pages();
        // Switch to the last tab if it exists
        if (!pages.isEmpty()) {
            Page lastPage = pages.get(pages.size() - 1);
            lastPage.bringToFront();   // Brings the tab to focus
        }
    }

    public Page openAndSwitchToNewTab(Page currentPage, String url) {
        // Create a listener to capture the new tab (Page)
        BrowserContext context = currentPage.context();
        // Wait for the new page (tab) to open
        Page newPage = context.waitForPage(() -> {
            // Open a new tab via JavaScript or Ctrl+T-like behavior
            currentPage.evaluate("window.open('" + url + "', '_blank')");
        });
        // Bring the new tab to the front (focus)
        newPage.bringToFront();
        // Wait for navigation to complete
        newPage.waitForLoadState(LoadState.LOAD);
        // (Optional) Verify the URL
        if (!newPage.url().equals(url)) {
            newPage.navigate(url);
            newPage.waitForURL(url);
        }
        return newPage;
    }

    public boolean verifyElementsLocated (Page page, String locator) {
        boolean flag = true;
        Locator elements = page.locator(locator);
        int count = elements.count();
        for (int i = 0 ; i < count ; i++) {
            if(elements.nth(i).isVisible()){
                System.out.println("Element is Visible : "+elements.nth(i).textContent());
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public void enterText (Page page, String locator,String Value) {
        try{
            page.fill(locator, Value);
        } catch (Exception e) {
            System.out.println("Error While Entering Text");
        }
    }
}
