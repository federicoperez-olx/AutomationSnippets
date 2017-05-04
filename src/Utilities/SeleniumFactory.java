package Utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;


public class SeleniumFactory 
{
	
	//https://sites.google.com/a/chromium.org/chromedriver/capabilities
	public static ChromeDriver getChromeExtended( )
	{
		System.setProperty("webdriver.chrome.driver","deps/chromedriver");
		
		ChromeOptions co = new ChromeOptions();
		//co.addExtensions(new File("deps/headers_mod.crx") );
		
		//modify deps/headersmod/main.js to hardcode default profile
		//find entry point
		//find end point
		//paste code in-between
		
		co.addArguments("load-extension=deps/headersmod");
		
		return new ChromeDriver(co);
	}
	
	
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
	
	public static void SetSize(WebDriver wd, int width, int height)
	{
		wd.manage().window().setSize( new Dimension(width, height));
	}
	
	public static void SetPos(WebDriver wd, int x, int y)
	{
		wd.manage().window().setPosition( new Point(x, y) );
	}
	
	public static void SetImplicitWait(WebDriver wd, int time)
	{
		wd.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	
	
	
	/*
	
	//http://stackoverflow.com/questions/36389649/how-can-i-pass-arguments-tochrome-extension-through-selenium-script-c
	public static void executeScript(WebDriver wd, String script)
	{
		driver.ExecuteScript(@"
			    localStorage.setItem('profiles', JSON.stringify([{
			      title: 'Selenium', hideComment: true, appendMode: '',
			      headers: [
			        {enabled: true, name: 'token1', value: '01234', comment: ''},
			        {enabled: true, name: 'token2', value: '56789', comment: ''}
			      ],
			      respHeaders: [],
			      filters: []
			    }]));");
		JavascriptExecutor je = (JavascriptExecutor) wd;
		
		System.out.println(script);
		je.executeScript(script);
	}
	
	
	public static void WaitUntilElementVisible(WebDriver wd, WebElement we, long time)
	{
		WebDriverWait wa = new WebDriverWait(wd, time);
		wa.until(ExpectedConditions.visibilityOf(we));
		
	}
	*/
	
}
