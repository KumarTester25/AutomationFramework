package main.java.pageEvents;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import main.java.pageobjects.LawsuitPageElements;
import main.java.utils.Constants;
import main.java.utils.CustomException;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import test.java.BaseProgramTest.baseTest;
import com.aventstack.extentreports.markuputils.Markup;

import java.time.Duration;
import java.util.*;

public class AccessibilityPage {
    public int TotalLawsuitCount;
    public  List<LinkedList<String>> finalTestDataFromDB = null;
    public WebDriverWait wait;

    public boolean getAllRecentLawSuits()  {
        try {
            wait = new WebDriverWait(baseTest.driver, Duration.ofSeconds(80));
            Actions action = new Actions(baseTest.driver);
            finalTestDataFromDB = new ArrayList<>();
            List<WebElement> allURLS = baseTest.driver.findElements(By.xpath("//div[@class='mostrecent-list post-listing active']//div/h3//a"));
            TotalLawsuitCount = allURLS.size();
            LinkedList<String> lawsuit = new LinkedList<>();
            //Getting all recent lawsuits links URL and storing in the lawsuit list.
            for (WebElement Au : allURLS) {
                String hrefValue = Au.getAttribute("href");
                Assert.assertNotNull(hrefValue);
                Assert.assertFalse(hrefValue.isEmpty());
                Assert.assertTrue(hrefValue.startsWith("http") || hrefValue.startsWith("https"));
                lawsuit.add(hrefValue);
            }
            //Selecting the lawsuit links and fetching the values, storing in the list.
            for (int i = 0; i <= lawsuit.size() - 1; i++) {
                String hrefValue = lawsuit.get(i);
                WebElement link = baseTest.driver.findElement(By.xpath("(//a[@href='" + hrefValue + "'])[1]"));
                ((JavascriptExecutor) baseTest.driver).executeScript("arguments[0].scrollIntoView();", link);
                Thread.sleep(1000);
                link.click();
                finalTestDataFromDB.add(this.getvalues());
                baseTest.driver.navigate().back();
                baseTest.driver.navigate().refresh();
            }
            this.writeValuesinReport();
            return true;
        }catch (AssertionError e){
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Get the Values from the Lawsuit Page
    public LinkedList<String> getvalues() {
        WebElement LawsuitName = baseTest.driver.findElement(By.xpath(LawsuitPageElements.LawsuitName));
        WebElement plaintiff = baseTest.driver.findElement(By.xpath(LawsuitPageElements.PlaintiffTitle));
        WebElement plaintiffName = baseTest.driver.findElement(By.xpath(LawsuitPageElements.PlaintiffName));
        WebElement filingDate = baseTest.driver.findElement(By.xpath(LawsuitPageElements.PlaintiffFilingDate));
        WebElement stateOfFiling = baseTest.driver.findElement(By.xpath(LawsuitPageElements.PlaintiffStateofFiling));
        WebElement Defendant = baseTest.driver.findElement(By.xpath(LawsuitPageElements.DefandentTitle));
        WebElement defendantName = baseTest.driver.findElement(By.xpath(LawsuitPageElements.DefandentName));
        WebElement website = baseTest.driver.findElement(By.xpath(LawsuitPageElements.Defandentwebsite));
        WebElement industry = baseTest.driver.findElement(By.xpath(LawsuitPageElements.Defandentindustry));
        WebElement summary = baseTest.driver.findElement(By.xpath(LawsuitPageElements.Defandentsummary));
        String L_Name = LawsuitName.getText();
        String Ptitle_Name = plaintiff.getText();
        String P_Name = plaintiffName.getText();
        String P_filingDate = filingDate.getText();
        String P_stateOfFiling = stateOfFiling.getText();
        String Dtitle_Name = Defendant.getText();
        String D_Name = defendantName.getText();
        String D_website = website.getText();
        String D_industry = industry.getText();
        String D_summary = summary.getText();
        LinkedList<String> list = new LinkedList<>(Arrays.asList(L_Name, Ptitle_Name, P_Name, P_filingDate, P_stateOfFiling, Dtitle_Name, D_Name, D_website, D_industry, D_summary));
        return list;
    }

    //Iterating the Values and print in the extent report.
    public void writeValuesinReport(){
        for (LinkedList<String> strings : finalTestDataFromDB) {
            baseTest.logger.log(Status.PASS,"LAWSUIT NAME - "+strings.get(0)+" Please refer to the plaintiff and defendant details below:");
            StringBuilder sb = new StringBuilder();
            for(int i=1; i<=strings.size()-1; i++){
                sb.append(strings.get(i));
                sb.append("<br>");
            }
            baseTest.logger.log(Status.INFO, String.valueOf(sb));
        }
    }
}
