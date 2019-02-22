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

public class TC_004_Edit_Computer extends BaseTest {
	String testCaseName="TC_004_Edit_Computer";
	SoftAssert softAssert;
	WebDriver driver;
	String compName;
	String introducedOnDate;
	String discontinuedDate;
	String company;
	String introducedOnDate_Edited;
	String discontinuedDate_Edited;
	String company_Edited;



	@BeforeTest
	public void startup() {


		test = extreports.startTest(testCaseName, "Validate that user is able to successfully edit a computer");
		test.log(LogStatus.INFO, "Starting test");
		softAssert = new SoftAssert();
		init();
		driver = initialize();
	}



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
		introducedOnDate_Edited =map.get("IntroducedDate_Edited");
		discontinuedDate_Edited =map.get("DiscontinuedDate_Edited");
		company_Edited =map.get("Company_Edited");  
		addcomputer.addComputer(compName, introducedOnDate, discontinuedDate, company);

	}

	@Test(dataProvider = "data",dependsOnMethods= {"addNewComputer"})
	public void editComputer(Map <String, String> map) {

		LaunchPage launchpage = new LaunchPage(driver);
		EditComputer editcomputer = new EditComputer(driver);

		launchpage.initComputerSearch(compName);
		launchpage.clickOnRetrievedComputer(compName);
		editcomputer.editComputerName("_Edited");
		editcomputer.editIntroducedDate(introducedOnDate_Edited);
		editcomputer.enterDiscontinuedDate(discontinuedDate_Edited);
		editcomputer.selectCompany(company_Edited);
		editcomputer.saveComp();

		boolean editSuccess = launchpage.validateEditSuccess(compName+"_Edited");
		if (editSuccess==true)
			reportPass("Computer "+compName+" was edited successfully to: "+compName+"_Edited");
		else
			reportFailure("Computer"+compName+" coould not be edited");

	}


	@Test(dataProvider = "data",dependsOnMethods= {"addNewComputer","editComputer"})
	public void searchEditedComputer(Map <String, String> map){

		LaunchPage launchpage = new LaunchPage(driver);
		EditComputer editcomputer = new EditComputer(driver);
		launchpage.initComputerSearch(compName);

		launchpage.clickOnRetrievedComputer(compName);
		compareText(editcomputer.getTextofComputerName(),compName+"_Edited");
		compareText(editcomputer.getTextOfIntroducedDate(),introducedOnDate_Edited );
		compareText(editcomputer.getTextOfDiscontinuedDate(),discontinuedDate_Edited);
		compareText(editcomputer.getCompanyText(), company_Edited);
		System.out.println("All fields are validated after editing");

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