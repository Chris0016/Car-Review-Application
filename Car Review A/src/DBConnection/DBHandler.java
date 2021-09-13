package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;

import application.AlertConfigs;

public class DBHandler extends Configs{

	private Connection dbConnection;
	AlertConfigs alertConfigs;
	
	public DBHandler() {
		alertConfigs = new AlertConfigs();
	}
	
	public Connection getConnection(){
		String connectionString = "jdbc:mysql://" + Configs.dbhost + ":" + Configs.dbport + "/" + Configs.dbname;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");	
		}catch(Exception e){
			System.out.println("Error in DBHandler Class: ");
			e.printStackTrace();
			System.exit(1);

			
		}
		try{
			dbConnection = DriverManager.getConnection(connectionString, Configs.username, Configs.password);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in DB Connection");
			alertConfigs.connectionError.showAndWait();
			
		}
		
		
		return dbConnection;
	}
}
