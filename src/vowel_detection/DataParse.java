package vowel_detection;

// testData format :TrainTest, SpeakerNum, Sex, F0, F1, F2, F3, F4, F5, F6, F7, F8, F9, Clas
public class DataParse {
	
	float expected;
	float actual;
	float[] currentData;
	
	public boolean parseTestData(int index, float[][] testData){
		this.currentData = testData[index];
		this.expected = testData[index][1]; // 1= SpeakerNum
		SqlQuery(index);
		return checkResults();
	}
	
	private void SqlQuery(int index){
		//TODO Look up Table of Weights
		//TODO Look up Table of Person's Statics that closest match
		//TODO actual?
		this.actual = (float) 0.123;
	}
	
	private boolean checkResults(){
		if (this.expected == this.actual){
			return true;
		}else{
			System.out.printf("Failed Epectecd:%s \t Actual:%s\n", expected, actual);
			return false;}
	}
}
