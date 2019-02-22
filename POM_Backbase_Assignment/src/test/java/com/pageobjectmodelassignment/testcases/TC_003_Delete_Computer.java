/*=============================================================================================
 *   TC Name: TC_003_Delete_Computer
 *   
 *   Description: This test case creates a computer record in the data base, deleted it and then 
 *   searches for the record to ensure successful deletion operation  
 * ============================================================================================*/  


package com.pageobjectmodelassignment.testcases;

import java.io.IOException;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.pageobjectmodelassignment.pages.AddComputer;
import com.pageobjectmodelassignment.pages.EditComputer;
import com.pageobjectmodelassignment.pages.LaunchPage;
import com.pageobjectmodelassignment.testcases.base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class TC_003_Delete_Computer extends BaseTest {

	SoftAssert softAssert;
	WebDriver driver;

	String testCaseName="TC_003_Delete_Computer";
	String compName ;
	String introducedOnDate;
	String discontinuedDate;
	String company;


	//Setup the URL,PropertyFile and browser before starting test

	@BeforeTest  
	public void startup() {
		test = extreports.startTest(testCaseName, "Validate that user is able to successfully delete a computer");
		test.log(LogStatus.INFO, "Starting test");
		softAssert = new SoftAssert();
		init();
		driver = initialize();
	}

    
	//Add a new computer, then delete that computer and then search for the deleted computer to ensure that computer is actually deleted.
	@Test(dataProvider = "data",priority=1)

	public void addNewComputer(Map <String, String> map) {
		LaunchPage launchpage = new LaunchPage(driver);
		AddComputer addcomputer = new AddComputer(driver);
		int initialNumberOfComps = launchpage.findNum();
		test.log(LogStatus.INFO, "Total Number of Computers in database is :"+initialNumberOfComps);
		System.out.println(initialNumberOfComps);	
		launchpage.initComputerAdd();
		compName = map.get("ComputerName");
		introducedOnDate = map.get("IntroducedDate");
		discontinuedDate = map.get("DiscontinuedDate");
		company = map.get("Company"); 
		addcomputer.addComputer(compName, introducedOnDate, discontinuedDate, company);

	}

	@Test(dataProvider = "data",dependsOnMethods= {"addNewComputer"})
	public void deleteComputer(Map <String, String> map) {
		LaunchPage launchpage = new LaunchPage(driver);
		EditComputer editcomputer = new EditComputer(driver);
		launchpage.initComputerSearch(compName);
		launchpage.clickOnRetrievedComputer(compName);
		compareText(editcomputer.getTextofComputerName(), compName);
		editcomputer.deleteComp();
		boolean success = launchpage.validateDeleteMessage();
		if (success == true)
			reportPass("Computer "+compName+" deleted successfully");
		else
			reportFailure("Computer "+compName+" was not deleted");

	}

	@Test(dataProvider = "data",dependsOnMethods= {"addNewComputer","deleteComputer"})
	public void searchDeletedComputer(Map <String, String> map){
		LaunchPage launchpage = new LaunchPage(driver);
		compName = map.get("ComputerName");
		launchpage.initComputerSearch(compName);
		boolean verifySuccess = launchpage.validateNoItemsMessage();
		boolean verifySuccessHeader = launchpage.validateHeaderText();
		if ( verifySuccess ==  true) {
			if (verifySuccessHeader == true) {
				reportPass("Computer "+compName+" deleted successfully and message displayed");
			}
			else
				reportFailure("Computer "+compName+" was not deleted");
		}
		else
			reportFailure("Computer "+compName+" was not deleted");

	}

	@DataProvider(name = "data")
	public Object[][] dataSupplier() throws IOException {
		Object[][] obj = excelUtilObj.readDataInMap(System.getProperty("user.dir")+"\\src\\test\\resources\\"+"TC_003_Delete_Computer"+".xlsx");
		return  obj;
	}

	@AfterTest
	public void quit(){

		try{
			softAssert.assertAll();
		}catch(Error e){
			test.log(LogStatus.FAIL, e.getMessage());
		}
		if(extreports!=null) {
			extreports.endTest(test);
			extreports.flush();
		}
		if(driver!=null)
			driver.quit();
	}

}