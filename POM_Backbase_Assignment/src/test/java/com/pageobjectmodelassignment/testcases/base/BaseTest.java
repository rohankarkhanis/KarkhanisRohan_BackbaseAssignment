package com.pageobjectmodelassignment.testcases.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import com.pageobjectmodelassignment.util.Constants;
import com.pageobjectmodelassignment.util.ExcelUtil;
import com.pageobjectmodelassignment.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	public ExtentReports extreports =ExtentManager.getInstance();
	public ExtentTest test;
	public ExcelUtil excelUtilObj = new ExcelUtil();
	public Properties prop;
	WebDriver driver;
	String appURL;
	String browser ;

	public WebDriver initialize() {

		if(browser.contentEquals("Mozilla"))
			driver = new FirefoxDriver(); 
		// path of Gecko driver .exe is already updated in the path environment system variable

		else if (browser.contentEquals("Chrome")) {
			System.setProperty("WebDriver.Chrome.driver", Constants.CHROME_DRIVER_EXE);
			driver = new ChromeDriver();

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.navigate().to(appURL);
		takeScreenshot();
		return driver;

	}

	public void init(){
		//initiate the prop file
		if(prop==null){
			prop=new Properties();
			try {

				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
				prop.load(fs);
				appURL=prop.getProperty("appUrl");
				browser = prop.getProperty("browser");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean verifyText(WebElement element,String expectedText){
		String actualText=element.getText().trim();
		if(actualText.equals(expectedText))
			return true;
		else 
			return false;
	}

	public void compareText(String actualText,String expectedText){
		if(actualText.equals(expectedText)) {
			System.out.println("comparing text");
			reportPass("Text of computer Name is validated as:"+actualText);
		}
		else 
		{
			reportFailure("Text of computer Name is incorrect as:"+actualText);
		}	

	}

	public void takeScreenshot() {

		Date d = new Date();
		String screenShot = d.toString().replace(":","_").replace(" ", "_")+".png";
		//store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try { 
			FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir")+"//screenshots//"+screenShot));
			test.log(LogStatus.INFO,test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenShot));

		}catch(Exception e) {
			e.printStackTrace();

		}

	}

	public void reportPass(String msg){
		test.log(LogStatus.PASS, msg);
		takeScreenshot();
	}

	public void reportFailure(String msg){
		test.log(LogStatus.FAIL, msg);
		takeScreenshot();
		Assert.fail(msg);

	}

}
