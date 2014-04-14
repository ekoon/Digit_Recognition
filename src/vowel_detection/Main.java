package vowel_detection;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main {
	
	private static int MALE   = 0;
	private static int FEMALE = 1;

	public static void main(String[] args)
	{
		File data = new File ("data/vowel-context.data");
		
	    Map<String, Integer> persons = new HashMap<String, Integer>()  
	    {  
			private static final long serialVersionUID = -1458594260311538990L;

			{  
	            put ("Andrew", MALE); 
	            put ("Bill"  , MALE);
	            put ("David" , MALE);
	            put ("Mark"  , MALE);
	            put ("Jo"    , FEMALE);
	            put ("Kate"  , FEMALE);
	            put ("Penny" , FEMALE);
	            put ("Rose"  , FEMALE);
	            put ("Mike"  , MALE);
	            put ("Nick"  , MALE);
	            put ("Rich"  , MALE);
	            put ("Tim"   , MALE);
	            put ("Sarah" , FEMALE);
	            put ("Sue"   , FEMALE);
	            put ("Wendy" , FEMALE);
	        }  
	    };  
		
		
		try
		{
			/* File Parser and Database object */
			Parser parser = new Parser (data);
			Database db   = new Database ("denis", "", "vowels");
			
			/* Populate Speakers */
			for (Entry<String, Integer> person : persons.entrySet())
			try (Statement st = db.statement ())
			{
				Speaker speaker = new Speaker (person.getKey(), person.getValue());
				speaker.save (st);
				st.close ();
				System.out.println ("Added " + speaker.first_name() + " to database");
			}
			catch (SQLException e)
			{ e.printStackTrace(); }
			
			
			
			
			/* Populate Features & Samples */
			for (float[] buffer = new float[14]; parser.next(buffer);)
			{
				Sample sample = new Sample (buffer);
				
				try (Statement st = db.statement ())
				{
					sample.save (st);
				}
				catch (SQLException e)
				{ e.printStackTrace(); }
				
			}
		}
		catch (NumberFormatException | IOException e)
		{ e.printStackTrace(); }
		
		
	}

}
