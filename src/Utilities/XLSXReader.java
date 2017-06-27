package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XLSXReader 
{
	@SuppressWarnings("deprecation")
	public static void Read( String path )
	{
	
		FileInputStream fis = null;
		try 
		{
			fis = new FileInputStream(new File(path));
			
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		// Finds the workbook instance for XLSX file 
		XSSFWorkbook myWorkBook = null;
		try 
		{
			myWorkBook = new XSSFWorkbook (fis);
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
		// Return first sheet from the XLSX workbook k
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		
		mySheet.iterator();
		
		// Get iterator to all the rows in current sheet 
		Iterator<Row> rowIterator = mySheet.iterator(); 
		
		// Traversing over each row of XLSX file 
		while (rowIterator.hasNext()) 
		{ 
			Row row = rowIterator.next(); 
			// For each row, iterate through each columns 
			Iterator<Cell> cellIterator = row.cellIterator(); 
				while (cellIterator.hasNext()) 
				{ 
					Cell cell = cellIterator.next(); 
					
					switch (cell.getCellType()) 
					{ 
						case Cell.CELL_TYPE_STRING: 
								System.out.print(cell.getStringCellValue() + "\t"); 
							break;
							
						case Cell.CELL_TYPE_NUMERIC: 
								System.out.print(cell.getNumericCellValue() + "\t"); 
							break; 
								
						case Cell.CELL_TYPE_BOOLEAN: 
							System.out.print(cell.getBooleanCellValue() + "\t"); 
						break; 
					default : 
					} 
				} 
			System.out.println(""); 
		
		}

		//Read more: http://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html#ixzz4lEITS5Os
	}
}
