package Tests;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import PageObjects.HomePagePO;
import Utilities.SeleniumHelper;


public abstract class BaseTests extends TestCase
{
	private WebDriver wd;
	private HomePagePO homePO;
	
	@Before
	protected void OnTestStart()
	{
		System.out.println("Test Start");
		wd = SeleniumHelper.getChromeExtended();
		homePO = new HomePagePO(wd);
	}
	
	@Test
	protected void TestRegister()
	{
		homePO.Register("", "");
		assertEquals(wd.getCurrentUrl(), "https://www.olx.com.ar/register/success");
		
	}
	
	@After
	protected void OnTestFinished()
	{
		System.out.println("Test End");
		wd.close();
	}
	
}
