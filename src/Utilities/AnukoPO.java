package Utilities;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class AnukoPO 
{
	protected WebDriver wd;

	private String regexDate = "(\\d{1,2})-(\\d{1,2})-(\\d{4})";
	//private String homeURL = "http://190.220.6.226:50065/timetracker/login.php";
	private String homeURL = "http://192.168.5.195/timetracker/login.php";
				
	public AnukoPO(WebDriver driver)
	{
		wd = driver;
		wd.get(homeURL);
	}
	
	public void Login(String usr, String psw)
	{
		wd.findElement(By.id("login")).sendKeys(usr);
		wd.findElement(By.id("password")).sendKeys(psw);
		
		wd.findElement(By.id("btlogin")).click();
	}
	
	
	
	/**
	 * @param date Date of log, formatted as MM/dd/YYYY, ex.: 12/31/2000 
	 * @param projectName name of the project exactly as it appears in dropdown
	 * @param activityName name of the activity exactly as it appears in dropdown
	 * @param duration duration to be logged
	 * @param logNote note to add to the log
	 * @throws Exception if it's not logged in it will throw exception
	 */
	public void Log( String date, String projectName, String activityName, String duration, String logNote ) throws Exception
	{
		
		/*String processedDate = ParseDate( date );
		if ( processedDate == null )
		{
			throw new Exception("Date: "+date+" invalid." );
		}
		*/
		
		//browse to the page, either by url (non-PO way) or via button press
		// easier to navigate manually
		wd.navigate().to("http://192.168.5.195/timetracker/mytime.php?date="+date);
		
		// access dropdown menu with ID 'project', select OLX (or whatevs)
		Select projectSelect = new Select( wd.findElement( By.id("project") ) );		
		//css=#myselect option[value=123]
		projectSelect.selectByVisibleText(projectName);
		
		// access dropdown menu with ID 'activity', select QA (or whatevs)
		Select activitySelect = new Select( wd.findElement( By.id("activity") ) );
		activitySelect.selectByVisibleText(activityName);
		
        // access textbox with ID 'duration', fill with '8' (or whatevs)
		wd.findElement(By.id("duration")).sendKeys(duration);
		
        // access textbox with id 'note', fill with comment on tasks done for the day
		wd.findElement(By.id("note")).sendKeys( logNote );
		
        // SUBMIT! ( click button with id 'btn_submit' )
		wd.findElement(By.id("btn_submit")).click();
	}
	
	/**
	 * @param date Date of log, formatted as MM/dd/YYYY, ex.: 12/31/2000 
	 * @param projectName name of the project exactly as it appears in dropdown
	 * @param activityName name of the activity exactly as it appears in dropdown
	 * @param duration duration to be logged
	 * @param logNote note to add to the log
	 * @throws Exception if it's not logged in it will throw exception
	 */
	public void Log( String date, String projectName, String activityName, int duration, String logNote ) throws Exception
	{
		Log(date, projectName, activityName, Integer.toString(duration), logNote);
	}


	private String ParseDate(String date)
	{
		if ( ! RegexUtilities.VerifyPattern(date, regexDate)) 
		{
			System.out.println( "Unable to parse date: '"+ date +"'" );
			return null;
		}
		
		int DAY = 0, MONTH = 1, YEAR = 2;
		String[] splitdDate = RegexUtilities.CaptureGroups(date, regexDate );
		
		StringBuilder sb = new StringBuilder();
		sb.append( splitdDate[MONTH] ).append("/").append( splitdDate[DAY] ).append("/").append( splitdDate[YEAR] );
		
		return sb.toString();
	}
}
