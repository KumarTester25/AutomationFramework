package test.java.BaseProgramTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import main.java.utils.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class baseTest {
    private static String urlSlackWebHook = "https://hooks.slack.com/services/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";
    private static String channelName = "automation";
    private static String userOAuthToken= "xoxp-3625763529204-3608766835207-3616776405558-e69f7c5d22027cf2f15c80732d82ebb4";

    public  static  WebDriver driver;
    public ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest logger;

    @BeforeTest
    public void beforetestmethod(){
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date());
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+File.separator+"reports"+File.separator+"AutomationReport"+File.separator+"Automation Report "+timeStamp+".html");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Automtion Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Automation tester", "kumar");
    }

    @BeforeMethod(alwaysRun = true)
    public void LaunchBrowser(Method testMethod){
        logger = extent.createTest(testMethod.getName());
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.get(Constants.Accessibility_WebsiteURL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void aftermethod(ITestResult result){
        if(result.getStatus() == ITestResult.SUCCESS){
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+ " - Test Case Passed", ExtentColor.GREEN));
        }
        else if(result.getStatus() == ITestResult.FAILURE) {
            String Methodname = result.getMethod().getMethodName();
            String logText = "Test Case " + Methodname + " Failed";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
            logger.log(Status.FAIL, m);
        }
    }

    @AfterTest
    public void afterTest() throws Exception {
        extent.flush();
        driver.close();
    }
    public void sendTestExecutionReportToSlack() throws Exception{
        String url = "https://slack.com/api/files.upload";
        try {
            HttpClient httpclient = HttpClientBuilder.create().disableContentCompression().build();
            HttpPost httppost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            FileBody fileBody = new FileBody(new File(System.getProperty("user.dir")+File.separator+"reports"+File.separator+"AutomationReport"+File.separator+"Report.html"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileBody);
            builder.addTextBody("channels", channelName);
            builder.addTextBody("token", userOAuthToken);
            httppost.setEntity(builder.build());
            HttpResponse response = null;
            response = httpclient.execute(httppost);
            HttpEntity result = response.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
