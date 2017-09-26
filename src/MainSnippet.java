import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import olxPageObjects.ArticlePO;
import olxPageObjects.HomePagePO;
import olxPageObjects.PublishPO;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import Utilities.AnukoPO;
import Utilities.FileUtilities;
import Utilities.RandomUtilities;
import Utilities.RegexUtilities;
import Utilities.SeleniumFactory;
import Utilities.SeleniumHelper;
import Utilities.XLSXParser;

public class MainSnippet 
{

	public static void main(String[] args) throws Exception 
	{
		Print("Mission Start!");

		//QnD_TestChat();
		String[] usrs = new String[]
		{
				"argentina@olx.com",
				"uruguay@olx.com",
				"colombia@olx.com",
				"ecuador@olx.com",
				"panama@olx.com",
				"guatemala@olx.com",
				"costarica@olx.com",
				"elsalvador@olx.com"
	
		};
				
		for (int i = 0; i < usrs.length; i++) 
		{
			QnD_Register(usrs[i], "pass");
			SeleniumHelper.ForceWait(2);
		}
		
		
		Print("Mission End.");
	}
	
	
	
	static void QnD_TestChat()
	{

		String usr, filename, logMsg;
		String tgt = GetProperty("target");
		int cadence = Integer.parseInt( GetProperty("delay") ) ;
		
		//fyck it
		WebDriver wd = SeleniumFactory.getChromeProfiled() ;

		SeleniumHelper.SetPos(wd, 0, 0);
		SeleniumHelper.SetSize(wd, 1200, 600);
		
		HomePagePO homePO = new HomePagePO( wd );
		ArticlePO articlePO = new ArticlePO(wd);
		
		//last user prefs	
		int i = Integer.parseInt( GetPropertyFrom("lastUsr.properties", "lastUsr") );
		FileUtilities.DeleteFile("lastUsr.properties");
		
		while( true ) //for (int i = 0; i < 50; i++)
		{
			
			filename = "log" + new SimpleDateFormat("dd-MM-yyyy").format( Calendar.getInstance().getTime() ) + ".txt";
			
			usr = "usr"+i+"x@olx.com";
			homePO.Logout();
			
			logMsg = "Start: " + LocalNow();
			FileUtilities.WriteFile(filename, logMsg );
			Print(logMsg);
			
			homePO.Register(usr, "password");
			homePO.Login(usr, "password");
			FileUtilities.WriteFile(filename, "Logged in as>"+usr );
			
			wd.navigate().to(tgt);
			FileUtilities.WriteFile(filename, "Navigated to target." );
			
			String message = "Hi! it's " + usr + " @" + LocalNow()+".";
			articlePO.SendMessage(message);
			
			Print("Sent message.");
			FileUtilities.WriteFile(filename, "Message sent>" + message );

			try
			{
				//"Tu mensaje ha sido enviado"
				SeleniumHelper.WaitFor(wd, By.cssSelector("div.success.icons-material.icon-material-ok"), 5);
			}catch(TimeoutException te)
			{
				logMsg = "Timed out exception. Refreshing and waiting additional 5 seconds.";
				Print(logMsg);
				FileUtilities.WriteFile(filename, logMsg );
				
				SeleniumHelper.Refresh(wd);

				SeleniumHelper.ForceWait(5);
			}finally
			{
				//last usr prfs
				FileUtilities.WriteFile("lastusr.properties", "lastUsr="+i );
			}
			
			homePO.Logout();
			FileUtilities.WriteFile(filename, "Logged out." );
			
			SeleniumHelper.ForceWait( cadence );
			i++;
			
		}	
	
	}
	
	
	static void QnD_Register(String usr, String psw)
	{
		WebDriver wd = SeleniumFactory.getChromeStaging();
		
		HomePagePO home = new HomePagePO(wd);
		
		try
		{
			 //wd.navigate().refresh();
			 System.out.println("refresh");
			
			 home.Register(usr, psw);
			 //home.Login(usr, psw);
			 wd.quit();
			FileUtilities.WriteFile("cuentas.txt", usr+"\n"+psw);
		
		}catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("No such element exception@ "+wd.getCurrentUrl());
		}
		
	}
	
	static void QnD_Publish(String usr, String psw, int qAds )
	{		
		WebDriver wd = SeleniumFactory.getChromeTesting();
		SeleniumHelper.SetPos(wd, 0,  0);
		SeleniumHelper.SetImplicitWait(wd, 10);
		
		PublishPO publishPage = new PublishPO(wd);
		
		String domain = "ar";
		
		//registrarse
		wd.navigate().to("https://www.olx.com."+domain+"/register");	//wd.findElement(By.linkText("Registrarse")).click();

		SeleniumHelper.Wait5AndSend(wd, By.name("email"), usr);		
		wd.findElement(By.name("password")).sendKeys(psw);
		wd.findElement(By.className("send")).click();
		
		
		wd.navigate().to("https://www.olx.com."+domain+"/login");	//wd.findElement(By.linkText("Ingresar")).click();
		SeleniumHelper.Wait5AndSend(wd, By.name("usernameOrEmail"), usr);
		wd.findElement(By.name("password")).sendKeys(psw);
		wd.findElement(By.className("send")).click();
		
		SeleniumHelper.ForceWait(5);
		//wd.navigate().to("https://www.olx.com."+domain+"/posting");
		
		for (int i = 0; i < qAds; i++) 
		{
			String titulo = i +" - "+ RandomUtilities.GenerateString(10);
			String desc = "Descripción: \n" + RandomUtilities.GenerateString(152);
			String priz = RandomUtilities.GenerateInt(999999);
			String img = RandomUtilities.GenerateRndImage(10, 10);

			if ( i % 2 == 0 )
			{
				publishPage.Sell("853", "855", titulo, desc, priz, img);
			}else if ( i % 3 == 0 ){
				publishPage.Sell("811", "814", titulo, desc, priz, img);
			}else{
				publishPage.Sell("362", "377", titulo, desc, priz, img);	
			}

			SeleniumHelper.ForceWait(5);
			
			String articleId = RegexUtilities.ApplyRegex(wd.getCurrentUrl(), "\\/([0-9]{9}?)");
			System.out.println( wd.getCurrentUrl() );
			System.out.println("article id is "+articleId);	
		}	
	}
	
	public static String GetProperty(String prop)
	{
		Properties props = new Properties();
		
		String filePath = "config.properties";
		try {
			props.load( new FileInputStream( new File(filePath) ) ) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return props.getProperty(prop);
	}

	public static String GetPropertyFrom(String filePath, String prop)
	{
		Properties props = new Properties();
		
		try {
			props.load( new FileInputStream( new File(filePath) ) ) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return props.getProperty(prop);
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
    		String note = data.get( key );
    		
    		if ( note.equals("") ) continue;
    		
    		System.out.println( "On "+date+" I logged: "+note );
    		
    		anukill.Log(date, "OLX", activity, duration, note);
    		//SeleniumHelper.ForceWait(1);
        }
	}
	
	/*
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
	
	static void TestChat(String usr, String psw, String url, int q)
	{
		/*
		String usr = GetProperty("usr");
		String psw = GetProperty("psw");
		String target = GetProperty("url");
		
		int qMsgs = Integer.parseInt( args[0] );
		
		TestChat( usr, psw, target, qMsgs );
		*/
		
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
		
		for (int i = 1; i < q; i++) 
		{
			SeleniumHelper.ForceWait( 1 );

			String msg = id + " " + i;
			wd.findElement(locator).sendKeys(msg);
			wd.findElement(locator).sendKeys(Keys.ENTER);

		}
		
		wd.close();
		
	}
	
	public static void Print(Object... msg)
	{
		for (int i = 0; i < msg.length; i++) 
		{
			System.out.println( msg[i] );	
		}
	}
	
	public static String LocalNow()
	{
		return Instant.now().atZone( ZoneOffset.systemDefault() ).toString();
	}
	
	public static String UTCNow()
	{
		return Instant.now().toString();
	}
}