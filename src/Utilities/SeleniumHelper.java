package Utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class SeleniumHelper 
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
	
	public static void SetSize(WebDriver wd, int width, int height)
	{
		wd.manage().window().setSize( new Dimension(width, height));
	}
	
	public static void SetPos(WebDriver wd, int x, int y)
	{
		wd.manage().window().setPosition(new Point(x,y) );
	}
	
	public static void SetImplicitWait(WebDriver wd, int time)
	{
		wd.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public static void WaitFor(WebDriver wd, By locator, long secs)
	{
		WebDriverWait ww = new WebDriverWait( wd, secs);
		ww.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public static void Wait5AndClick(WebDriver wd, By locator)
	{
		WebDriverWait ww = new WebDriverWait(wd, 5);
		ww.until(ExpectedConditions.elementToBeClickable(locator));
		
		wd.findElement(locator).click();
	}
	
	public static void Wait5AndSend(WebDriver wd, By locator, String msg)
	{
		WebDriverWait ww = new WebDriverWait( wd, 5 );
		ww.until(ExpectedConditions.elementToBeClickable(locator));
		
		wd.findElement(locator).sendKeys(msg);
	}
	
	public static void ForceWait(long secs)
	{
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void WaitUntilElementVisible(WebDriver wd, By locator, long time)
	{
		WebDriverWait wa = new WebDriverWait(wd, time);
		wa.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public static void WaitUntilElementVisible(WebDriver wd, WebElement we, long time)
	{
		WebDriverWait wa = new WebDriverWait(wd, time);
		wa.until(ExpectedConditions.visibilityOf(we));
	}

	public static void WaitUntilElementClickable(WebDriver wd, By locator, long time) 
	{
		WebDriverWait wa = new WebDriverWait(wd, time);
		wa.until(ExpectedConditions.elementToBeClickable(locator));		
	}

	public static boolean isPresent(WebDriver wd, By locator) 
	{
		try 
		{
			wd.findElement(locator);
		} catch (NotFoundException e) 
		{
			return false;
		}
		return true;
	}



}
