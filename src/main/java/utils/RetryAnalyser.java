package main.java.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyser implements IRetryAnalyzer {
    int count = 0;
    int Retrycount = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        while(count<Retrycount){
            count++;
            return true;
        }
        return false;
    }
}
