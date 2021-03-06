package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import DBConnection.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LogInController implements Initializable{

	private Parent root;
	private Scene scene;
	private Stage stage;
	
	
	private Connection connection;
	private DBHandler handler;
	private Statement stmt;
	private String sql;
	private ResultSet rs;


 	private String username = "";
 	private String password = "";
 	
 	private AlertConfigs alertConfigs;

 	private Account acct;
 	private static LogInController instance;
 	
 	@FXML
 	private Button signUpButton;

 	@FXML
 	private Button logInButton;

 	@FXML
 	private TextField logInUsernameTextField;
 	
 	@FXML
 	private PasswordField passwordTextField;


 	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		handler = new DBHandler();
		alertConfigs = new AlertConfigs();
		acct =new Account();
	}
	
	public LogInController(){
		instance = this; 
	}

	public static LogInController getInstance(){
		return instance;
	}
	
	public Account getAccount() {
		return acct;
	}
	
	private void loadVariables() {
				username = logInUsernameTextField.getText();
				password = passwordTextField.getText();
	
	}

	
	public void logIn(ActionEvent event){
		loadVariables();
		if (hasValidCredentials()){
			try {
				System.out.println("Loging Into Main");
				root = FXMLLoader.load(getClass().getResource("/fxml/HomePage.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Internal Error In Log In");
			}

		}
	}

	public void signUp(ActionEvent event){
		
		try {
		 root = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private boolean hasValidCredentials() {
		//Connect to data base and check if user name is not already taken 
		
		System.out.println("Checking Username...");
		if (username.equals("") || password.equals("")){
			System.out.println("Form Incomplete");
			alertConfigs.incompleteForm.showAndWait();
			return false;	
		} 

		try{
			connection = handler.getConnection();
			System.out.println("Connecting to database...");
			
			stmt = connection.createStatement();

			 
			sql = "SELECT  user_id, name, lastname, age, favoritecar " +
            "FROM accounts "+
            "WHERE username =\'"+ username + "\'"
            + "AND password =\'"+ password + "\'";
			
			
			rs  = stmt.executeQuery(sql);
			
			if(!rs.next()){
				//The query did not retrieve any row
				System.out.println("Username-password  combination not found");
				alertConfigs.invalidCredentials.showAndWait();
				return false;
			}
			
			initializeAcct();
			
			rs.close();
   			stmt.close();
			connection.close();
			System.out.println("Connection Closed...");

		}catch(Exception e){
			if (e instanceof SQLException)
				alertConfigs.connectionError.showAndWait();
			else
				alertConfigs.unknownError.showAndWait();

			e.printStackTrace();
			
			System.out.println("Error in validating user name");
		}
		return true;
	}
	
	private void initializeAcct() {
		try {
			acct.setId(Integer.parseInt(rs.getString(1)));
			acct.setName(rs.getString(2));
			acct.setLastName(rs.getString(3));
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error trying to initialize accnt class");
			e.printStackTrace();
			alertConfigs.unknownError.showAndWait();
		}
		
	}
	
	
	
	
}


