package com.pageobjectmodelassignment.pages;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.pageobjectmodelassignment.base.BasePage;
import com.pageobjectmodelassignment.util.Constants;
import com.relevantcodes.extentreports.LogStatus;


public class LaunchPage extends BasePage  {
	WebDriver driver;

	public LaunchPage(WebDriver driver) {
		super(driver);
		this.driver =driver;

	}


	//webElements
	By addCompBtn = By.xpath("//*[@id=\"add\"]");

	By searchBox = By.id("searchbox");

	By headerText = By.xpath("//*[@id=\"main\"]/h1");

	By searchSubmitBtn = By.id("searchsubmit");

	By retrievedCompName =By.xpath("//*[@id=\"main\"]/table/tbody/tr[1]/td[1]");

	By retrievedIntroducedValue =By.xpath("//*[@id=\"main\"]/table/tbody/tr/td[2]");

	By retrievedDiscontinuedValue =By.xpath("//*[@id=\"main\"]/table/tbody/tr/td[3]");

	By retrievedCompanyValue = By.xpath("//*[@id=\"main\"]/table/tbody/tr/td[4]");

	By headerResult =By.xpath("//*[@id=\"main\"]/h1");

	By successMessage =By.xpath("//*[@id=\"main\"]/div[1]");

	By nothingToDisplay =By.xpath("//*[@id=\"main\"]/div[2]");


	public void initComputerAdd() {
		try {
			System.out.println("will click on add comp");
			driver.findElement(addCompBtn).click();
			log.debug("Adding Computer");

		}catch(NoSuchElementException e){
			e.printStackTrace();
			Assert.fail("Locator not correct - " +addCompBtn);
			log.debug("Add computer button was not found hence test was exited");
			takeScreenshot();
		}
	}

