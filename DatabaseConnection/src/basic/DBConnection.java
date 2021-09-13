package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection com = DriverManager.getConnection("jdbc:mysql://localhost:3306/carreviewaccounts", "root", "361.77Mlqs@@" );
		
		System.out.println("Connected to Database");
	}
}
