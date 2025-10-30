package com.qa.playwright.listeners;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import static com.qa.playwright.factory.PlaywrightFactory.takeScreenshot;

public class ExtentReportListener implements ITestListener {
    static Properties property;
    static String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss").format(new Date());

    private static ExtentReports extent;

    static {
        try {
            extent = init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    private static ExtentReports extentReports;


    private static ExtentReports init() throws IOException {
        FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
        property = new Properties();
        property.load(ip);

        String OUTPUT_FOLDER = "./Reports/"+property.get("projectName")+"/";

        Path path = Paths.get(OUTPUT_FOLDER);
        // if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // fail to create directory
                e.printStackTrace();
            }
        }

        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + property.get("projectName") + timestamp);
        reporter.config().setReportName("EasyDinner Automation Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "WINDOWS");
        extentReports.setSystemInfo("Author", "Planit Testing");
//		extentReports.setSystemInfo("Build", "1.1");
        extentReports.setSystemInfo("Team", "QA");
        extentReports.setSystemInfo("Customer Name", "EasyDinner");

        //extentReports.setSystemInfo("ENV NAME", System.getProperty("env"));

        return extentReports;
    }

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started!");

    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extent.flush();
        test.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println(methodName + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        /*
         * methodName = StringUtils.capitalize(StringUtils.join(StringUtils.
         * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
         */
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("Test passed");
        test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(), result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}