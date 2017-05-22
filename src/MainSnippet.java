
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;

import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import PageObjects.HomePagePO;
import PageObjects.PublishPO;
import Utilities.FileUtilities;
import Utilities.RandomUtilities;
import Utilities.SeleniumHelper;



public class MainSnippet 
{

	public static void main(String[] args) throws Exception 
	{
		TestChat();
	}
	
	static void TestChat()
	{
		WebDriver wd = SeleniumHelper.getChromeDriver();
		
		HomePagePO ho = new HomePagePO(wd);
		
		ho.Login("roverto@mailinator.com", " ");
		
		wd.findElement(By.linkText("Mis Mensajes")).click();
		
		By locator = By.cssSelector("input.sendMessage");
		
		for (int i = 0; i < 100; i++) 
		{
			SeleniumHelper.ForceWait(1);

			String msg = RandomUtilities.GenerateString(2) +" "+ i;
			wd.findElement(locator).sendKeys(msg);
			wd.findElement(locator).sendKeys(Keys.ENTER);
			
		}
		
		
	}
	
	static void TestRegister()
	{

		String path = System.getProperty("user.dir")+"/Articles/Article01/";
		WebDriver wd = SeleniumHelper.getChromeExtended();
		
		HomePagePO home = new HomePagePO(wd);
		
		PublishPO publishPage = new PublishPO(wd);
		
		String id = RandomUtilities.GenerateString("0123456789", 2, 3);
		
		String usr = "asshat"+id+"@mailinator.com";
		String psw = "asshat";
		
		try
		{
			 //wd.navigate().refresh();
			 System.out.println("refresh");
			
			 home.Register(usr, psw);
			 home.Login(usr, psw);
			 publishPage.Sell( path );
			 
			FileUtilities.WriteFile("cuentas.txt", usr+"\n"+psw);
		
		}catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("No such element exception@ "+wd.getCurrentUrl());
		}
		
	}
	
	public static String[] GetUsrPsw(int userId)
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
		
		String[] data = new String[]{ usr, psw };
		
		return data;
	}
	
	static void SearchBumpUps(String idsFilename)
	{
		
		WebDriver wd = SeleniumHelper.getChromeDriver();
		
		SeleniumHelper.SetPos(wd, 0, 0);
		SeleniumHelper.SetSize(wd, 250, 250);
		SeleniumHelper.SetImplicitWait(wd, 2);
		
		String[] ids = FileUtilities.ReadAllLine(idsFilename);
		
		String filename = "results.txt";
		
		int len = ids.length;
		
		for (int i = 0; i < len; i++) 
		{
		
			wd.get("http://inspector.olxlatam.com/search?q="+ids[i]);
			
			WebElement info = wd.findElement(By.cssSelector(".col2"));
			//write id OK, to file if it's featured and if its bumped up
			//else write id, WRONG, if it's not featured and is bumped up
			
			String strInfo = info.getText();
			
			int beginIndex = strInfo.indexOf("Flags");
			int endIndex = strInfo.indexOf("Images");
			
			strInfo = strInfo.substring(beginIndex, endIndex);
			
			//Print( strInfo );

			//4 Blocked : 100 (Closed by spamhandler)
			//11 PaidBumpUp: false
			
			String[] infoPairs = strInfo.split("\n");
			StringBuilder sb = new StringBuilder();
			
			boolean blocked = ! infoPairs[4].contains("Not blocked");
			boolean bumped = infoPairs[11].contains("true");
			if ( !blocked && !bumped )
			{
				sb.append( ids[i]+" " );
				sb.append( infoPairs[4] );
				sb.append( infoPairs[11] );
				
				FileUtilities.WriteFile(filename, sb.toString() );
				
				Print ( sb.toString() );
			}
			/*
			for ( int j = 0; j < infoPairs.length; j++)
			{
				String msg = ids[i]+": "+infoPairs[2];
				FileUtilities.WriteFile(filename, msg );
				
				Print(j+" : "+ infoPairs[j] );
				
			}
			*/
			
			String pMsg = i +" Progress: "+( (float) (i / (float)ids.length * 100) +"%" ); 
			Print(pMsg);
			
		}
		
		
	}
	
	static void EnviarMsjsPublicacionRndUser()
	{
		String[] avisos = FileUtilities.ReadAllLine("urls.txt");
		
		//navegar al aviso
		String usr, psw;
		WebDriver wd;
		
		ArrayList<WebDriver> navs = new ArrayList<WebDriver>();
		
		for (int i = 0; i < avisos.length; i++) 
		{
			int id = new Random().nextInt(1000000);

			wd = SeleniumHelper.getChromeExtended();
			wd.get("chrome://extensions/");
			SeleniumHelper.SetSize(wd, 1280, 1024);
			SeleniumHelper.SetImplicitWait(wd, 5);
			
			usr = "testing-"+ id +"@mail.com";
			psw = "pass";
			
			System.out.println("I:"+ i );
			
			wd.get( avisos[i] );
			
			//comentar
			String msg = "Auto message: "+ id + "\n\n" + RandomUtilities.GenerateString();
			
			wd.findElement(By.name("message")).sendKeys(msg);
			wd.findElement(By.className("sendmessage")).click();	
			
			//registrar/login
			WebElement webElem = wd.findElement(By.name("email"));
			webElem.sendKeys(usr);
			
			webElem.sendKeys("\t");
			webElem = wd.switchTo().activeElement();
			
			webElem.sendKeys(psw);
			//WebElement pswField = wd.findElement(By.name("password")); 
			
			wd.findElement(By.cssSelector("input.submit")).click();
			
			navs.add(wd);
			
			if ( i > 0 )
				navs.get(i-1).quit();
			if (i == avisos.length )
				navs.get(i).quit();
		}
	}
	
	static void EnviarMsjsPublicacion( int offset )
	{
		
		String[] avisos = FileUtilities.ReadAllLine("urls.txt");
		
		//navegar al aviso
		String usr, psw;
		ArrayList<WebDriver> navs = new ArrayList<WebDriver>();
		Thread[] ts = new Thread[avisos.length];
		
		for (int i = 0; i < avisos.length; i++) 
		{
			int id = i + offset;
			
			ts[i] = new Thread();
			
			WebDriver wd = SeleniumHelper.getChromeDriver();//new ChromeDriver();
			SeleniumHelper.SetSize(wd, 1280, 1024);
			
			usr = "user"+ id +"@mail.com";
			psw = "pass";
			
			System.out.println("I:"+ i );
			wd.get( avisos[i] );
			
			//comentar
			String msg = "Auto message: "+ id + "\n\n" + RandomUtilities.GenerateString();
			
			wd.findElement(By.name("message")).sendKeys(msg);
			wd.findElement(By.className("sendmessage")).click();	
			
			//registrar/login
			WebElement webElem = wd.findElement(By.name("email"));
			webElem.sendKeys(usr);
			
			webElem.sendKeys("\t");
			webElem = wd.switchTo().activeElement();
			
			webElem.sendKeys(psw);
			//WebElement pswField = wd.findElement(By.name("password")); 
			
			wd.findElement(By.cssSelector("input.submit")).click();
			
			navs.add(wd);
			
			if ( i > 0 )
				navs.get(i-1).quit();
			if (i == avisos.length-1 )
				navs.get(i).quit();
		}
	}
	
	static void EnviarMsjsPublicacion( )
	{
		
		String[] avisos = FileUtilities.ReadAllLine("urls.txt");
		
		//navegar al aviso
		
		String[] data = GetUsrPsw(3);
		
		String usr = data[0];
		String psw = data[1];
		
		ArrayList<WebDriver> navs = new ArrayList<WebDriver>();
		Thread[] ts = new Thread[avisos.length];
		
		for (int i = 0; i < avisos.length; i++) 
		{
			ts[i] = new Thread();
			
			WebDriver wd = SeleniumHelper.getChromeDriver();//new ChromeDriver();
			SeleniumHelper.SetSize(wd, 1280, 1024);
			
			
			System.out.println("I:"+ i );
			wd.get( avisos[i] );
			
			//comentar
			String msg = "Auto message# "+ i + "\n\n" + RandomUtilities.GenerateString();
			
			wd.findElement(By.name("message")).sendKeys(msg);
			wd.findElement(By.className("sendmessage")).click();	
			
			//registrar/login
			WebElement webElem = wd.findElement(By.name("email"));
			webElem.sendKeys(usr);
			
			webElem.sendKeys("\t");
			webElem = wd.switchTo().activeElement();
			
			webElem.sendKeys(psw);
			//WebElement pswField = wd.findElement(By.name("password")); 
			
			wd.findElement(By.cssSelector("input.submit")).click();
			
			navs.add(wd);
			
			if ( i > 0 )
				navs.get(i-1).quit();
			if (i == avisos.length-1 )
				navs.get(i).quit();
		}
	}
	
	public static void Print(Object msg)
	{
		System.out.println(msg);
	}
	
}