package vowel_detection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	private Connection _connection = null;
	
	public Database (String user, String password, String database)
	{
		try   { Class.forName("org.postgresql.Driver"); }
		catch (ClassNotFoundException e)
		{
			System.err.println("pSQL JDBC Driver missing");
			e.printStackTrace();
			return;
		}
		
		try
		{
			_connection = DriverManager.getConnection (
				"jdbc:postgresql://127.0.0.1:5432/" + database,
				user, password
			);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return;
		}
 
		if (_connection == null)
			System.err.println("Connection: null");
	}
	
	public Statement statement () throws SQLException
	{
		return _connection.createStatement();
	}
	
	public Connection connection ()
	{
		return _connection;
	}
}