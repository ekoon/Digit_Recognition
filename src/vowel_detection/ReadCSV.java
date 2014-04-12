package vowel_detection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {
	public static float[][] obtainTestData(float[][] testData, int maxRow, int maxCol) 
		throws IOException {
			//TODO ADD location	 
			String csvFile = "SOMELOCATION.csv";
			BufferedReader br = null;
			String line = "";
			try {
				
				br = new BufferedReader(new FileReader(csvFile));
				for (int row = 0; row < maxRow; row++){
					for (int col = 0; col < maxCol; col++){
						for(String ind: line.split(",")){
						testData[row][col] = Float.parseFloat(ind);
					}
				}
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 
		System.out.println("Test Data successfully read");
		return testData;
	}
}
