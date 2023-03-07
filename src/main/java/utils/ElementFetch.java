package main.java.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.java.BaseProgramTest.baseTest;

import java.util.List;

public class ElementFetch {
    public WebElement getWebelement(String IdentifierType, String IdentifierValue){
        IdentifierType = IdentifierType.toUpperCase();
        switch(IdentifierType) {
            case "ID":
                return baseTest.driver.findElement(By.id(IdentifierValue));
            case "XPATH":
                return baseTest.driver.findElement(By.xpath(IdentifierValue));
            case "NAME":
                return  baseTest.driver.findElement(By.name(IdentifierValue));
            default:
                return null;
        }
    }

    public List<WebElement> getListWebelement(String IdentifierType, String IdentifierValue){
        IdentifierType = IdentifierType.toUpperCase();
        switch(IdentifierType) {
            case "ID":
                return baseTest.driver.findElements(By.id(IdentifierValue));
            case "XPATH":
                return baseTest.driver.findElements(By.xpath(IdentifierValue));
            default:
                return null;
        }
    }

}
