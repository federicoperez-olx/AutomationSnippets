package olxPageObjects;

import java.nio.file.NotDirectoryException;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import Utilities.FileUtilities;
import Utilities.RandomUtilities;
import Utilities.SeleniumHelper;

public class PublishPO extends BasePO
{
	public PublishPO(WebDriver driver) 
	{
		wd = driver;
	}

	/**
	 * Access the sell functionality of the '/posting' page
	 * @param cat Category of item
	 * @param subCat sub category of item
	 * @param title of publication
	 * @param descriptionW
	 * @param price
	 * @param pathImg absolute path to image of publication
	 */
	public void Sell(String cat, String subCat, String title, String description, String price, String... pathImg)
	{		
		//click en el boton de vender
		By venderLocator = By.linkText("Vender");
		SeleniumHelper.WaitFor(wd, venderLocator,3 );
		wd.findElement(venderLocator).click();
		
		PublishProcess(cat, subCat, title, description, price, pathImg);
	}
	
	public void SellRnd()
	{
		By venderLocator = By.linkText("Vender");// By.xpath("//a[contains(.,'Vender')]");
		SeleniumHelper.WaitFor(wd, venderLocator,3 );
		wd.findElement(venderLocator).click();
		
		String cat = "191";
		String subCat = "207";
		String title = RandomUtilities.GenerateString(60);
		String description = RandomUtilities.GenerateStringExt(250);
		String price = Integer.toString(new Random().nextInt(10000));
		
		
		
		String pathImg = RandomUtilities.GenerateRndImage(64, 64);
		
		PublishProcess(cat, subCat, title, description, price, pathImg);
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

		By venderLocator = By.linkText("Vender");// By.xpath("//a[contains(.,'Vender')]");
		SeleniumHelper.WaitFor(wd, venderLocator,3 );
		wd.findElement(venderLocator).click();
		
		Properties propFile = FileUtilities.newPropFromFile( articleFolder+"data.properties" );
		
		String cat = propFile.getProperty("category");
		String subCat = propFile.getProperty("sub-category");
		String title = propFile.getProperty("tit", "Title description should be >10 chars");
		String description = propFile.getProperty("desc", RandomUtilities.GenerateString(140) );
		String price = propFile.getProperty("price", ""+new Random().nextInt(10000) );
		String pathImg = articleFolder + propFile.getProperty("image01", "image01.jpg");
		
		PublishProcess(cat, subCat, title, description, price, pathImg);
	}

	
	private void PublishProcess(String cat, String subCat, String title, String description, String price, String... pathImg)
	{
		
		SelectCategory(cat, subCat);
		
		UploadImg(pathImg);
		
		wd.findElement(By.id("field-title")).sendKeys(title);
		wd.findElement(By.id("field-description")).sendKeys(description);
		
		SeleniumHelper.Wait5AndSend(wd, By.id("field-priceW"), price );
		
		By ubicacionLocator = By.linkText("Agrega tu ubicación");
		if ( SeleniumHelper.isPresent(wd, ubicacionLocator) )
		{
			wd.findElement(ubicacionLocator).click();
			
			By locationLocator = By.id("profile-location-autocomplete-input");
			SeleniumHelper.WaitUntilElementVisible(wd, locationLocator, 3);
			
			wd.findElement(locationLocator).sendKeys("Ghana - La Rioja, Buenos Aires");
			wd.findElement(locationLocator).sendKeys(Keys.TAB);
			wd.findElement(locationLocator).sendKeys(Keys.ENTER);
			
			By agregarUbiLocator = By.xpath("//input[@data-label='Agregar ubicación']");
			wd.findElement(agregarUbiLocator).click();

			System.out.println("Ubicación cambiada");	
		}	

		By submitLocator = By.className("submit-form");
		SeleniumHelper.WaitUntilElementVisible(wd, submitLocator, 3);
		wd.findElement(submitLocator).click();
		
		//SeleniumHelper.GetScreenshot(wd, "/ss.png");
		
		System.out.println("'"+title +"' published.");
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
		
		By venderLocator = By.linkText("Vender");
		SeleniumHelper.Wait5AndClick(wd, venderLocator);
		
		Properties propFile = FileUtilities.newPropFromFile( articleFolder+"data.properties" );
		
		String cat = propFile.getProperty("category");
		String subCat = propFile.getProperty("sub-category");
		String tit = propFile.getProperty("tit");
		String desc = propFile.getProperty("desc");
		String min = propFile.getProperty("salary_min", "100");
		String max = propFile.getProperty("salary_max", "1000000");
		
		By categoryLocator = By.xpath("//a[@data-qa='category-name-"+cat+"']");
		SeleniumHelper.WaitFor(wd, categoryLocator, 3);
		
		wd.findElement(categoryLocator).click();
		wd.findElement(By.xpath("//a[@data-qa='subcategory-name-"+subCat+"']")).click();
		
		wd.findElement(By.id("field-title")).sendKeys(tit);
		wd.findElement(By.id("field-description")).sendKeys(desc);
		
		//SeleniumHelper.Wait5AndSend(wd, By.id("field-priceW"), new Random().nextInt(10000)+"" );
		wd.findElement(By.id("field-salary_from")).sendKeys(min);
		wd.findElement(By.id("field-salary_to")).sendKeys(max);
		By ubicacionLocator = By.linkText("Agrega tu ubicación");

		if ( SeleniumHelper.isPresent(wd, ubicacionLocator) )
		{
			wd.findElement(ubicacionLocator).click();
			wd.findElement(By.cssSelector("a.input")).click();
		}
		
		wd.findElement(By.className("submit-form")).click();
		
		System.out.println("'"+tit +"' published.");
	}
	
	private void UploadImg(String... pathImg)
	{
		System.out.println("Subiendo imagen");
		//upload images
		By fileLocator = By.id("file0");
		SeleniumHelper.WaitFor(wd, fileLocator, 3);
		
		for (int i = 0; i < pathImg.length; i++) 
		{
			wd.findElement(fileLocator).sendKeys(pathImg[i]);			
		}

		//wait for it to load
		By imageLoadedLocator = By.className("overlay-image");
		SeleniumHelper.WaitFor(wd, imageLoadedLocator, 3 );
	}
	
	private void SelectCategory(String cat, String subCat)
	{	
		By catLocator = By.xpath("//a[@data-qa='category-name-"+cat+"']");
		By subCatLocator = By.xpath("//a[@data-qa='subcategory-name-"+subCat+"']");
		
		SeleniumHelper.WaitUntilElementClickable(wd, catLocator, 3);
		wd.findElement(catLocator).click();
		
		SeleniumHelper.WaitUntilElementClickable(wd, subCatLocator, 3);
		wd.findElement(subCatLocator).click();
		
		System.out.println("Categoria elegidas");
	}
	
}

/*
 * Categorias de Vender
			String Autos = "362";
			String Propiedades = "16";
			String Electronica = "800";
			String Telefonos = "830";
			String HogarMuebles = "806";
			String Deportes = "881";
			String Moda = "815";
			String Bebes = "853";
			String Hobbies = "859";
			String Animales = "811";
			String Herramientas = "938";
			String Trabajo = "821";
			String Servicios = "191";
	
	
 */
