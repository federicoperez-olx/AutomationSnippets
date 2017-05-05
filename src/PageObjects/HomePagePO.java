package PageObjects;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import Utilities.SeleniumHelper;



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
	
	public void Sell()
	{
		By venderLocator = By.xpath("//a[contains(.,'Vender')]");
		SeleniumHelper.Wait5AndClick(wd, venderLocator);
		
		By categoryLocator = By.xpath("//a[@data-qa='category-name-859']");
		SeleniumHelper.WaitFor(wd, 5, categoryLocator);
		
		wd.findElement(categoryLocator).click();
		wd.findElement(By.xpath("//a[@data-qa='subcategory-name-860']")).click();
		
		wd.findElement(By.id("field-title")).sendKeys("Books by Baudrillard");
		wd.findElement(By.id("field-description")).sendKeys("I am selling a batch of books by Baudrillard, since I can't stand this postmodern asshole");
		
		//wd.findElement(By.id("field-priceW")).sendKeys( new Random().nextInt(10000)+"" );
		SeleniumHelper.Wait5AndSend(wd, By.id("field-priceW"), new Random().nextInt(10000)+"" );
		 
		String pathImage = System.getProperty("user.dir")+"/Articles/Article01/image01.png";
		System.out.println(pathImage);
		//SeleniumHelper.WaitFor(wd, 10, By.id("file0"));
		wd.findElement(By.id("file0")).sendKeys(pathImage);
		
		
		//http://stackoverflow.com/questions/10121750/stop-the-selenium-server-until-file-uploaded
		//SeleniumHelper.ForceWait(5);
		//SeleniumHelper.WaitFor(wd, 25, By.className("image.fill.r1"));
		System.out.println("a");
		wd.findElement(By.className("submit-form")).click();
	}
	
	
	public void Publish()
	{
		throw new NotImplementedException();
		//wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//wd.findElement(By.linkText("Vender")).click();
		
		//WebDriverWait w = new WebDriverWait(wd, 10);
		//w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

		/*
		 	//Get posting session id
	 		String postingSession = "";
			String contactName = "";
			String email = "";
			String country = "";
			String state = "";
			String neighborhood.id = "";
			String neighborhood.name = "";
			String streetAddress = "";
			String latitude = "";
			String longitude = "";
			String currency_type = "";
			String priceType = "";
			String description = "";
			String title = "";
			String make = "";
			String year = "";
			String condition = "";
			String mileage = "";
			String fuel = "";
			String color = "";
			String vehicleTransmission = "";
			String priceW = "";
			String priceC = "";
			String languageId = "";
			String platform = "";
			String ipAddress = "";
			String model = "";
			String category.parentId = "";
			String category.id = "";
			String location = "";

		 
		 	String  postRequest = 
			postingSession="+postingSession
			+"&contactName="+contactName
			+"&email="+email
			+"&country="+country
			+"&state="+state
			+"&neighborhood.id="+neighborhood.id
			+"&neighborhood.name="+neighborhood.name
			+"&streetAddress="+streetAddress
			+"&latitude="+latitude
			+"&longitude="+longitude
			+"&currency_type="+currency_type
			+"&priceType="+priceType
			+"&description="+description
			+"&title="+title
			+"&make="+make
			+"&year="+year
			+"&condition="+condition
			+"&mileage="+mileage
			+"&fuel="+fuel
			+"&color="+color
			+"&vehicleTransmission="+vehicleTransmission
			+"&priceW="+priceW
			+"&priceC="+priceC
			+"&languageId="+languageId
			+"&platform="+platform
			+"&ipAddress="+ipAddress
			+"&model="+model
			+"&category.parentId="+category.parentId
			+"&category.id="+category.id
			+"&location="+location+";
		 * 
		 *
		 * 
		 * Cattegory finders used
		//NO wd.findElement(By.xpath("//a[@class='category icons-material icon-material-cat-362']")).click();
		//NO wd.findElement(By.xpath("//a[@data-id='362']")).click();
		//? wd.findElement(By.xpath("//a[contains(@class,'category icons-material icon-material-cat-362')]")).click();
		//? wd.findElement(By.xpath("//a[contains(@data-id,'362')]")).click();
		//? wd.findElement(By.xpath("//a[contains(@data-qa,'category-name-362')]")).click();
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