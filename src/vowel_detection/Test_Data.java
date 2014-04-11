/**
 * 
 */
package vowel_detection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
<<<<<<< Updated upstream
=======
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
>>>>>>> Stashed changes

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author exk8925
 *
 */

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
	  
	/**
	 * @param args
	 */
	@Test
	public void Test_Cases() {
		DataParse tester = new DataParse();
		for (int i=0; i<50; i++){
			// might replace with regression testing within the junit framework
		}
	}

}
