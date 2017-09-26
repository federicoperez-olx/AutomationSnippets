package Tests;

import olxPageObjects.PublishPO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;


import Utilities.FileUtilities;
import Utilities.RegexUtilities;
import Utilities.SeleniumHelper;


public class PublishTest extends BaseTest 
{
	private String articlePath;
	private PublishPO publishPage;
	private String usr = "", psw = "";
	
	@Before 
	@Override
	public void OnTestStart()
	{
		super.OnTestStart();
		
		articlePath = System.getProperty("user.dir")+"/Articles/Article01/";
		
		String[] data = GetUsrPsw(1);
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
			//publishPage.SellRnd( );
			publishPage.Sell(articlePath);

			//https://www.olx.com.ar/posting/success/856455871?sk=00a58a095cde6064eb0cae4b43f4f58ee
			String articleId = RegexUtilities.ApplyRegex( wd.getCurrentUrl(), "\\/([0-9]{9})" );
			System.out.println("Article ID is "+articleId);
			
			//Espera el boton ir a mis avisos
			SeleniumHelper.Wait5AndClick(wd, By.cssSelector("a.btn-cancel"));
			
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
