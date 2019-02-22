package com.pageobjectmodelassignment.pages;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.pageobjectmodelassignment.base.BasePage;


public class AddComputer extends BasePage{

	WebDriver driver;
	public AddComputer(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	//WebElements
	By headerText = By.xpath("//*[@id=\"main\"]/h1");
	By newComputerName = By.id("name");
	By introducedDate = By.id("introduced");
	By discontinuedDate = By.id("discontinued");
	By headerLink = By.xpath("//*[@id=\"main\"]/h1");
	By companyName =By.name("company");
	By createComputerBtn =By.xpath("//*[@id=\"main\"]/form/div/input");
	By infoLink = By.xpath("//*[@id=\"main\"]/div[2]/em");	


	//operations

	public void enterComputerName(String compName) {
		try {		
			driver.findElement(newComputerName).sendKeys(compName);
			log.debug("Computer Name was entered to add a new computer");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +newComputerName);
			log.debug("Locator to enter name of the computer not found hence exiting test");
			takeScreenshot();
		}
	}

	public void enterIntroducedDate(String introducedOnDate) {
		try {		
			driver.findElement(introducedDate).sendKeys(introducedOnDate);
			log.debug("Introduced date value was entered");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +introducedDate);
			log.debug("Locator to enter introduced date not found hence exiting test");
			takeScreenshot();
		}
	}

	public void enterDiscontinuedDate(String discontinuedOnDate) {
		try {		
			driver.findElement(discontinuedDate).sendKeys(discontinuedOnDate);
			log.debug("Enter discontinued date");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +discontinuedDate);
			log.debug("Locator to enter discontinued date not found hence exiting test");
			takeScreenshot();
		}
	}

	public void selectCompany(String company) {
		try {
			Select selectcompany = new Select(driver.findElement(companyName));
			selectcompany.selectByVisibleText(company);
			log.debug("Company selected");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +companyName);
			log.debug("Locator to select company not found hence exiting test");
			takeScreenshot();
		}
	}

	public void clickCreateComputer() {
		try {		
			driver.findElement(createComputerBtn).click();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +createComputerBtn);
			log.debug("Create computer button not found hence exiting test");
			takeScreenshot();
		}
	}

	public void addComputer(String compName,String introducedOnDate, String discontinuedOnDate,String company) {
		
		enterComputerName(compName);
		enterIntroducedDate(introducedOnDate);
		enterDiscontinuedDate(discontinuedOnDate);
		selectCompany(company);
		clickCreateComputer();
		log.debug("Added a new computer with the name"+compName);
	}




}
