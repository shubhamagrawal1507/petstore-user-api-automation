package api.utilities;

import org.testng.ITestContext;
import org.testng.internal.TestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String reportName = "Test-Report.html";

    public void onStart(ITestContext testContext){
        
        sparkReporter = new ExtentSparkReporter(".\\reports\\"+reportName);

        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
        sparkReporter.config().setReportName("Users API");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS",System.getProperty("os.name"));
        extent.setSystemInfo("User",System.getProperty("user.name"));
        
    }

    public void onTestSuccess(TestResult result){
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS,"Test Passed");
    }

    public void onTestFailure(TestResult result){
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.FAIL,"Test Failed");
        test.log(Status.FAIL,result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext){
        extent.flush();
    }
}
