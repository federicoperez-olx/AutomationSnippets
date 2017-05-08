package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.Properties;


public class FileUtilities {


	public static void WriteFile(String filename, String data)
	{
		/*
		try{
		    PrintWriter writer = new PrintWriter(filename, "UTF-8");
		    writer.append( fileContents );
		    writer.close();
		} catch (IOException e) {
		   // do something
			e.printStackTrace();
		}
		*/
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) 
			{
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(data+"\n");

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try 
			{
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}
	
	public static String[] ReadAllLine(String filename) 
	{
		
		ArrayList<String> ret = new ArrayList<String>();

		//use . to get current directory
		File dir = new File(".");
		File fin = null;
		
		try 
		{
			fin = new File(dir.getCanonicalPath() + File.separator + filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Construct BufferedReader from FileReader
		BufferedReader br = null;
		
		try 
		{
			br = new BufferedReader(new FileReader(fin));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		String line = null;
		try 
		{
			while ((line = br.readLine()) != null) 
			{
				//System.out.println(line);
				ret.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		try 
		{
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return ret.toArray( new String[ret.size() ]);
		
	}

	public static Properties newPropFromFile ( String path)
	{
		Properties prop = new Properties();
		try 
		{
			prop.load(new FileInputStream(path));
		}catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return prop;
	}

	public static Boolean isDirectory(String path)
	{
		return new File(path).isDirectory();
	}
}
