package Utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;


import Utilities.Legacy.XLSXReader;


public class XLSXParser extends XLSXReader 
{
	private static int filterCol = -1;
	private static String filterKey = "";
	private static boolean filterActive = false;

	private static SimpleDateFormat dformatter = new SimpleDateFormat("MM/dd/yyyy");
	
	
	public static void SetFilter(String filterColIndex, String filterKeyword)
	{
		SetFilter(Integer.parseInt(filterColIndex), filterKeyword);
	}
	
	/***
	 * Allows to filter rows by a certain cell content. When parsing a Row it will verify for the presence of the 'filterKeyword' within the specified 'filterColIndex'
	 * Activates the filtering of data to return on Parse, if the ColIndex is set to values less than zero or the keyword is empty string the filter is deactivated
	 * @param filterColIndex the column index to search for the keyword
	 * @param filterKeyword the term to use or discard the row
	 */
	public static void SetFilter(int filterColIndex, String filterKeyword)
	{
		filterCol = filterColIndex;
		filterKey = filterKeyword;
		
		
		if ( filterCol > -1 && ! filterKey.equals("") )
		{
			filterActive = true;

			System.out.println("Set filter as "+ filterCol +" for term "+ filterKey);
		}else{
			filterActive = false;

			System.out.println("Set filter OFF");
		}
		
	}
	
	
	public static HashMap<String, String> Parse( String path )
	{
		return Parse(path, 0, 1, 3);
	}

	public static HashMap<String, String> Parse( String path, int sheetIndex, int colKey, int colData )
	{
		HashMap<String, String> result = new HashMap<String, String>();
		
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
		
		//mySheet.iterator();
		
		// Get iterator to all the rows in current sheet 
		Iterator<Row> rowIterator = mySheet.iterator(); 
		
		
		// Traversing over each row of XLSX file 
		while (rowIterator.hasNext()) 
		{ 
			Row row = rowIterator.next();
			
			if ( filterActive )
			{
				
				if ( row.getCell(filterCol).getStringCellValue().equals( filterKey ) )
				{
					String key 	= getCellAsString( row.getCell(colKey) );
					String value = getCellAsString( row.getCell(colData) );
					
					//System.out.println( key + " -  " + value );
					
					result.put(key, value);
				}
			}else{

				String key 	= getCellAsString( row.getCell(colKey) );
				String value = getCellAsString( row.getCell(colData) );
				
				//System.out.println( key + " & " + value );
				
				result.put(key, value);
			}
			/*
			// For each row, iterate through each columns 
			Iterator<Cell> colIterator = row.cellIterator();
		
			while (colIterator.hasNext()) 
			{ 
				Cell cell = colIterator.next(); 

				if ( filterActive )
				{
					OnFilterCell(cell);
				}else{
					if ( cell.getColumnIndex() == colKey )
					{
						result.put(cell.getStringCellValue(), "");
					}else if ( cell.getRowIndex() == colData )
					{
						result.put(,);
					}
				}
				
				
				switch (cell.getCellType()) 
				{ 
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
			}*/
			
		}
		
		try 
		{
			myWorkBook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return result;
	}
	
	private static String getCellAsString(Cell cell)
	{
		if ( cell.getCellTypeEnum() == CellType.STRING )
		{
			return cell.getStringCellValue();
		}else 
		if ( cell.getCellTypeEnum() == CellType.NUMERIC )
		{
			if ( HSSFDateUtil.isCellDateFormatted(cell) )
			{
				return dformatter.format(cell.getDateCellValue());
			}else
			{
				return Double.toString( cell.getNumericCellValue() );
			}
		}else 
		if ( cell.getCellTypeEnum() == CellType.BLANK )
		{
			return "";
		}
		
		return null;
	}
}
