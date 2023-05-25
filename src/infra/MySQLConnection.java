package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection{
	
	private static final String Controlador = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/hotel-alura";
	private static final String username = "root";
	private static final String password = "Classic6317";
	Connection connection = null;
	
	static {
		try {
			  Class.forName(Controlador);
		} catch (ClassNotFoundException e) {
			System.out.println("Connection failed");
		    e.printStackTrace();
		}
	}

  public Connection Conexion () throws ClassNotFoundException{
        try {
            Class.forName(Controlador);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
		return connection;
        
    }
   
}

