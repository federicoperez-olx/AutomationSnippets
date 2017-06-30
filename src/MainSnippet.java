import java.io.File;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import Utilities.AnukoPO;
import Utilities.FileUtilities;
import Utilities.SeleniumFactory;
import Utilities.XLSXParser;
/*
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import Utilities.RandomUtilities;
import Utilities.RegexUtilities;
import Utilities.SeleniumHelper;

import olxPageObjects.HomePagePO;
import olxPageObjects.PublishPO;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
*/

public class MainSnippet 
{

	public static void main(String[] args) throws Exception 
	{
		Print("Start...");
		
		Log(  );
		
		Print("End.");
	}
	
	
	static void Log() throws Exception
	{
		String rootPath = new File("").getAbsolutePath();
		
		Properties prop = FileUtilities.newPropFromFile( rootPath + "/config.properties" );

		String duration = prop.getProperty("anukoDuration");;
		String activity = prop.getProperty("anukoActivity");
		
		String filterCol = prop.getProperty("anukoFilterCol");
		String filterTerm = prop.getProperty("anukoFilterTerm");
		String filepath = rootPath + "/" + prop.getProperty("anukoFileName");	
		int dateCol = Integer.parseInt( prop.getProperty("anukoDateCol") );
		int todaysCol = Integer.parseInt( prop.getProperty("anukoTodayCol") );
		
		XLSXParser.SetFilter(filterCol, filterTerm);
		HashMap<String, String> data = XLSXParser.Parse(filepath, 0, dateCol, todaysCol);
		
		WebDriver wd = SeleniumFactory.getChromeDriver();
		wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		AnukoPO anukill = new AnukoPO( wd );
		anukill.Login( prop.getProperty("anukoUsr"), prop.getProperty("anukoPsw") );
		
		
        for ( String key : data.keySet() ) 
        {
        	String date = key;
    		String note = data.get(key);
    		
    		if ( note.equals("") ) continue;
    		
    		System.out.println( "On "+date+" I logged: "+note );
    		
    		anukill.Log(date, "OLX", activity, duration, note);
    		//SeleniumHelper.ForceWait(1);
        }
	}
	
	/*
	static void QnD_Publish(String usr, String psw, String baseURL )
	{		
		WebDriver wd = SeleniumFactory.getChromeDriver();
		
		SeleniumHelper.SetImplicitWait(wd, 3);
		
		PublishPO publishPage = new PublishPO(wd);			
		HomePagePO homePO = new HomePagePO(wd);
		wd.navigate().to(baseURL);
		
		homePO.Login(usr, psw);
		
		String titulo = "El Titulo: " + RandomUtilities.GenerateString(16);
		String desc = "Descripción: \n" + RandomUtilities.GenerateString(52);
		String priz = RandomUtilities.GenerateInt(99999999);
		String img = RandomUtilities.GenerateRndImage(10, 10);
		
		publishPage.Sell("16", "1070", titulo, desc, priz, img);
		
		String articleId = RegexUtilities.ApplyRegex(wd.getCurrentUrl(), "\\/([0-9]{1,})");
		System.out.println( wd.getCurrentUrl() );
		System.out.println("article id is "+articleId);
	}
	
	static void TestChat(String usr, String psw, String url)
	{
		WebDriver wd = SeleniumFactory.getChromeDriver();
		
		HomePagePO ho = new HomePagePO(wd);
		
		ho.Register(usr, psw);
		ho.Login(usr, psw);
		
		//////// Primer mensaje
		wd.get( url );
		
		String msg_inicial = "Auto message# \n" + RandomUtilities.GenerateString();
		
		wd.findElement(By.name("message")).sendKeys(msg_inicial);
		wd.findElement(By.className("sendmessage")).click();
		
		////////
		
		
		String id = RandomUtilities.GenerateString(3);
		
		SeleniumHelper.Wait5AndClick(wd, By.linkText("Mis Mensajes"));
		
		By locator = By.cssSelector("input.sendMessage");
		
		SeleniumHelper.WaitFor(wd, locator, 10);
		
		for (int i = 0; i < 100; i++) 
		{
			SeleniumHelper.ForceWait( 1 );

			String msg = id + " " + i;
			wd.findElement(locator).sendKeys(msg);
			wd.findElement(locator).sendKeys(Keys.ENTER);
			
		}
		
		
	}
	
	static void TestRegister()
	{

		String path = System.getProperty("user.dir")+"/Articles/Article01/";
		WebDriver wd = SeleniumFactory.getChromeDriver();
		
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
		
		WebDriver wd = SeleniumFactory.getChromeDriver();
		
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

			wd = SeleniumFactory.getChromeDriver();
			
			SeleniumHelper.SetSize(wd, 1280, 1024);
			SeleniumHelper.SetImplicitWait(wd, 5);
			
			usr = "user"+ id +"@olx.com";
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
			
			WebDriver wd = SeleniumFactory.getChromeDriver();//new ChromeDriver();
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
			
			WebDriver wd = SeleniumFactory.getChromeDriver();//new ChromeDriver();
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

	
	
	static void TriggerBug()
	{
		String telltale = "googletag";
		float avg_ads = 0;
		float avg_msg = 0;
		
		WebDriver wd = SeleniumFactory.getChromeDriver();
		HomePagePO h = new HomePagePO( wd );
		
		String usr = "prodA@olx.com";
		String pass = "pass";
		
		h.Register(usr, pass);
		h.Login(usr, pass);
		
		for (int i = 0; i < 100; i++) 
		{
			String msg = RandomUtilities.GenerateString(10);
			Print("Search#"+i+" -  "+ msg);
			h.Search( msg );
			SeleniumHelper.ForceWait(2);
		}
		
		Print("Metrics start");
		try
		{	
			long durationAds = 0, durationMsgs = 0;
			int q = 100;
			for (int i = 0; i < q; i++) 
			{
				h.MyAds();
				//wait for icons of wallet to appear
				durationAds = SeleniumHelper.WaitCountFor(wd, By.cssSelector("div.coins"), 5);
				
				avg_ads += durationAds;
				
				Print("Iteration#" + i + ", Ad load time "+ durationAds);
				
				
				h.MyMessages();
				durationMsgs = SeleniumHelper.WaitCountFor(wd, By.cssSelector("div.icon-message"), 5);
				//wait for icon of "Por el momento, no tenés mensajes."
				
				avg_msg += durationAds;
				Print("Iteration#" + i + ", Msg load time "+ durationMsgs);
			}
	
			avg_msg /= q;
			avg_ads /= q;
			
			Print("Ads avg load time "+ avg_ads);
			Print("Msg avg load time "+ avg_msg);
		}finally{
		
			LogEntries logs = wd.manage().logs().get(LogType.BROWSER);
			
			for (LogEntry logEntry : logs) 
			{
				if ( logEntry.getMessage().contains(telltale) )
				{
					Print( logEntry.getTimestamp()+" "+logEntry.getLevel()+" "+ logEntry.getMessage() );
				}
			}
		}
		
		
	}
	*/
	public static void Print(Object... msg)
	{
		for (int i = 0; i < msg.length; i++) 
		{
			System.out.println( msg[i] );	
		}
	}
	
}