package olxPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ChatPO extends HomePagePO
{
	//https://www.olx.com.ar/myolx/chat

	//sendMessage
	
	public ChatPO(WebDriver driver) 
	{
		super(driver);
	}
	
	public void GoToMyMessages()
	{
		
	}

	public void SendMessage(String message)
	{
		wd.findElement(By.cssSelector("input.sendMessage")).sendKeys(message);
		wd.findElement(By.cssSelector("input.sendMessage")).sendKeys(Keys.ENTER);
	}
	
}
