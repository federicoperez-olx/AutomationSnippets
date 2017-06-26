package Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import olxPageObjects.HomePagePO;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import Utilities.SeleniumFactory;


public abstract class BaseTest 
{
	protected WebDriver wd;
	protected HomePagePO homePO;
	
	@Before
	public void OnTestStart()
	{
		//System.out.println("Test Start");
		wd = SeleniumFactory.getChromeTesting();
		homePO = new HomePagePO(wd);
	}
	
	@After
	public void OnTestFinished()
	{
		//System.out.println("Test End");
		wd.close();
	}
	
	public String[] GetUsrPsw(int userId)
	{
		Properties props = new Properties();
		
		String filePath = "config.properties";
		try {
			props.load( new FileInputStream(new File(filePath)) ) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String usr = props.getProperty("usr"+userId);
		String psw = props.getProperty("psw"+userId);
		
		if ( usr == null || psw == null ) return null;
		
		String[] data = new String[]{ usr, psw };
		
		return data;
	}
}
