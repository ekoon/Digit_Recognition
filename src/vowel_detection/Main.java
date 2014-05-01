package vowel_detection;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    
    public static void main(String[] args)
    {
        File data = new File ("data/vowel-context.data");           
        
        try
        {
            /* File Parser and Database object */
            Parser parser = new Parser (data);
            Database db   = new Database ("denis", "", "vowels");
            
            /* Populate Speakers */
            Who speakers = new Who();
            for (Speaker speaker : speakers.AllSpeakers() )
            //for (Speaker speaker : speakers)
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
