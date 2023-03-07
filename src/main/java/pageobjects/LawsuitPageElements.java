package main.java.pageobjects;

public interface LawsuitPageElements {
    //Lawsuits Xpath
    String LawsuitName = "//span[@id='hs_cos_wrapper_name']";
    String PlaintiffTitle = "//div[@class='plaintiff_block']//h2";
    String PlaintiffName = "//div[@class='plaintiff_block']//li[1]";
    String PlaintiffFilingDate = "//div[@class='plaintiff_block']//li[2]";
    String PlaintiffStateofFiling= "//div[@class='plaintiff_block']//li[3]";

    String DefandentTitle = "//div[@class='defendant_block']//h2";
    String DefandentName  = "//div[@class='defendant_block']//li[1]";
    String Defandentwebsite = "//div[@class='defendant_block']//li[2]";
    String Defandentindustry = "//div[@class='defendant_block']//li[3]";
    String Defandentsummary = "//div[@class='defendant_block']//li[4]";
}
