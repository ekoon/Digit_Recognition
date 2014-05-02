package vowel_detection;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class DbObject<T> {
	
	private Map<String, Field> _field = new TreeMap<String, Field> ();

	private Table      _table = null;
	private Column     _pkey  = null;

	public final void parse () throws Exception
	{
		@SuppressWarnings("unchecked")
		Class<T> _class = (Class<T>) this.getClass();
    	_table = _class.getAnnotation(Table.class);
    	
    	if (_table == null)
    	{
    		System.out.println ("table is null");
    		return;
    	}
    	
		Column c = null;
		for (Field field : _class.getDeclaredFields ())
	    {
			if ((c = field.getAnnotation(Column.class)) != null)
    			_field.put(c.value(), field);
			
			if (field.isAnnotationPresent(PrimaryKey.class))
				_pkey = c;
	    }
	    
		if (_pkey == null)
    	{
    		System.out.println ("primary key is null");
    		return;
    	}
	}
	
    public final void save (Statement st) throws Exception
    {   
    	parse ();
    	
    	String fieldStr = "";
    	String valueStr = "";
    	for (Entry<String, Field> entry : _field.entrySet())
    	{
    		fieldStr += entry.getKey()   + ",";
    		valueStr += "'" + entry.getValue().get(this) + "',";
    	}
    	
    	fieldStr = fieldStr.substring(0, fieldStr.length()-1);
    	valueStr = valueStr.substring(0, valueStr.length()-1);

    	System.out.println ("INSERT INTO " + _table.value() + " (" + fieldStr + ") SELECT "
     		   + valueStr + " "
               +"WHERE NOT EXISTS"
               +"("
               +    "SELECT 1 FROM " + _table.value () + " WHERE id = " + _field.get(_pkey.value ()).get (this)
               +")"
    	);
    	
        st.execute ("INSERT INTO " + _table.value() + " (" + fieldStr + ") SELECT "
        		   + valueStr + " "
                   +"WHERE NOT EXISTS"
                   +"("
                   +    "SELECT 1 FROM " + _table.value () + " WHERE id = '" + _field.get(_pkey.value ()).get (this) + "'"
                   +")"
        );
    }
}