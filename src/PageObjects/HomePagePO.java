package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;



public class HomePagePO 
{
	//TODO: search
	// //*[@id="header-view"]/div/form/fieldset/input[1]
	
	
	//TODO: register
	// same as login
	
	//vender (imposiburu)
	private WebDriver wd;
	
	public HomePagePO(WebDriver driver)
	{
		wd = driver;
		
		wd.get("http://www.olx.com");
	}

	//login
	// https://www.olx.com.ar/login
	public void Login(String usr, String psw)
	{
		wd.findElement(By.linkText("Ingresar")).click();
		
		wd.findElement(By.name("usernameOrEmail")).sendKeys(usr);
		wd.findElement(By.name("password")).sendKeys(psw);
		
		wd.findElement(By.linkText("Submit")).click();
		
	}
	
	public void Search(String searchTerm)
	{
		//available in all pages
		wd.findElement(By.cssSelector("input.field.search")).sendKeys(searchTerm);
	}
	
	public void Publish()
	{
		throw new NotImplementedException();
		//wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//wd.findElement(By.linkText("Vender")).click();
		
		//WebDriverWait w = new WebDriverWait(wd, 10);
		//w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

		/*
		String pathImage = "/home/Pictures/sample_01.jpg";
		
		WebElement cat = null, subcat = null, postImg = null;
		/ *
		List<WebElement> links = wd.findElements(By.tagName("a"));

		for (int i = 0; i < links.size(); i++) 
		{
			WebElement e = links.get(i);

			if ( e.getAttribute("data-qa") != null )
			{  
				
				if ( e.getAttribute("data-qa").contains( Hobbies ) )
				{
					System.out.println("Found hobbies in "+e);
					cat = e;
					
				}else
				if ( e.getAttribute("data-qa").contains( "860" ) )
				{
					System.out.println("Found subcat in "+e);
					subcat = e;;
				}
			}
			
			if ( e.getAttribute("data-tracking") != null )
			{
				if ( e.getAttribute("data-tracking").contains("Posting-ClickUploadPicture") )
				{
					System.out.println("Found image post in "+e);
					postImg = e;
					
					
				}
			}
		}
		//

		wd.findElement(By.linkText("Vehículos")).click();
		wd.findElement(By.linkText("Autos")).click();
		
		System.out.println("Loopend");		
		
		//postImg.sendKeys(pathImage);

		
		wd.findElement(By.id("field-title")).sendKeys("Books by Baudrillard");
		
		wd.findElement(By.id("field-description")).sendKeys("I am selling a batch of books by Baudrillard, since I can't stand this postmodern asshole");
		
		wd.findElement(By.id("field-priceW")).sendKeys("63");
		
		wd.findElement(By.className("submit-form")).click();
		*/
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