package com.pageobjectmodelassignment.pages;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.pageobjectmodelassignment.base.BasePage;

public class EditComputer extends BasePage {

	public EditComputer(WebDriver driver) {
		super(driver);
		this.driver =driver;
	}

	//WebElements

	By compName_Edit = By.id("name");
	By introducedDate_Edit =By.id("introduced");
	By discontinuedDate_Edit = By.id("discontinued");
	By companyName_edit = By.name("company");
	By saveThisCompBtn = By.xpath("//*[@id=\"main\"]/form[1]/div/input");
	By deleteCompBtn = By.xpath("//*[@id=\"main\"]/form[2]/input")	;


	//

	//operations

	public void editComputerName(String compName) {
		try {		

			driver.findElement(compName_Edit).sendKeys(compName);
			log.debug("Entered text in the edit computer name field");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +compName_Edit);
			log.debug("Element to enter edited computer name was not found hence test was exited");
			takeScreenshot();
		}
	}

	public void editIntroducedDate(String introducedOnDate) {
		try {	
			System.out.println("trying to edit date");
			driver.findElement(introducedDate_Edit).sendKeys(introducedOnDate);
			log.debug("Introduced Date edited");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			log.debug("Element to enter edited Introduced date was not found hence test was exited");
			Assert.fail("Locator not correct - " +introducedDate_Edit);
			takeScreenshot();
		}
	}

	public void enterDiscontinuedDate(String discontinuedOnDate) {
		try {		

			driver.findElement(discontinuedDate_Edit).sendKeys(discontinuedOnDate);
			log.debug("Discontinued on date edited");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			log.debug("Element to enter edited computer name was not found hence test was exited");
			Assert.fail("Locator not correct - " +discontinuedDate_Edit);
			takeScreenshot();
		}
	}

	public void selectCompany(String company) {
		try {
			Select selectcompany = new Select(driver.findElement(companyName_edit));
			selectcompany.selectByVisibleText(company);
			log.debug("Company value edited");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +companyName_edit);
			log.debug("Element to edit company name was not found hence test was exited");
		}
	}

	public void saveComp() {
		try {		
			driver.findElement(saveThisCompBtn).click();
			log.debug("Edited computer is saved");
			takeScreenshot();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			log.debug("Element to save edited computer was not found hence test was exited");
			Assert.fail("Locator not correct - " +saveThisCompBtn);
			takeScreenshot();
		}
	}

	public void deleteComp() {

		try {		
			driver.findElement(deleteCompBtn).click();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			log.debug("Element to delete computer was not found hence test was exited");
			Assert.fail("Locator not correct - " +deleteCompBtn);
			takeScreenshot();
		}

	}

	public String getTextofComputerName() {
		try {
			System.out.println("retrieving compname:");
			String actualCompName = driver.findElement(compName_Edit).getAttribute("value");
			return actualCompName;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			return "";
		} 
	}

	public String getTextOfIntroducedDate() {
		try {
			String actualIntroducedDate = driver.findElement(introducedDate_Edit).getAttribute("value");
			return actualIntroducedDate;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			return "";
		} 
	}

	public String getTextOfDiscontinuedDate() {
		try {
			String actualDiscontinuedDate = driver.findElement(discontinuedDate_Edit).getAttribute("value");
			return actualDiscontinuedDate;
		}catch(ElementNotVisibleException e) {
			e.printStackTrace();
			return "";
		} 
	}

	public String getCompanyText() {
		try {
			Select select = new Select(driver.findElement(companyName_edit));
			WebElement option = select.getFirstSelectedOption();
			String defaultItem = option.getText();
			return defaultItem;
		}catch(ElementNotVisibleException e) {
			e.printStackTrace();
			return "";
		} 
	}

	public void deleteComputer() {
		try {

			driver.findElement(deleteCompBtn).click();

		}catch(ElementNotVisibleException e) {

			e.printStackTrace();
			System.out.println("Delete button could not be found hence exiting test");
		}

	}


}
