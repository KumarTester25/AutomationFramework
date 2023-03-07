package main.java.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import test.java.BaseProgramTest.baseTest;
import java.io.File;
import org.apache.commons.io.FileUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SuiteListeners implements ITestListener, IAnnotationTransformer {

    @Override
    public void  onTestFailure(ITestResult iTestResult){
        String Filename = System.getProperty("user.dir")+ File.separator+"screenshots"+File.separator+"FailedTestcaseScreenshots"+File.separator+ iTestResult.getName();
        File f = ((TakesScreenshot) baseTest.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(f, new File(Filename+".png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method){
        iTestAnnotation.setRetryAnalyzer(RetryAnalyser.class);
    }
}
