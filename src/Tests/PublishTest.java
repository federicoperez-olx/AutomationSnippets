package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import PageObjects.HomePagePO;
import PageObjects.PublishPO;
import Utilities.FileUtilities;
import Utilities.RegexUtilities;
import Utilities.SeleniumHelper;

public class PublishTest extends BaseTest 
{
	private String path;
	private PublishPO publishPage;
	private String usr = "", psw = "";
	
	@Before 
	@Override
	public void OnTestStart()
	{
		super.OnTestStart();
		path = System.getProperty("user.dir")+"/Articles/Article02/";
		
		String[] data = GetUsrPsw(4);
		usr = data[0];
		psw = data[1];
	}
	
	@Test
	public void PublishSuccess()
	{	
		publishPage = new PublishPO(wd);
		
		try
		{
			
			homePO.Register(usr, psw);
			homePO.Login(usr, psw);
			publishPage.SellRnd( );
			 
			//https://www.olx.com.ar/posting/success/856455871?sk=00a58a095cde6064eb0cae4b43f4f58ee
			 
			String articleId = RegexUtilities.ApplyRegex(wd.getCurrentUrl(), "\\/([0-9]{1,})");
			System.out.println( wd.getCurrentUrl() );
			System.out.println("article id is "+articleId);
			
			FileUtilities.WriteFile("cuentas.txt", usr+"\n"+psw);
		
		}catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("No such element exception@ "+wd.getCurrentUrl());
		}
		
	}

	
	@After

	@Override
	public void OnTestFinished()
	{
		super.OnTestFinished();
	}
}