	public void enterCompName(String compName) {
		try {
			driver.findElement(searchBox).sendKeys(compName);
			log.debug("Computer Name was entered");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +searchBox);
			log.debug("Computer Name text box was not found hence test was exited ");
			takeScreenshot();
		}
	}

	public void clickFilter() {
		try {
			driver.findElement(searchSubmitBtn).click();
			
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Locator not correct - " +searchSubmitBtn);
			log.debug("Search submit button was not found hence test was exited");
			takeScreenshot();
		}
	}

	public void initComputerSearch(String CompName) {

		enterCompName(CompName);
		clickFilter();
		log.debug("Searching for existing computer");
	}

	public String getRetrievedCompName(String expectedCompName) {
		displayNumberOfComputers();


		for(int i =0;;i++) {

			if (driver.findElement(retrievedCompName).getText().equals(expectedCompName)) {
				break;
			}
		}
		
		String retrievedcompname = driver.findElement(retrievedCompName).getText();
		System.out.println("The retreived comp name is: "+retrievedcompname);
		return retrievedcompname;
	}


	public String getRetrievedCompanyName() {
		String retrievedcompanyname = driver.findElement(retrievedCompanyValue).getText();
		log.debug("Company name of existing computer retrieved");
		return retrievedcompanyname;
		
	}

	public String getRetrievedDiscontinuedValue() {
		String retrieveddiscontinuevalue = driver.findElement(retrievedDiscontinuedValue).getText();
		log.debug("Discontinued date value was retrieved");
		return retrieveddiscontinuevalue;
		
	}

	public String getRetrievedIntroducedValue() {
		String retrievedintroducedevalue = driver.findElement(retrievedIntroducedValue).getText();
		log.debug("Introduced date value was retrieved");
		return retrievedintroducedevalue;
	}

	public void initiateEditComp() {
		try {
			driver.findElement(retrievedCompName).click();
			log.debug("Navigating to edit computer page");
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			log.debug("Retrieved computer name object was not found hence test was exited");
			Assert.fail("Locator not correct - " +retrievedCompName);
		}
	}

	public boolean verifyIntroDate(String expectedIntroDate) {
		String actualIntroDate=driver.findElement(retrievedIntroducedValue).getText().trim();
		boolean match = actualIntroDate.equals(expectedIntroDate);
		if(match=true) {
			test.log(LogStatus.INFO, "Retrieved Introduction date is correct,Expected was :"+expectedIntroDate+ "Actual is:"+actualIntroDate);
			return match;
		}

		else {
			test.log(LogStatus.INFO, "Retrieved Introduction date is incorrect,Expected was :"+expectedIntroDate+ "Actual is:"+actualIntroDate);
			return match;
		}
	}

	public boolean verifyDiscontinueDate(String expectedDiscontinueDate) {
		String actualDiscontinueDate=driver.findElement(retrievedDiscontinuedValue).getText().trim();
		boolean match =actualDiscontinueDate.equals(expectedDiscontinueDate);
		if(match) {
			test.log(LogStatus.INFO, "Retrieved discontinuation date is correct,Expected was :"+expectedDiscontinueDate+ "Actual is:"+actualDiscontinueDate);
			return match;
		}

		else {
			test.log(LogStatus.INFO, "Retrieved Computer discontinuation is incorrect,Expected was :"+expectedDiscontinueDate+ "Actual is:"+actualDiscontinueDate);
			return false;
		}
	}

	public boolean verifyCompanyName(String expectedCompanyName) {
		String actualCompanyName=driver.findElement(retrievedCompName).getText().trim();
		boolean match = actualCompanyName.equals(expectedCompanyName);
		if(match=true) {
			test.log(LogStatus.INFO, "Retrieved company name is correct,Expected was :"+expectedCompanyName+ "Actual is:"+actualCompanyName);
			return match;
		}

		else {
			test.log(LogStatus.INFO, "Retrieved Computer discontinuation is incorrect,Expected was :"+expectedCompanyName+ "Actual is:"+actualCompanyName);
			return match;
		}
	}

	public int findNum() {
		System.out.println("trying to find num");
		String headertext = driver.findElement(headerText).getText();
		int numberOfComp = splitStringReturnNum(headertext);
		return numberOfComp;

	}

	public boolean checkSuccessMessage(String CompName) {
		boolean compareMessage = false;
		try {
			String actualMessage = driver.findElement(successMessage).getText();
			String expectedMessage  = Constants.EXPECTED_SUCCESS_MESSAGE_PART1+CompName+Constants.EXPECTED_SUCCESS_MESSAGE_PART2;
			compareMessage = actualMessage.equals(expectedMessage);
			return compareMessage;
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			log.debug("Success Message element was not found hence the test exited");
			return compareMessage;
		}
	}


	public void searchComputer(String CompName) {

		driver.findElement(searchBox).sendKeys(CompName);
		System.out.println("Entering text in the field");
		driver.findElement(searchBox).sendKeys(Keys.RETURN);

	}

	public void displayNumberOfComputers() {
		String numberofResults= driver.findElement(headerText).getText();
		System.out.println(numberofResults);

	}

	public void clickOnRetrievedComputer(String compname) {
		WebElement element;
		System.out.println("Attempting to click");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/table/tbody/tr[1]/td[1]/a")));
		element.click();

	}	

	public boolean validateDeleteMessage() {

		String actualMessage = driver.findElement(successMessage).getText();
		String expectedMessage  = Constants.EXPECTED_DELETE_MESSAGE;
		boolean messageIsPresent = actualMessage.equals(expectedMessage);
		if(messageIsPresent == true)
			return true;
		else
			return false;

	}

	public boolean validateNoItemsMessage() {

		String actualSuccessMessage =driver.findElement(nothingToDisplay).getText();
		String expectedSuccessMessage = Constants.EXPECTED_NO_COMPUTER_MESSAGE;
		boolean validationSuccess = actualSuccessMessage.equals(expectedSuccessMessage);
		if(validationSuccess == true)
			return true;
		else
			return false;
	}

	public boolean validateHeaderText() {
		String actualHeaderMessage = driver.findElement(headerText).getText();
		String expectedHeasderMessage = Constants.EXPECTED_HEADER_MESSAGE;
		boolean validateHeaderMessage = actualHeaderMessage.equals(expectedHeasderMessage);
		if(validateHeaderMessage == true)
			return true;
		else
			return false;
	}

	public boolean validateEditSuccess(String CompName) {
		String editSuccessActual = driver.findElement(successMessage).getText();
		String editSuccessExpected = Constants.EXPECTED_EDIT_SUCCESS_PART1+CompName+Constants.EXPECTED_EDIT_SUCCESS_PART2;
		boolean validateEditMessage = editSuccessActual.equals(editSuccessExpected);
		if(validateEditMessage == true)
			return true;
		else
			return false;
	}
}



