package dataLibrary;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import extentReports.Reports;

public class ReadExcel extends Reports {
	
	public static Object[][] readExcelDP(String location) throws IOException, InvalidFormatException
	{
		XSSFWorkbook wb = new XSSFWorkbook(new File(location));
		XSSFSheet sheet = wb.getSheetAt(0);
		int RowNum = sheet.getLastRowNum();
		int CellNum = sheet.getRow(0).getLastCellNum();
		Object[][] obj = new Object[RowNum][CellNum];
		for(int i=1;i<=RowNum;i++)
		{
			XSSFRow row = sheet.getRow(i);
			for(int j=0;j<CellNum;j++)
			{
				XSSFCell cell = row.getCell(j);
				String stringCellValue = null;
				if(cell.getCellType()==CellType.STRING) 
				{
					stringCellValue = cell.getStringCellValue(); 
				}
				else if(cell.getCellType()==CellType.NUMERIC) 
				{
					stringCellValue = (int)cell.getNumericCellValue()+"";
				}
				obj[i-1][j] = stringCellValue;
			}
		}
		wb.close();
		return obj;
	}

}
