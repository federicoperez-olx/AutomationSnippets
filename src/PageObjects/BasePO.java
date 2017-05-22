package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.SeleniumHelper;

public class BasePO 
{
	protected WebDriver wd;
	
	public void Search(String searchTerm)
	{
		WebElement we = wd.findElement(By.cssSelector("input.field.search"));
		we.sendKeys(searchTerm);
		we.sendKeys(Keys.ENTER);
	}
	

	public void Login(String usr, String psw)
	{
		wd.findElement(By.linkText("Ingresar")).click();
		
		wd.findElement(By.name("usernameOrEmail")).sendKeys(usr);
		wd.findElement(By.name("password")).sendKeys(psw);
		
		//wd.findElement(By.linkText("Entrar")).click();
		wd.findElement(By.className("send")).click();
		SeleniumHelper.ForceWait(5);
	}
	
	
	public void Register(String usr, String psw)
	{
		wd.findElement(By.linkText("Registrarse")).click();
		
		wd.findElement(By.name("email")).sendKeys(usr);
		wd.findElement(By.name("password")).sendKeys(psw);
		
		wd.findElement(By.className("send")).click();
		
	}

}
