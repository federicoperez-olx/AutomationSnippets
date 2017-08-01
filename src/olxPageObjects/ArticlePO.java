package olxPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import Utilities.SeleniumHelper;

public class ArticlePO extends BasePO 
{
	public ArticlePO(WebDriver driver) 
	{
		wd = driver;
	}
	
	public void SendMessage(String message)
	{	
		wd.findElement(By.name("message")).sendKeys(message);
		wd.findElement(By.className("sendmessage")).click();
	}
}
