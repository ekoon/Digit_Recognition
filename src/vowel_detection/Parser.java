package vowel_detection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
	
	private BufferedReader bufreader_ = null;

	public Parser (File filename) throws FileNotFoundException
	{
		bufreader_ = new BufferedReader(new FileReader(filename));
	}

	public boolean next (float[] buffer) throws NumberFormatException, IOException
	{
		String   line    = null;
		String[] columns = null;
		
		if ((line = bufreader_.readLine()) == null)
			return false;
		
		if ((columns = line.split("[ ,]+")) == null)
			return false;

		for (int i = 0; i < columns.length; i++)
			buffer[i] = Float.parseFloat(columns[i]);
		
		return true;
	}
}
