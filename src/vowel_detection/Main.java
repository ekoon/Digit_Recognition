package vowel_detection;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Main {
    
    private static int MALE   = 0;
    private static int FEMALE = 1;

    public static void main(String[] args)
    {
        File data = new File ("data/vowel-context.data");
        
        Speaker[] speakers = {
            new Speaker ("Andrew" , 0 , MALE  ),
            new Speaker ("Bill"	  , 1 , MALE  ),
            new Speaker ("David"  , 2 , MALE  ),
            new Speaker ("Mark"   , 3 , MALE  ),
            new Speaker ("Jo"     , 4 , FEMALE),
            new Speaker ("Kate"   , 5 , FEMALE),
            new Speaker ("Penny"  , 6 , FEMALE),
            new Speaker ("Rose"   , 7 , FEMALE),
            new Speaker ("Mike"   , 8 , MALE  ),
            new Speaker ("Nick"   , 9 , MALE  ),
            new Speaker ("Rich"   , 10, MALE  ),
            new Speaker ("Tim"    , 11, MALE  ),
            new Speaker ("Sarah"  , 12, FEMALE),
            new Speaker ("Sue"    , 13, FEMALE),
            new Speaker ("Wendy"  , 14, FEMALE)
        };
        
        
        try
        {
            /* File Parser and Database object */
            Parser parser = new Parser (data);
            Database db   = new Database ("denis", "", "vowels");
            
            /* Populate Speakers */
            for (Speaker speaker : speakers)
            try (Statement st = db.statement ())
            {
                speaker.save (st);
                st.close ();
                System.out.println ("Added " + speaker.first_name() + " to database");
            }
            catch (SQLException e)
            { e.printStackTrace(); }
            
            
            
            
            /* Populate Features & Samples */
            int i = 0;
            for (float[] buffer = new float[14]; parser.next(buffer); i++)
            try (Statement st = db.statement ())
            {
                Sample sample = new Sample (i, buffer);
                sample.save (st);
            }
            catch (SQLException e)
            { e.printStackTrace(); }
        }
        catch (NumberFormatException | IOException e)
        { e.printStackTrace(); }
        
        
    }

}
