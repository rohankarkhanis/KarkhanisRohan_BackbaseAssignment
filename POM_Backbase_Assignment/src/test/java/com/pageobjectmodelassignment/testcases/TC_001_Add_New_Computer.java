
/*=============================================================================================
Test Case Name: TC_001_Add_New_Computer

Test case Description: This test case creates a computer record by providing all the values, 
searches for the created computer record and validates its associated values like computer name,
introducedOnDate, discontinued date and Company name


==============================================================================================*/
package com.pageobjectmodelassignment.testcases;
import java.io.IOException;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.pageobjectmodelassignment.base.BasePage;
import com.pageobjectmodelassignment.pages.AddComputer;
import com.pageobjectmodelassignment.pages.LaunchPage;
import com.pageobjectmodelassignment.testcases.base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class TC_001_Add_New_Computer extends BaseTest {
	String testCaseName="TC_001_Add_New_Computer";
	SoftAssert softAssert;
	WebDriver driver;
	public String compName;
	public String introducedOnDate;
	public String discontinuedDate;
	public String company;

	@BeforeTest
	public void startup() {
		test = extreports.startTest(testCaseName, "Validate that user is able to successfully add a new computer by providing only relevant mandatory data");
		test.log(LogStatus.INFO, "Starting test");
		softAssert = new SoftAssert();
		init();
		driver = initialize();
	}



	@Test(dataProvider = "data",priority=1)
	public void addNewComputer(Map <String, String> map) {

		LaunchPage launchpage = new LaunchPage(driver);
		AddComputer addcomputer = new AddComputer(driver);
		BasePage basepage = new BasePage (driver);
		basepage.log.debug("Starting test");

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
			test.log(LogStatus.INFO, "Now the total Number of Computers in database is : "+newNumberOfComps);

			if (newNumberOfComps==(initialNumberOfComps+1)) 
				reportPass("Total Number of computers post addition is correct: "+newNumberOfComps);
			else 
				reportFailure("New computer could not be created successfully");


		}
	}
	
	//After addition of computer, validating the added information by searching the newly created computer
	
	@Test(dependsOnMethods = {"addNewComputer"})

	public void searchAddedComputer() {
		
		
		LaunchPage launchpage = new LaunchPage(driver);
		launchpage.initComputerSearch(compName);
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
					}
					else
						reportFailure("Retrieved Company name is not correct ");
				}
				else
					reportFailure("Retrieved doscontinuedOn date is not correct ");					 
			}
			else
				reportFailure("Retrieved cintroduced date is not correct ");  
		}
		else
			reportFailure("Retrieved computer name is not correct ");
	}

	@DataProvider(name = "data")
	public Object[][] dataSupplier() throws IOException {
		Object[][] obj = excelUtilObj.readDataInMap(System.getProperty("user.dir")+"\\src\\test\\resources\\"+testCaseName+".xlsx");
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