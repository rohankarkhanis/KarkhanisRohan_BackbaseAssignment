package com.pageobjectmodelassignment.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.pageobjectmodelassignment.base.BasePage;
import com.relevantcodes.extentreports.LogStatus;

public class TopMenu extends BasePage {
	
	public TopMenu(WebDriver driver) {
	super(driver);
		
	}
	
	
	//WebElements
	
	By topMenuLink = By.xpath("/html/body/header/h1/a/text()");
	
	
	public void clickTopMenu(){
		try {
			driver.findElement(topMenuLink).click();
			test.log(LogStatus.INFO, "Top Menu Link is clicked,returning to main page");
		}catch(Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Top menu link element is not available");
		}
	}
}
