
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;

import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import PageObjects.HomePagePO;
import Utilities.FileUtilities;
import Utilities.SeleniumHelper;



public class MainSnippet 
{

	public static void main(String[] args) throws Exception 
	{
		//Test();
		String path = System.getProperty("user.dir")+"/Articles/Job/";
		new HomePagePO(SeleniumHelper.getChromeDriver()).JobOffer(path);
	}
	
	static void Test()
	{
		WebDriver wd = SeleniumHelper.getChromeExtended();
		wd.get("https://www.olx.com.ar/posting");
		String pathImage = System.getProperty("user.dir")+"/Articles/Article01/image02.jpg";
		System.out.println(pathImage);
		
		//SeleniumHelper.WaitFor(wd, 10, By.id("file0"));
		wd.findElement(By.id("file0")).sendKeys(pathImage);
		
		//NO By locator = By.className("div.image.fill.r1");
		//NO By locator = By.cssSelector("div.image.fill.r1");
		//NO By locator = By.xpath("//div[contains(@class,'overlay-image')]");
		By locator = By.className("overlay-image");
		
		SeleniumHelper.WaitFor(wd, 5, locator);


		wd.findElement(By.className("submit-form")).click();
	}
	
	static String[] GetUsrPsw(int userId)
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
		
		WebDriver wd = SeleniumHelper.getChromeDriver();
		
		SeleniumHelper.SetSize(wd, 1280, 1024);
		//SeleniumFactory.SetImplicitWait(wd, 5);
		
		ArrayList<WebDriver> navs = new ArrayList<WebDriver>();

		
		for (int i = 0; i < avisos.length; i++) 
		{
			int id = new Random().nextInt(1000000);
			
			
			wd.get(avisos[i]);
			
			usr = "user"+ id +"@mail.com";
			psw = "pass";
			
			System.out.println("I:"+ i );
			wd.get( avisos[i] );
			
			//comentar
			String msg = "Auto message: "+ id + "\n\n" + generateString();
			
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
			String msg = "Auto message: "+ id + "\n\n" + generateString();
			
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
			String msg = "Auto message# "+ i + "\n\n" + generateString();
			
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
	
	public static String generateString()//(Random rng, String characters, int length)
	{
		Random rng = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int length = new Random().nextInt(15);
		
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
}