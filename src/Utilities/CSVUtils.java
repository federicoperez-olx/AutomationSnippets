package Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CSVUtils 
{

	//gently 'promoted' from https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	private static int filterRow = -1;
	private static String filterKey = null;

	public static void SetFilter(String row, String keyword)
	{
		filterRow = Integer.parseInt(row);
		filterKey = keyword;
	}
	
	public static void SetFilter(int row, String keyword)
	{
		filterRow = row;
		filterKey = keyword;
	}
	
	
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';


    public static HashMap<String, String> Read(String path)
    {

    	HashMap<String, String> ret = new HashMap<>();
    	
        Scanner scanner;
		try 
		{		
			scanner = new Scanner( new File(path) );

			while (scanner.hasNext()) 
	        {
	            List<String> line = parseLine( scanner.nextLine() );
	            System.out.println("-> "+line);
            	
	            //
	            if ( line.size() < 4 ){ System.out.println("skipped"); continue; }

            	//FILTER
                if ( filterRow > -1 && filterKey != null)
                {
                	if ( ! line.get(filterRow).equals( filterKey ) ) continue;	
                }
                                
                String date = line.get(1).trim();
                String todayNote = line.get(3).trim();

                if ( date != null && date != "" )
                {
                	if ( todayNote != null && todayNote != "")
                	{
		            	System.out.println("* Added: "+ date + " / "+todayNote );	                		
                		ret.put(date, todayNote);
                	}
                }
	            
	        }
	        
			scanner.close();
		
		} catch (FileNotFoundException e) 
		{

			e.printStackTrace();
		}
		
		return ret;
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}