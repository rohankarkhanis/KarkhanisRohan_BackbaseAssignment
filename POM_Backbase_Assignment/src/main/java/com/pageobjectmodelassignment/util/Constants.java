package com.pageobjectmodelassignment.util;

public class Constants {
	//paths
	public static final String CHROME_DRIVER_EXE = "C:\\SeleniumWebdriver\\drivers";
	public static final String REPORT_PATH = System.getProperty("user.dir")+"\\ExtentReports\\";
	public static final String APPURL = "http://computer-database.herokuapp.com/computers";
	
	
	//Informationn Message
	public static final String NUMBER_OF_COMPUTERS = "//*[@id=\"main\"]/h1";
	public static final String EXPECTED_SUCCESS_MESSAGE_PART1 = "Done! Computer ";
	public static final String EXPECTED_SUCCESS_MESSAGE_PART2 = " has been created";
	public static final String EXPECTED_DELETE_MESSAGE = "Done! Computer has been deleted";
	public static final String EXPECTED_NO_COMPUTER_MESSAGE = "Nothing to display";
	public static final String EXPECTED_NO_COMPUTER_MESSAGE2 = "No computers found";
	public static final String EXPECTED_HEADER_MESSAGE ="No computers found";
	public static final String EXPECTED_EDIT_SUCCESS_PART1 = "Done! Computer ";
	public static final String EXPECTED_EDIT_SUCCESS_PART2 = " has been updated";
	
		
	//Browsers
	public static final String MOZILLA ="Mozilla";
	public static final String CHROME ="Chrome";
	
	
	
}
