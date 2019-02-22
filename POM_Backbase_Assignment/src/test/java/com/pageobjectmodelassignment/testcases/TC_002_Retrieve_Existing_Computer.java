package com.pageobjectmodelassignment.testcases;
/*=============================================================================================
Test Case Name: TC_002_Retrieve_Existing_Computer

Test case Description: This test case creates a computer record by providing all the values, 
searches for the created computer record and opens in Edit mode to validates its associated values like computer name,
introducedOnDate, discontinued date and Company name
==============================================================================================*/
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

public class TC_002_Retrieve_Existing_Computer extends BaseTest {
	String testCaseName="TC_002_Retrieve_Existing_Computer";
	SoftAssert softAssert;
	WebDriver driver;
	String compName;
	String introducedOnDate;
	String discontinuedDate;
	String company;


	@BeforeTest
	public void startup() {
		test = extreports.startTest(testCaseName, "Validate that user is able to retrieve computer from the database");
		test.log(LogStatus.INFO, "Starting test");
		softAssert = new SoftAssert();
		init();
		driver = initialize();
	}



	@Test(dataProvider = "data")
	public void addNewComputer(Map <String, String> map) {	
		LaunchPage launchpage = new LaunchPage(driver);
		AddComputer addcomputer = new AddComputer(driver);
		int initialNumberOfComps = launchpage.findNum();
		test.log(LogStatus.INFO, "Total Number of Computers in database is : "+initialNumberOfComps);
		System.out.println(initialNumberOfComps);

		launchpage.initComputerAdd();	  
		compName = map.get("ComputerName");
		introducedOnDate = map.get("IntroducedDate");
		discontinuedDate = map.get("DiscontinuedDate");
		company = map.get("Company");
		addcomputer.addComputer(compName, introducedOnDate, discontinuedDate, company);  
		boolean compareResult = launchpage.checkSuccessMessage(compName);
		if (compareResult == true) {
			reportPass("New computer "+compName+" was successfully added in the database");  
			int newNumberOfComps = launchpage.findNum();
			test.log(LogStatus.INFO, "Now the total Number of Computers in database is :"+newNumberOfComps);
			if (newNumberOfComps==(initialNumberOfComps+1)) {
				reportPass("Total Number of computers post addition is correct: "+newNumberOfComps);
			}
			else
				reportFailure("Total Number of computers post addition is not correct: "+initialNumberOfComps);
		}
		else		  			  
			reportFailure("New computer could not be added");		
	}		  

	@Test(dependsOnMethods={"addNewComputer"})
	public void validateRetrieveComputer(){

		LaunchPage launchpage = new LaunchPage(driver);
		EditComputer editcomputer = new EditComputer(driver);	
		launchpage.searchComputer(compName);
		test.log(LogStatus.INFO, "Checking for newly added computer Name");
		String actualCompName = launchpage.getRetrievedCompName(compName);
		if (compName.equals(actualCompName)) {
			reportPass("Retrieved computer name is correct "+compName); 	  
			String actualIntroDate = launchpage.getRetrievedIntroducedValue();
			String formatedActualDate = launchpage.formatDateString(actualIntroDate);		  
			if(introducedOnDate.equals(formatedActualDate)) {
				reportPass("Retrieved introduced date is correct "+introducedOnDate);		 
				String actualDiscontinueDate = launchpage.getRetrievedDiscontinuedValue();
				String formatedActualDisDate = launchpage.formatDateString(actualDiscontinueDate);
				if(discontinuedDate.equals(formatedActualDisDate)){
					reportPass("Retrieved discontinedOn date is correct "+introducedOnDate);
					String expectedCompanyName = launchpage.getRetrievedCompanyName();
					if(company.equals(expectedCompanyName)) {
						reportPass("Retrieved Company name is correct: "+company); 		   
						launchpage.clickOnRetrievedComputer(compName);		   
						//verify ComputerName text of Edit page
						compareText(editcomputer.getTextofComputerName(), compName);
						compareText(editcomputer.getTextOfIntroducedDate(),introducedOnDate);
						compareText(editcomputer.getTextOfDiscontinuedDate(),discontinuedDate);
						compareText(editcomputer.getCompanyText(), company);		   
					}
					else
						reportFailure("Retrieved Company name is not correct ");
				}
				else
					reportFailure("Retrieved doscontinuedOn date is not correct ");

			}
			else
				reportFailure("Retrieved introduced date is not correct ");

		}
		else
			reportFailure("Retrieved computer name is not correct ");
	}

	@DataProvider(name = "data")
	public Object[][] dataSupplier() throws IOException {
		Object[][] obj = excelUtilObj.readDataInMap(System.getProperty("user.dir")+"\\src\\test\\resources\\"+"TC_002_Retrieve_Exsisting_Computer"+".xlsx");
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