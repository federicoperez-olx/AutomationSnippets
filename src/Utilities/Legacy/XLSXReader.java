package Utilities.Legacy;

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
	public static void Read( String path )
	{
		Read(path, 0);
	}

	@SuppressWarnings("deprecation")	
	public static void Read( String path, int sheetIndex )
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
		XSSFSheet mySheet = myWorkBook.getSheetAt(sheetIndex);
		
		mySheet.iterator();
		
		// Get iterator to all the rows in current sheet 
		Iterator<Row> rowIterator = mySheet.iterator(); 
		
		// Traversing over each row of XLSX file 
		while (rowIterator.hasNext()) 
		{ 
			Row row = rowIterator.next();
			
			
			// For each row, iterate through each columns 
			Iterator<Cell> colIterator = row.cellIterator();
			
			
				while (colIterator.hasNext()) 
				{ 
					Cell cell = colIterator.next(); 
					OnCell(cell);
					
					switch (cell.getCellType()) 
					{ 
						case Cell.CELL_TYPE_BLANK: 
						 
						break;
						
						case Cell.CELL_TYPE_STRING:
	
								int rowi = cell.getRowIndex();		
								int coli = cell.getColumnIndex();
								System.out.print(rowi+" "+coli); 
							
								System.out.print( "\t" + cell.getStringCellValue() + "\n");
								OnStringCell( cell.getStringCellValue() );
							break;
							
						case Cell.CELL_TYPE_NUMERIC: 
								System.out.println(cell.getNumericCellValue() + "\t");
								OnNumberCell( cell.getNumericCellValue() );
							break; 
						
						
						 
					default : 
					} 
				} 
			System.out.println(""); 
		
		}

		//Read more: http://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html#ixzz4lEITS5Os
	}

	protected static void OnCell(Cell cell){};
	protected static void OnStringCell(String cellContents){};
	protected static void OnNumberCell(Double cellContents){};
}
