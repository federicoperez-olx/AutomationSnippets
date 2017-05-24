package Utilities;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;


public class SeleniumFactory 
{


	private static String chromeDriverLocation = "deps/chromedriver";
	private static String firefoxDriverLocation = "deps/geckodriver";
	
	public static void setPathChromeDriver()
	{
		System.setProperty("webdriver.chrome.driver",chromeDriverLocation);
	}
	
	public static WebDriver getChromeDriver()
	{
		System.setProperty("webdriver.chrome.driver",chromeDriverLocation);
		return new ChromeDriver();
	}
	
	public static WebDriver getChromeDriver(ChromeOptions co)
	{
		System.setProperty("webdriver.chrome.driver",chromeDriverLocation);
		return new ChromeDriver(co);
	}
	
	public static WebDriver getFirefoxDriver()
	{
		System.setProperty("webdriver.gecko.driver",firefoxDriverLocation);
		return new FirefoxDriver();
	}
	
	public static WebDriver getPhantomJSDriver()
	{
		DesiredCapabilities desireCaps = new DesiredCapabilities();
		desireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "deps/phantomjs");
		desireCaps.setJavascriptEnabled(true);
		
		return new PhantomJSDriver(desireCaps);
	}

	
	private static String extensionPath = "deps/modifyheaders.crx";
	
	public static WebDriver getChromeTesting()
	{
		return getChromeReqHeaders("x-origin-olx", "testing");
	}
	
	public static WebDriver getChromeStaging()
	{
		return getChromeReqHeaders("x-origin-olx", "staging");
	}
	
	public static WebDriver getChromeReqHeaders(String header, String value) 
	{
		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
		
		ChromeOptions options = new ChromeOptions();
		options.addExtensions( new File(extensionPath) );
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		WebDriver wd = new ChromeDriver(options);
		
		wd.get("chrome-extension://innpjfdalfhpcoinfnehdnbkglpmogdi/options.html");
		
		wd.findElement(By.xpath("//button[@tooltip='Add New']")).click();
		
		new Select(  wd.findElement(By.name("action")) ).selectByVisibleText("Add");
		
		wd.findElement(By.name("name")).sendKeys(header);

		wd.findElement(By.name("value")).sendKeys(value);

		wd.findElement(By.xpath("//button[@tooltip='Save']")).click();
		
		wd.findElement(By.xpath("//button[@tooltip='Start Modifying Headers']")).click();
		
		wd.findElement(By.xpath("//button[@tooltip='Enable']")).click();
		
		return wd;
	}	
}
