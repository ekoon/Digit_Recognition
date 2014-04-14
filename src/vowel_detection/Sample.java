package vowel_detection;

import java.sql.SQLException;
import java.sql.Statement;

public class Sample {
    
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
}
