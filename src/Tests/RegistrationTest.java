package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//@RunWith(Parameterized.class)
public class RegistrationTest extends BaseTest
{

	@Test
	public void RegisterSuccessTest()
	{
		String[] data = GetUsrPsw(1);
		System.out.println("Data "+data[0]);
		
		homePO.Register(data[0], data[1]);
		
		assertEquals(wd.getCurrentUrl(), "https://www.olx.com.ar/register/success");
	}
	
	@Before
	@Override
	public void OnTestStart() 
	{
		super.OnTestStart();
	}
	
	@After
	@Override
	public void OnTestFinished()
	{
		super.OnTestFinished();
	}
	
	@Test
	public void RegisterFailsTest() 
	{
		homePO.Register("", "");
		assertNotEquals(wd.getCurrentUrl(), "https://www.olx.com.ar/register/success");
	}
	
	/*
	@Parameter(0)
	public String user;
	
	@Parameter(1)
	public String password;
	
    @Parameters
    public static Iterable<Object[]> data() 
    {
        return Arrays.asList(
        		new Object[][] 
        				{ 
          				{ "a@a.com", "p" }, 
          				{ "b@b.com", "p" }, 
          			    }	);
    }
    */
}