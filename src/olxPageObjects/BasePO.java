package olxPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.SeleniumHelper;

public class BasePO 
{
	protected WebDriver wd;
	
	public void OverrideDriver(WebDriver driver )
	{
		wd = driver;
	}
	
	public void Search(String searchTerm)
	{
		WebElement we = wd.findElement(By.cssSelector("input.field.search"));
		we.clear();
		we.sendKeys(searchTerm);
		we.sendKeys(Keys.ENTER);
	}
	

	public void Login(String usr, String psw)
	{
		SeleniumHelper.Wait5AndClick(wd, By.linkText("Ingresar"));
		//wd.findElement(By.linkText("Ingresar")).click();
		
		SeleniumHelper.Wait5AndSend(wd, By.name("usernameOrEmail"), usr);

		wd.findElement(By.name("password")).sendKeys(psw);
		
		wd.findElement(By.className("send")).click();
		
		SeleniumHelper.ForceWait(2);
	}
	
	public void Logout()
	{
		wd.navigate().to("https://www.olx.com.ar/logout");
	}
	
	public void Register(String usr, String psw)
	{
		wd.findElement(By.linkText("Registrarse")).click();
		
		SeleniumHelper.Wait5AndSend(wd, By.name("email"), usr);
		
		wd.findElement(By.name("password")).sendKeys(psw);
		
		wd.findElement(By.className("send")).click();
		
	}
	
	public void MyAds()
	{
		wd.findElement( By.className("user")).click();
	}
	
	public void MyMessages()
	{
		wd.findElement(By.linkText("Mis Mensajes")).click();
	}
	

}
