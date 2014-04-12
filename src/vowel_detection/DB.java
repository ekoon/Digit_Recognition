package vowel_detection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;

public class DB {
	static Connection connection = null;
	public static boolean connect(){
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");
 		try { 
			Class.forName("org.postgresql.Driver");
 		} catch (ClassNotFoundException e) {
 			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace(); 
			return false;
		}	
		System.out.println("PostgreSQL JDBC Driver Registered!");	 
		
		Scanner user_input = new Scanner( System.in );
		String user;
		System.out.print("Enter your user name: ");
		user = user_input.next( );

		String password;
		System.out.print("Enter your password: ");
		password = user_input.next( );
		
 		try {
 			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/testdb", 
					user, password);
 		} catch (SQLException e) {
 			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return false;
		}
 
		if (connection != null) {
			System.out.println("DB connection made!");
			return true;
		} else {
			System.out.println("Failed to make DB connection!");
			return false;
		}
	}
	
	public static boolean disconnect(){
		try { 
			connection.close();
			return true;
 		} catch (SQLException e) {
 			System.out.println("Could not disconnect");
			e.printStackTrace(); 
			return false;
		}	
	}
}