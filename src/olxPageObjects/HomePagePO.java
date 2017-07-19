package olxPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.SeleniumHelper;




public class HomePagePO extends BasePO
{
	
	public HomePagePO(WebDriver driver)
	{
		wd = driver;
		
		wd.get("http://www.olx.com");
		SeleniumHelper.WaitFor(wd, By.cssSelector("input.field.search"), 2);
	}
	

}