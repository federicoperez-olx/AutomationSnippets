package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;


public class SeleniumFactory 
{
	
	
	public static void setPathChromeDriver()
	{
		System.setProperty("webdriver.chrome.driver","deps/chromedriver");
	}
	
	public static WebDriver getChromeDriver()
	{
		System.setProperty("webdriver.chrome.driver","deps/chromedriver");
		return new ChromeDriver();
	}
	
	public static WebDriver getChromeDriver(ChromeOptions co)
	{
		System.setProperty("webdriver.chrome.driver","deps/chromedriver");
		return new ChromeDriver(co);
	}
	
	public static WebDriver getFirefoxDriver()
	{
		System.setProperty("webdriver.gecko.driver","deps/geckodriver");
		return new FirefoxDriver();
	}
	
	public static WebDriver getPhantomJSDriver()
	{
		DesiredCapabilities desireCaps = new DesiredCapabilities();
		desireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "deps/phantomjs");
		desireCaps.setJavascriptEnabled(true);
		
		return new PhantomJSDriver(desireCaps);
	}	
}
