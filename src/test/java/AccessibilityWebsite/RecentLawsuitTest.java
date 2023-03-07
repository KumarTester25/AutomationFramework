package test.java.AccessibilityWebsite;

import com.aventstack.extentreports.Status;
import main.java.pageEvents.AccessibilityPage;
import main.java.utils.CustomException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import test.java.BaseProgramTest.baseTest;

import java.util.LinkedList;
import java.util.List;

public class RecentLawsuitTest extends baseTest{
        AccessibilityPage AP = new AccessibilityPage();

        @Test
        public void getRecentLawsuitDetails(){
            try {
                if(AP.getAllRecentLawSuits()){
                    baseTest.logger.log(Status.PASS, "Successfully retrieved the all recent lawsuit details.");
                }else{
                    throw new CustomException("Unable to retrieve the recent lawsuit details");
                }
            }catch (CustomException e){
                baseTest.logger.log(Status.FAIL, e.getMessage());
            }
        }

}
