package olxPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ChatPO extends BasePO
{
	//https://www.olx.com.ar/myolx/chat

	//sendMessage
	
	public ChatPO(WebDriver driver) 
	{
		wd = driver;
	}
	
	public void GoToMyMessages()
	{
		
	}
	
	public void FindConversation(String identifier)
	{
		// ¿User name displayed in conversation?
		
		//List<WebElement> els = wd.findElements(By.xpath("\/\/*[@id=\"user-chat\"]\/div[2]\/ul\/li[1]\/div[1]\/h1") ); 
		
		// ¿Picks up conversation?
		List<WebElement> els = wd.findElements(By.cssSelector("li.conversation.unavailable") );
		
		for (int i = 0; i < els.size(); i++) 
		{
			//els.
		}
	}

	public void SendMessage(String message)
	{
		wd.findElement(By.cssSelector("input.sendMessage")).sendKeys(message);
		wd.findElement(By.cssSelector("input.sendMessage")).sendKeys(Keys.ENTER);
	}
	
}
