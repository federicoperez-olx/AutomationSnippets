package Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVReader 
{
	//gently 'promoted' from https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/

    public static HashMap<String, String> Read(String path) 
    {
    	return Read(path, ",");
    }
    
    public static HashMap<String, String> Read(String path, String splitBy) 
    {
    	System.out.println(path);

    	HashMap<String, String> ret = new HashMap<>();
        String line = "";
        
        int lineNo = 0;
        
        try ( BufferedReader br = new BufferedReader( new FileReader(path) ) ) 
        {
        	
            while ( (line = br.readLine() ) != null )
            {
            	lineNo++;
            	//System.out.println(lineNo+" "+line);
            	
        		

                String[] splitLines = line.split(splitBy);
                
                //what rows go in the map?
                //second row, fourth row
                if ( splitLines.length < 4 ) continue;
                
                String date = splitLines[1].trim();
                String todayNote = splitLines[3].trim();
                
                if ( date != null && date != "" )
                {
                	if ( todayNote != null && todayNote != "")
                	{
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
    

    public static HashMap<String, String> Read(String path, String splitBy, int filterRow, String filter, int dataRow) 
    {
    	System.out.println(path);

    	HashMap<String, String> ret = new HashMap<>();
        String line = "";
        
        int lineNo = 0;
        
        try ( BufferedReader br = new BufferedReader( new FileReader(path) ) ) 
        {
        	
            while ( (line = br.readLine() ) != null )
            {
            	lineNo++;
            	System.out.println(lineNo);

                String[] splitLines = line.split(splitBy);
     
                if ( splitLines[filterRow].equals(filter) )
                {
                	if ( splitLines[dataRow] != null )
                	{
                		ret.put( splitLines[filterRow], splitLines[dataRow] );
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