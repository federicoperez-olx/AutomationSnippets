package Tests;

import olxPageObjects.ArticlePO;
import olxPageObjects.ChatPO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import Utilities.SeleniumHelper;

public class ChatTest extends BaseTest 
{

	private ChatPO chatPO;
	private ArticlePO articlePO;
	private String targetAd = "";
	
	private String emitUsr, emitPsw, recUsr, recPsw;
	private String message = "message #1";
	
	@Before 
	@Override
	public void OnTestStart()
	{
		super.OnTestStart();
		chatPO = new ChatPO(wd);
		articlePO = new ArticlePO(wd);

		String[] data = GetUsrPsw(1);

		emitUsr = data[0];
		emitPsw = data[1];
		
		data = GetUsrPsw(2);
		
		recUsr = data[0];
		recPsw = data[1];
	}
	
	@Test
	public void TestChat() //send message and assert it afterwards
	{
			// Login as emitting usr
			// Initiate conversation with MESSAGE
  			
			homePO.Register(emitUsr, emitPsw);
			homePO.Login(emitUsr, emitPsw);
			
			articlePO.SendMessage(message);
			
			//"Tu mensaje ha sido enviado"
			SeleniumHelper.WaitFor(wd, By.cssSelector("div.success.icons-material.icon-material-ok"), 5);
			
			homePO.Logout();
			
			// Logout as emitting usr, login as receiving usr
			homePO.Login(recUsr, recPsw);
			
			// Go to chat and assert presence of X
			chatPO.GoToMyMessages();
			
			chatPO.FindConversation("faro");
	}
	
	
	@After
	@Override
	public void OnTestFinished()
	{
		homePO.Logout();
		super.OnTestFinished();
	}
	
}
