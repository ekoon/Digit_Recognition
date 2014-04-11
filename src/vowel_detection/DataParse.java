package vowel_detection;
import static org.junit.Assert.assertEquals;

public class DataParse {
	//TODO Select the next 50 test data and perform statistics on this (anther class)
	// index from Junit framework
	//TODO Parse out the expected name
	//TODO Parse out the actual name calcuated
	
	public void parseTestData(int index){
		
	}
	
	private void performStatisics(int index){
		
	}
	
	private boolean checkResults(String expected, String actual){
		if (expected == actual){
			return true;
		}else{
			System.out.printf("Failed Epectecd:%s \t Actual:%s\n", expected, actual);
			return false;}
	}
}
