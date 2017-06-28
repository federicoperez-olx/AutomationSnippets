package Utilities.Legacy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVReader 
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
	
	
    public static HashMap<String, String> Read(String path) 
    {
    	return Read(path, ",");
    }
    
    public static HashMap<String, String> Read(String path, String splitBy) 
    {
    	//System.out.println("Attempting to read: " + path);

    	HashMap<String, String> ret = new HashMap<>();
        String line = "";
        
        int lineNo = 0;
        
        try ( BufferedReader br = new BufferedReader( new FileReader(path) ) ) 
        {
        	
            while ( (line = br.readLine() ) != null )
            {
            	lineNo++;
            	System.out.println(lineNo+" "+line);

            	line.replace("\n", "");
            	line.replaceAll("", "");
            	
                String[] splitLines = line.split(splitBy);
                
                //FILTER
                if ( filterRow > -1 && filterKey != null)
                {
                	if ( ! splitLines[filterRow].equals( filterKey ) )	continue;
                }
                
                //what rows go in the map?
                //second row, fourth row
                if ( splitLines.length < 4 ) continue;
                
                String date = splitLines[1];//.trim();
                String todayNote = splitLines[3];//.trim();
                
                if ( date != null && date != "" )
                {
                	if ( todayNote != null && todayNote != "")
                	{
		            	System.out.println("* Added: "+ date + " / "+todayNote );	  
                		ret.put(date, todayNote);
                	}
                }
            }
            
            return ret;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    


}