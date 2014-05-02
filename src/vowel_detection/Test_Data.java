/**
 * 
 */
package vowel_detection;

//https://github.com/junit-team/junit/wiki/Assertions
//http://www.tutorialspoint.com/junit/junit_test_framework.htm
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import java.io.File;
import java.sql.SQLException;

/**
 * @author exk8925
 *
 */

public class Test_Data {
	static int maxCol = 14;		
	static int maxRow = 462;	// Number of test data
	static float[][] csvData = new float[maxRow][maxCol];
	static Database db;
	
	@Rule
	 public ErrorCollector collector = new ErrorCollector();
		
	@BeforeClass
	public static void testSetup() {     
		// form some connection with DB	for Training Set        
		/* File Parser and Database object */
        db   = new Database ("denis", "", "vowels");
        if(!db.work()){
        	System.out.printf("Could not connect to the DB\n");
        	assertTrue("Could not connect to the DB", false);  
        }
    	   
		
		// Read CSV File for test samples
//		ReadCSV Data = new ReadCSV();
//		
//
//		
//		try{
//			csvData = Data.obtainTestData(csvData, maxRow, maxCol);
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.printf("Test Data fail reading CSV file");
//			assertTrue("Test Data fail reading CSV file", false);
//		}
	}
	  
	/**
	 * @param args
	 */
	@Test
	public void Test_Cases() {		
		boolean success;
		
		int passed = 0;
		
		for (int i=0; i<maxRow; i++){
			ValidateWDb tester = new ValidateWDb();
			success = tester.getSample(db);	
			collector.checkThat(success, equalTo(true));
			
			passed += (success) ? 1 : 0;
			
			if ( !success ){
				System.out.printf("Test%d did not pass\n", i);
			}
		}
		System.out.println ("PREDICTION TEST RESULTS");
		System.out.println ("-----------------------");
		System.out.println ("Tests : " + maxRow);
		System.out.println ("Passed: " + (passed * 100)/(maxRow * 1.0) + "%");
	}
	
	@AfterClass
	public static void testCleanup() {
		// Teardown for data used by the unit tests
		//TODO disconnection from DB with boolean disconnectDB as a return		
        try{
            db = null;
        }catch (NumberFormatException e){ 
        	e.printStackTrace(); 
        	System.out.printf("Did not delete DB");
        	assertTrue("Did not delete DB", false); 
        } 
	}	

}
