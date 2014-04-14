package vowel_detection;

import java.sql.SQLException;
import java.sql.Statement;

public class Sample {
	
	private int     _train    = -1;
	private int     _speaker  = -1;
	private int     _sex      = -1;
	private float[] _feature = new float [10];
	private int     _class    = -1;
	private int     _hash     = 0;
	
	public Sample (float[] data)
	{
		_train   = (int) data[0];
		_speaker = (int) data[1];
		_sex     = (int) data[2];
		_class   = (int) data[13];
		
		/* deep copy features */
		for (int i = 0; i < _feature.length; i++)
			_feature[i] = data[3+i];
		
		
		/* calculate simple unique hash */
		for (float feature : _feature)
		{
			for (char c : Float.toString(feature).toCharArray())
				_hash = c + (_hash << 6) + (_hash << 16) - _hash;
		}
	}
	
	public int id      () { return _hash;    }
	public int train   () { return _train;   }
	public int speaker () { return _speaker; }
	public int sex     () { return _sex;     }
	
	public float feature (int i) { return _feature[i]; }
	
	public void save (Statement st) throws SQLException
	{
//		st.execute ("INSERT INTO Sample SELECT "
//				   + _speaker + ","
//				   + vowel    + ","
//				   + train    + " "
//				   +"WHERE NOT EXIST"
//				   +"("
//				   +	"SELECT 1 FROM Sample WHERE id = " + id ()
//				   +")"
//		);
	}
}
