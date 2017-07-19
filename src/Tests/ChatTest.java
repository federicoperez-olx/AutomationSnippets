package Tests;

import junit.framework.Assert;
import olxPageObjects.ArticlePO;
import olxPageObjects.ChatPO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import Utilities.RandomUtilities;

public class ChatTest extends BaseTest 
{

	private ChatPO chatPO;
	private ArticlePO articlePO;
	private String targetAd = "";
	
	@Before 
	@Override
	public void OnTestStart()
	{
		super.OnTestStart();
		chatPO = new ChatPO(wd);
		articlePO = new ArticlePO(wd);
	}
	
	@Test
	public void TestChat() //send message and assert it afterwards
	{
		// Login as A, go to ad of user B
		// Initiate conversation with STRING X
		// Logout as A, login as B
		// Go to chat and assert presence of X
		
	}
	
	
	public void TestChat(String usr, String psw, String target) //just send message
	{
		// Login as rnd usr x
		homePO.Register(usr, psw);
		homePO.Login(usr, psw);
		
		// Initiate conversation with STRING X
		wd.navigate().to(target);
		
		String message = "Hi! it's "+usr;
		articlePO.SendMessage(message);

		//write message to file?
		//read file lines and assert all lines are present in chat conversations?
	}
	
	@After
	@Override
	public void OnTestFinished()
	{
		super.OnTestFinished();
	}
	
}
