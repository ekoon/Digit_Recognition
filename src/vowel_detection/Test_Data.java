/**
 * 
 */
package vowel_detection;

//https://github.com/junit-team/junit/wiki/Assertions
//http://www.tutorialspoint.com/junit/junit_test_framework.htm
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

<<<<<<< Updated upstream
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

=======

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

>>>>>>> Stashed changes

import org.junit.Rule;
import org.junit.rules.ErrorCollector;



/**
 * @author exk8925
 *
 */

public class Test_Data {
	static int maxCol = 14;		
	static int maxRow = 990;	// Number of test data
	static float[][] csvData = new float[maxRow][maxCol];

	@Rule
	 public ErrorCollector collector = new ErrorCollector();
	
	@BeforeClass
	public static void testSetup() {
		boolean connectDB = true;
		//TODO form some connection with DB with boolean connectDB as a return		
		assertTrue("Could not connect to the DB", connectDB); 
		ReadCSV Data = new ReadCSV();
		try{
		csvData = Data.obtainTestData(csvData, maxRow, maxCol);
		}catch(Exception e){
			System.out.printf("DB not working");
			assertTrue("DB not working", false);
		}
	}
	  
	/**
	 * @param args
	 */
	@Test
	public void Test_Cases() {		
		boolean success;
		for (int i=0; i<maxRow; i++){
			DataParse tester = new DataParse();
			success = tester.parseTestData(i, csvData);			
			collector.checkThat(success, equalTo(true));
			if ( success ){
				System.out.printf("Test%d did not pass\n", i);
			}
		}
	}
	
	@AfterClass
	public static void testCleanup() {
		// Teardown for data used by the unit tests
		boolean disconnectDB = true;
		//TODO disconnection from DB with boolean disconnectDB as a return		
		assertTrue("Could not disconnect from DB", disconnectDB); 
	}	

}
