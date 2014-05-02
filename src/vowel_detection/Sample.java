package vowel_detection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Sample {
    
	private final static int CSV_FEATURE_OFFSET = 3;
	
    private boolean _train    = false;
    private int     _speaker  = -1;
    private int     _sex      = -1;
    private float[] _feature = new float [10];
    private int     _class    = -1;
    private int     _hash     =  0;
    
    public Sample (int id, float[] data)
    {
        _train   = ((int) data[0] != 0);
        _speaker = (int) data[1];
        _sex     = (int) data[2];
        _class   = (int) data[13];
        
        /* deep copy features */
        for (int i = 0; i < _feature.length; i++)
            _feature[i] = data[3+i];
        
        
        /* calculate simple unique hash */
//        for (float feature : _feature)
//        {
//            for (char c : Float.toString(feature).toCharArray())
//                _hash = c + (_hash << 6) + (_hash << 16) - _hash;
//        }
        _hash = id;
    }
    
    public int id      () { return _hash;    }
    public boolean train   () { return _train;   }
    public int speaker () { return _speaker; }
    public int sex     () { return _sex;     }
    public int clas    () { return _class;   }
    public String vowel () 
    {
        switch(_class)
        {
            case 0 : return "hid";
            case 1 : return "hId";
            case 2 : return "hEd";
            case 3 : return "hAd";
            case 4 : return "hYd";
            case 5 : return "had";
            case 6 : return "hOd";
            case 7 : return "hod";
            case 8 : return "hUd";
            case 9 : return "hud";
            case 10: return "hed";
            default: return "---";
        }
    }
    
    public float feature (int i) { return _feature[i]; }
    
    public void save (Statement st) throws SQLException
    {
        System.out.println ("INSERT INTO Class (id, vowel) SELECT "
                   + _class    + ","
                   + "'" + vowel ()  + "' "
                   +"WHERE NOT EXISTS"
                   +"("
                   +    "SELECT 1 FROM Sample WHERE id = " + _class
                   +")");
        
        System.out.println ("INSERT INTO Sample (id, speaker, class, train) SELECT "
                   + _hash    + ","
                   + _speaker + ","
                   + _class   + ","
                   + _train   + " "
                   +"WHERE NOT EXISTS"
                   +"("
                   +    "SELECT 1 FROM Sample WHERE id = " + _hash
                   +")");

        st.execute ("INSERT INTO Class (id, vowel) SELECT "
                   + _class    + ","
                   + "'" + vowel ()  + "' "
                   +"WHERE NOT EXISTS"
                   +"("
                   +    "SELECT 1 FROM Class WHERE id = " + _class
                   +")"
        );
        
        st.execute ("INSERT INTO Sample (id, speaker, class, train) SELECT "
                   + _hash    + ","
                   + _speaker + ","
                   + _class   + ","
                   + _train   + " "
                   +"WHERE NOT EXISTS"
                   +"("
                   +    "SELECT 1 FROM Sample WHERE id = " + _hash
                   +")"
        );
        
        for (int i = 0; i < _feature.length; i++)
        {
            st.execute ("INSERT INTO Feature (sample, feature, number) SELECT "
                       + _hash            + ","
                       + i              + ","
                       + _feature[i]
                               
                       +" WHERE NOT EXISTS"
                       +"("
                       +    "SELECT 1 FROM Feature WHERE sample = " + _hash
                       +    " AND feature = " + i
                       +")"
            );
        }
    }
    
    public static int getEstimateSpeakerId (Database db, float[] row)
    {
    	double[] scores = new double[14];
    	
    	
    	for (int i = 0; i < 10; i++)
    	{
    		try (Statement st = db.statement())
    		{
    			double[] results = getNearest (st, i, row[i+CSV_FEATURE_OFFSET], 5);
    			
    			for (int k = 0; k < results.length; k++)
    			{
    				scores[k] += results[k] * (1/10.0); // equal weighting of all features
    			}

    		}
    		catch (SQLException e)
    		{ e.printStackTrace(); }
    	}
    	

    	
    	/* Find highest ranking speaker */
    	double min  = Double.MAX_VALUE;
    	int speaker = -1;
    	for (int i = 0; i < scores.length; i++)
    	{
    		if (scores[i] < min)
    		{
    			min = scores[i];
    			speaker = i;
    		}
    	}
    	
    	return speaker;
    }
    
    public static double[] getNearest (Statement st, int feature, float target, int k_neighbors) throws SQLException
    {
    	double[] scores = new double[14];
    	
    	for (int i = 0; i < scores.length; i++)
    		scores[i] = -1.0;
    	
    	ResultSet rset = null;
    	/*
    	 
    	SELECT * FROM
		(
		    SELECT * FROM
		    (
		        SELECT * FROM Feature AS F
		        WHERE F.feature = $FEATURE ID AND F.number > $TARGET
		        ORDER BY F.number ASC
		        LIMIT 5
		    ) AS upper_half
		
		    UNION ALL
		
		    SELECT * FROM
		    (
		        SELECT * FROM Feature AS F
		        WHERE F.feature = $FEATURE ID AND F.number < $TARGET
		        ORDER BY F.number DESC
		        LIMIT 5
		    ) AS lower_half
		
		    UNION ALL
		
		    SELECT * FROM
		    (
		        SELECT * FROM Feature AS F
		        WHERE F.feature = $FEATURE ID AND F.number = $TARGET
		        ORDER BY F.number DESC
		        LIMIT 5
		    ) AS matches
		
		) AS resultset
		
		ORDER BY ABS(number - $TARGET) ASC, number DESC
    	 */
  
    	rset = st.executeQuery ("SELECT *, ABS(number - " + target + ") AS distance FROM"
    						+	"("
    						+		"SELECT * FROM"
    						+		"("
    						+			"SELECT * FROM Feature"		 								+ " "
    						+			"WHERE feature = " + feature + " AND number > " + target	+ " "
    						+			"ORDER BY number ASC"										+ " "
    						+			"LIMIT " + (k_neighbors / 2)
    						+		") AS upper_half"												+ " "
    						
    						+		"UNION ALL"														+ " "
    						
    						+		"SELECT * FROM"
    						+		"("
    						+			"SELECT * FROM Feature"		 								+ " "
    						+			"WHERE feature = " + feature + " AND number < " + target	+ " "
    						+			"ORDER BY number DESC"										+ " "
    						+			"LIMIT " + (k_neighbors / 2)
    						+		") AS lower_half"												+ " "
    						
    						+		"UNION ALL"														+ " "
    						
    						+		"SELECT * FROM"
    						+		"("
    						+			"SELECT * FROM Feature"		 								+ " "
    						+			"WHERE feature = " + feature + " AND number = " + target	+ " "
    						+		") AS matches"													+ " "
    						+	") AS resultset"													+ " "
    						+	"LEFT JOIN Sample AS S"												+ " "
    						+	"ON S.id = sample"													+ " "
    						+ 	"WHERE S.train = false"												+ " "
    						+ 	"ORDER BY ABS(number - " + target + ") ASC, number DESC"
    						
    	);
    	
    	while (rset.next())
    	{
    		int    speaker  = rset.getInt ("speaker");
    		double value    = rset.getDouble("distance");
    		
    		//System.out.println ("rset(" + feature + "," + speaker + ") = " + value);

    		scores[speaker] = value;
    	}
    	
    	return scores;
    }
}
