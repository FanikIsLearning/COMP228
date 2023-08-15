package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
	 // Create a static method to get a database connection
	 public static Connection getDatabaseConnection() {
	        Connection connection;
	        try {
	            System.out.println("> Start Program ...");
	            // Load the Oracle JDBC driver class (for Oracle databases)
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            System.out.println("> Driver Loaded successfully.");
	            // Establish a connection to the Oracle database
	            //connection = DriverManager.getConnection("jdbc:oracle:thin:@199.212.26.208:1521:SQLD", " COMP228_M23_sy_8", "password");
	            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
	            System.out.println("Database connected successfully.");
	         // Create a statement object to execute SQL queries on the connected database
	            Statement statement = connection.createStatement();
	            // Return the established connection
	            return connection;
	        } catch (Exception e) {
	            // If any exception occurs during the process, print the stack trace
	            e.printStackTrace();
	        }
	        // If an exception occurred or the connection couldn't be established, return null
	        return null;
	    }
}
