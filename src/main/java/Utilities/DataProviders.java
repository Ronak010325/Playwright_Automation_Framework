package Utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider
    public Object[][] getProductData() {
        return new Object[][] {
                { "Macbook" },
                { "iMac" },
                { "Samsung" }
        };
    }
}
