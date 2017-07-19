package olxPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ArticlePO extends HomePagePO 
{
	public ArticlePO(WebDriver driver) 
	{
		super(driver);
	}
	
	public void SendMessage(String message)
	{
		wd.findElement(By.name("message")).sendKeys(message);
		wd.findElement(By.cssSelector("sendmessage")).sendKeys(Keys.ENTER);
	}
}
