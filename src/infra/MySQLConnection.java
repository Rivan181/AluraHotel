package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
	public static void main(String[] args) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hotel-alura";
            String username = "root";
            String password = "Classic6317";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }
	

}
