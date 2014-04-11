/**
 * 
 */
package vowel_detection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * @author exk8925
 *
 */
@RunWith(JUnit4.class)
public class Test_Data {

	@BeforeClass
	public static void testSetup() {
		//TODO form some connection with DB
	}
	
	@AfterClass
	public static void testCleanup() {
		// Teardown for data used by the unit tests
		//TODO disconnect from DB
		//TODO Create a report of all the test cases 
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExceptionIsThrown() {
		MyClass tester = new MyClass();
	    tester.multiply(1000, 5);
	}
	  
	/**
	 * @param args
	 */
	@Test
	public void Test_Cases() {
		String expected = "";
		String actual = "";
		org.junit.Assert.assertEquals("failure - DB recognized the wrong person", expected, actual);
	}

}
