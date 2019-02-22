package com.pageobjectmodelassignment.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.pageobjectmodelassignment.pages.common.TopMenu;
import com.pageobjectmodelassignment.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class BasePage {
	public WebDriver driver;
	public TopMenu menu;
	public ExtentTest test;

	public Properties prop;
	public ExtentReports rep = ExtentManager.getInstance();
	public Logger log = Logger.getLogger("devpinoyLogger");


	public void init(){
		//init the prop file
		if(prop==null){
			prop=new Properties();
			try {

				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
				prop.load(fs);
				String env=prop.getProperty("env");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public BasePage(WebDriver driver) {
		this.driver = driver;
		log.debug("Starting loggin");
	}

	public void openBrowser(String bType){
		test.log(LogStatus.INFO, "Opening browser "+bType );

		if(bType.equals("Mozilla"))
			driver=new FirefoxDriver();

		else if(bType.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_exe"));
			driver=new ChromeDriver();
		}
		log.debug("Opening browser");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Browser opened successfully "+ bType);
		System.out.println("browser open");
		takeScreenshot();
	}

	public void navigate(String appUrl){
		test.log(LogStatus.INFO, "Navigating to "+prop.getProperty(appUrl) );
		System.out.println(prop.getProperty(appUrl));
		driver.get(prop.getProperty(appUrl));
	}


	public void takeScreenshot() {
		//webDriver Code
		Date d = new Date();
		String screenShot = d.toString().replace(":","_").replace(" ", "_")+".png";
		//store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try { 
			FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir")+"//screenshots//"+screenShot));
		}catch(Exception e) {
			e.printStackTrace();

		}
	}

	public int splitStringReturnNum(String text) {
		String arr[] = text.split(" ", 2);
		String num = arr[0];
		int number = Integer.parseInt(num);
		return number;
	}
	public String formatDateString(String actualString) {
		String inputString = actualString;
		String arr[] = inputString.split(" ", 3);
		String month = arr[1];
		String year = arr[2];
		String date =  arr[0];
		String formatedMonth;

		switch (month) {
		case "Jan":
			formatedMonth= "01";
			break;
		case "Feb":
			formatedMonth= "02";
			break;	
		case "Mar":
			formatedMonth= "03";
			break;
		case "Apr":
			formatedMonth= "04";
			break;
		case "May":
			formatedMonth= "05";
			break;
		case "Jun":
			formatedMonth= "06";
			break;
		case "Jul":
			formatedMonth= "07";
			break;
		case "Aug":
			formatedMonth= "08";
			break;
		case "Sep":
			formatedMonth= "09";
			break;
		case "Oct":
			formatedMonth= "10";
			break;
		case "Nov":
			formatedMonth= "11";
			break;
		case "Dec":
			formatedMonth= "12";	
			break;
		default: formatedMonth ="invalid month";
		break;
		}
		String finalString = arr[2]+"-"+formatedMonth+"-"+arr[0];
		System.out.println(finalString);
		return finalString;
	}






}
