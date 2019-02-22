package com.pageobjectmodelassignment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	
	
		
	public void initExcelFile(String Path) throws IOException {
		File file = new File(Path);
		//File file = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\"+TCName+".xlsx");
		FileInputStream fis = new FileInputStream(file);
	    XSSFWorkbook wb = new XSSFWorkbook(fis);
	    XSSFSheet sheet = wb.getSheetAt(0);
	    fis.close();
	}
	
	public Object[][] readDataInMap(String Path) throws IOException{

		File file = new File(Path);
		FileInputStream fis = new FileInputStream(file);
	    XSSFWorkbook wb = new XSSFWorkbook(fis);
	    XSSFSheet sheet = wb.getSheetAt(0);
	    fis.close();
	    int lastRowNum = sheet.getLastRowNum() ;
	    int lastCellNum = sheet.getRow(0).getLastCellNum();
	    Object[][] obj = new Object[lastRowNum][1];


	    for (int i = 0; i < lastRowNum; i++) {
	        Map<String, String> datamap = new HashMap<>();
	        for (int j = 0; j < lastCellNum; j++) {
	          datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i+1).getCell(j).toString());
	        }
	        obj[i][0] = datamap;

	      }
	      return  obj;
	    }

}