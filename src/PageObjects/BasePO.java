package PageObjects;

import java.nio.file.NotDirectoryException;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.FileUtilities;
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
		
		wd.findElement(By.linkText("Submit")).click();
		
	}
	
	
	public void Register(String usr, String psw)
	{
		wd.findElement(By.linkText("Registrarse")).click();
		
		wd.findElement(By.name("email")).sendKeys(usr);
		wd.findElement(By.name("password")).sendKeys(psw);
		
		wd.findElement(By.className("send")).click();
		
	}
	
	/**
	 * Access the sell functionality of the '/posting' page
	 * @param articleFolder 
	 * Folder that contains image(support for only one image, named by default image01.jpg) 
	 * and "data.properties" which contains the data for category, title, description, price, name of image
	 */
	public void Sell(String articleFolder)
	{
		if ( ! FileUtilities.isDirectory(articleFolder) )
		{
			try {
				throw new NotDirectoryException(articleFolder);
			} catch (NotDirectoryException e){
				e.printStackTrace();
			}
		}
		//Acciones de selenium
		
		By venderLocator = By.linkText("Vender");// By.xpath("//a[contains(.,'Vender')]");
		SeleniumHelper.WaitFor(wd, 5, venderLocator); //SeleniumHelper.Wait5AndClick(wd, venderLocator);
		wd.findElement(venderLocator).click();
		
		
		Properties propFile = FileUtilities.newPropFromFile( articleFolder+"data.properties" );
		
		String cat = propFile.getProperty("category");
		String subCat = propFile.getProperty("sub-category");
		String tit = propFile.getProperty("tit", "Books by Baudrillard");
		String desc = propFile.getProperty("desc", "I am selling a batch of books by Baudrillard, since I can't stand this postmodern asshole");
		String price = propFile.getProperty("price", ""+new Random().nextInt(10000) );
		String pathImage = articleFolder + propFile.getProperty("image01", "image01.jpg");
		
		By fileLocator = By.id("file0");
		SeleniumHelper.WaitFor(wd, 10, fileLocator);
		wd.findElement(fileLocator).sendKeys(pathImage);

		By locator = By.className("overlay-image");
		SeleniumHelper.WaitFor(wd, 5, locator);
		
		By categoryLocator = By.xpath("//a[@data-qa='category-name-"+cat+"']");
		SeleniumHelper.Wait5AndClick(wd, categoryLocator);
		
		wd.findElement(By.xpath("//a[@data-qa='subcategory-name-"+subCat+"']")).click();
		
		wd.findElement(By.id("field-title")).sendKeys(tit);
		wd.findElement(By.id("field-description")).sendKeys(desc);
		
		SeleniumHelper.Wait5AndSend(wd, By.id("field-priceW"), price );
		
		//http://stackoverflow.com/questions/10121750/stop-the-selenium-server-until-file-uploaded
		System.out.println("wait");
		
		wd.findElement(By.className("submit-form")).click();
		
		System.out.println("end");
	}
	

	public void JobOffer(String articleFolder)
	{

		if ( ! FileUtilities.isDirectory(articleFolder) )
		{
			try {
				throw new NotDirectoryException(articleFolder);
			} catch (NotDirectoryException e){
				e.printStackTrace();
			}
		}
		
		By venderLocator = By.linkText("Vender");//By.xpath("//a[contains(.,'Vender')]");
		//SeleniumHelper.Wait5AndClick(wd, venderLocator);
		SeleniumHelper.WaitFor(wd, 5, venderLocator);
		
		Properties propFile = FileUtilities.newPropFromFile( articleFolder+"data.properties" );
		
		String cat = propFile.getProperty("category");
		String subCat = propFile.getProperty("sub-category");
		String tit = propFile.getProperty("tit");
		String desc = propFile.getProperty("desc");
		String min = propFile.getProperty("salary_min", "100");
		String max = propFile.getProperty("salary_max", "1000000");
		
		
		
		By categoryLocator = By.xpath("//a[@data-qa='category-name-"+cat+"']");
		SeleniumHelper.WaitFor(wd, 5, categoryLocator);
		
		wd.findElement(categoryLocator).click();
		wd.findElement(By.xpath("//a[@data-qa='subcategory-name-"+subCat+"']")).click();
		
		wd.findElement(By.id("field-title")).sendKeys(tit);
		wd.findElement(By.id("field-description")).sendKeys(desc);
		
		//SeleniumHelper.Wait5AndSend(wd, By.id("field-priceW"), new Random().nextInt(10000)+"" );
		wd.findElement(By.id("field-salary_from")).sendKeys(min);
		wd.findElement(By.id("field-salary_to")).sendKeys(max);
		
		wd.findElement(By.className("submit-form")).click();
	}

}
