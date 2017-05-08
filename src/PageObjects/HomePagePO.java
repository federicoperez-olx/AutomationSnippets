package PageObjects;

import org.openqa.selenium.WebDriver;




public class HomePagePO extends BasePO
{
	//DONE
	// login
	// sell
	// register
	
	// sell
	//	TODO: store information from article page id 
	//  TODO: save info of article page id in data.properties
	
	public HomePagePO(WebDriver driver)
	{
		wd = driver;
		
		wd.get("http://www.olx.com");
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