package vowel_detection;

import java.sql.SQLException;
import java.sql.Statement;

public class Speaker {
	
	private String _first_name = null;
	private int    _sex        = -1;

	public Speaker (String first_name, int sex)
	{
		_first_name = first_name;
		_sex        = sex;
	}
	
	public String first_name () { return _first_name; }
	
	public int sex () { return _sex; }
	
	public String sexString ()
	{
		switch (_sex)
		{
			case 0: return "male";   // MALE
			case 1: return "female"; // FEMALE
		}
		
		return null;
	}
	
	public void save (Statement st) throws SQLException
	{
		st.execute ("INSERT INTO Sex (id, string) SELECT "
				   + _sex + ","
				   + "'" + sexString () + "' "
				   +"WHERE NOT EXISTS"
				   +"("
				   +	"SELECT 1 FROM Sex WHERE id = " + _sex
				   +")"
		);
		
		st.execute ("INSERT INTO Speaker (first_name, sex) SELECT "
				   + "'" + _first_name + "',"
				   + _sex + " "
				   + "WHERE NOT EXISTS"
				   + "("
				   + 	"SELECT 1 FROM Speaker WHERE first_name = '" + _first_name + "'" 
				   + ")"
		);

	}
	
	
}
