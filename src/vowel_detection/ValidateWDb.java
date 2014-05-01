package vowel_detection;

// testData format :TrainTest, SpeakerNum, Sex, F0, F1, F2, F3, F4, F5, F6, F7, F8, F9, Clas
public class ValidateWDb {
	
	float expected;
	float actual;
	float[] currentData;
	int index;
	
	public boolean getSample(int index, float[][] testData){
		this.currentData = testData[index];
		this.expected = testData[index][1]; // 1= SpeakerNum
		SqlQuery();
		return checkResults();
	}	
	
private void SqlQuery(){
		//use currentData for finding best results
		
		//TODO Look up Table of Weights
		//TODO Look up Table of Person's Statics that closest match

		//TODO store results from DB into actual
		//temp solution
		this.actual = (float) 1;
	}
	
	private boolean checkResults(){
		if (this.expected == this.actual){
			return true;
		}else{
			Who speakers = new Who();
			System.out.printf("Failed Epectecd:%s \t Actual:%s\n", 
					speakers.SpeakersName(this.expected), //expected 
					speakers.SpeakersName(this.actual)); //actual
			return false;}
	}
}
