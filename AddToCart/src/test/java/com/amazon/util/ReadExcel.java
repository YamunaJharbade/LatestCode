package com.amazon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static void main(String[] args) throws IOException {

		Object[][] myTestData = readTestData(System.getProperty("user.dir") +"\\src\\com\\testdata\\TestData.xlsx");
		//System.out.println(myTestData);
	}

	public static Object[][] readTestData(String fileName) throws IOException {

		String sheetName = "Sheet1";
		File file = new File(fileName);
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Workbook = new XSSFWorkbook(inputStream);

		// Read sheet inside the workbook by its name
		Sheet mySheet = Workbook.getSheet(sheetName);
		int rowCount = mySheet.getLastRowNum();
		int colCount = mySheet.getRow(0).getPhysicalNumberOfCells();
		// colCount = colCount;
		System.out.println("No of rows: " + rowCount + " " + "No of cols: " + colCount);
		Object[][] object = new Object[rowCount][colCount];
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			System.out.println("Row :" + i);
			Row row = mySheet.getRow(i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0; j < colCount; j++) {
				// Print excel data in console
				row.getCell(j).setCellType(CellType.STRING);
				// row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
				object[i][j] = row.getCell(j).toString();
				System.out.println(object[i][j]);
			}

		}
		Workbook.close();
		System.out.println("Done");
		return object;
	}

	public static void WriteResult(String fileName, int rownum, int col, String result) throws IOException {

		String sheetName = "Sheet1";
		File file = new File(fileName);
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Workbook = new XSSFWorkbook(inputStream);

		// Read sheet inside the workbook by its name
		Sheet mySheet = Workbook.getSheet(sheetName);
		mySheet.getRow(rownum).getCell(col).setCellValue(result);

		FileOutputStream outputStream = new FileOutputStream(new File("Result.xlsx"));
		Workbook.write(outputStream);
		outputStream.close();
		System.out.println("Done");
	}

}