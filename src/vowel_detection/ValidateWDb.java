package vowel_detection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// testData format :TrainTest, SpeakerNum, Sex, F0, F1, F2, F3, F4, F5, F6, F7, F8, F9, Clas
public class ValidateWDb {
	
	int expected;
	int actual;
	float[] currentData;
	int index;
	Parser parser = null;
	float[] buffer = new float[14];
	
	public ValidateWDb ()
	{
		try {
			parser = new Parser (new File("data/TESTING_DATA.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getSample(Database db){
		
		try {
			parser.next(buffer);
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		this.expected = Math.round(buffer[1]); // 1= SpeakerNum
		this.actual   = Math.round(Sample.getEstimateSpeakerId(db, buffer));
		
		return (this.expected == this.actual);
	}	
}
