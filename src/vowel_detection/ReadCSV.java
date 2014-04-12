package vowel_detection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {
	
	public static float[][] obtainTestData(float[][] testData, int maxRow, int maxCol) 
		throws IOException {
			//TODO ADD location	 
			final String dir = System.getProperty("user.dir");
		    System.out.println("current dir = " + dir);  	
			String csvFile = "\\data\\TESTING_DATA.csv";
			BufferedReader br = null;
			String line = "";
			try {
				br = new BufferedReader(new FileReader(csvFile));
				for (int row = 0; row < maxRow; row++){
					String strgCol[] = line.split(",");
					for (int col = 0; row < maxCol; col++){
						float num = Float.parseFloat(strgCol[col]);
						testData[row][col] = num;
					}
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}finally {
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
