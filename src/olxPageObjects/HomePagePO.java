package olxPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.SeleniumHelper;




public class HomePagePO extends BasePO
{
	
	
	public HomePagePO(WebDriver driver)
	{
		wd = driver;
		
		wd.get("http://www.olx.com");
		SeleniumHelper.WaitFor(wd, By.cssSelector("input.field.search"), 2);
	}
	
	public HomePagePO(WebDriver driver, boolean hack)
	{
		wd = driver;
		wd.get("http://www.olx.com.ar/login");
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