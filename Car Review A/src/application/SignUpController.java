package application;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.regex.*;
import DBConnection.DBHandler;

public class SignUpController implements Initializable{
	private Parent root;
	private Stage stage;
	private Scene scene;
	

	private Connection connection;
	private DBHandler handler;
	private PreparedStatement pst;
	private Statement stmt;
	private String sql;
	private ResultSet rs;

	private AlertConfigs alertConfigs;

	
	private Hashtable<String, String> form;
	private int ageNum;
	private String holder;
	
	@FXML
	 private TextField nameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField password1TextField;

	@FXML 
	private TextField password2TextField;

	@FXML
	private TextField ageTextField;

	@FXML
	private TextField favCarTextField;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private Button returnToLogInButton;
	
	@FXML
	private Button signUpButton;

	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBHandler();
		alertConfigs = new AlertConfigs();
		form = new Hashtable<String, String>(6);
		
	}
	
	private void loadVariables() {
		
		form.put("name", nameTextField.getText());
		form.put("last name", lastNameTextField.getText());
		form.put("age", ageTextField.getText());
		form.put("favorite car", favCarTextField.getText() );
		form.put("username", usernameTextField.getText());
		form.put("password1", password1TextField.getText());
		form.put("password2", password2TextField.getText());
	}
	
	public void returnToLogin(ActionEvent event){
		try{
			root = FXMLLoader.load(getClass().getResource("/fxml/LogIn.fxml"));
	 		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void signUp(){
		
		loadVariables();
		//System.out.println(form);
		if (emptyFieldsExist()){
			alertConfigs.incompleteForm.showAndWait();
			return;
		}
		
		//Check for valid credentials if true then try to save data: when both success then go back to login
		if (validCredentials() && saveDataToServer()) {
			alertConfigs.accountCreated.showAndWait();
			returnToLogInButton.fire();
			System.out.println("Success Creating User");	
		}
	}
	
	//	 Returns true if the data was saved to the server successfully and false otherwise.
	private boolean saveDataToServer() {
		
		System.out.println("Connecting to database...");
		try {
		sql = "INSERT INTO accounts(name, lastname, age, favoritecar, username, password)"
						+ "values(?, ?, ? , ?, ?, ?)";
		connection = handler.getConnection();
		System.out.println("Connected to database");
		
		pst = connection.prepareStatement(sql);
	
		pst.setString(1, form.get("name"));
		pst.setString(2, form.get("last name"));
		pst.setString(3, form.get("age"));
		pst.setString(4, form.get("favorite car"));
		pst.setString(5, form.get("username"));
		pst.setString(6, form.get("password1"));
		
		pst.executeUpdate();

		connection.close();
		pst.close();
		System.out.println("Closing database...");	
		
		}catch(Exception e) {
			System.out.print(e);
			if (e instanceof SQLException)
				alertConfigs.connectionError.showAndWait(); 
			else
				alertConfigs.unknownError.showAndWait();
			
			e.printStackTrace();
			return false;
		}
		return true;
		
				
	}

	private boolean validCredentials(){
		return (hasValidAge() && hasValidPassword() && hasValidUsername());
	
	}
	
	private boolean hasValidUsername() {
		//Connect to data base and check if user name is not already taken 
		
		System.out.println("Checking Username...");
		
		try{
			connection = handler.getConnection();
			System.out.println("Connecting to database...");
			
			stmt = connection.createStatement();

			sql = "SELECT username " +
            "FROM accounts "+
            "WHERE username =\'"+ form.get("username") + "\'";

			rs  = stmt.executeQuery(sql);

			if(rs.next()) {
				//Query retrieves a username meaning it already exists. 
				alertConfigs.usernameTaken.showAndWait();
				System.out.println("Username Taken");
				return false;
			}
				
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
	
	private boolean hasValidPassword() {
		return (passwordMatches() && strongPassword());
	}

	private boolean passwordMatches(){
		System.out.println("Checking passwords...");
		if (!form.get("password1").equals(form.get("password2"))){
			alertConfigs.passwordMismatch.showAndWait();
			System.out.println("Passwords Do Not Match");
			return false;
		}
		return true;
	}

	private boolean strongPassword(){
    	holder = form.get("password1");

        String regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[!@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
  
        
        Pattern p = Pattern.compile(regex);

        if (holder == null) {
        	alertConfigs.weakPassword.showAndWait();
            return false;
        }
  
        Matcher m = p.matcher(holder);
  
        if(!m.matches()){
        	alertConfigs.weakPassword.showAndWait();
        	return false;
        }
        return true;
    }
	
	private boolean hasValidAge(){
		System.out.println("Checking age...");
		try {
			ageNum = Integer.parseInt(form.get("age"));
			if(ageNum < 0 ) {
				alertConfigs.invalidAge.showAndWait();
				return false;
			}
			else if(ageNum < 18 || ageNum > 110){
				alertConfigs.underAge.showAndWait();
				System.out.println("Age is not valid");
				return false;
			}
		}
		catch(Exception e){
			alertConfigs.invalidAge.showAndWait();
			System.out.println("Age input is not a number");
			return false;
		
		}
		return true;
	}

	private boolean emptyFieldsExist() {
		try{
			for(Entry<String, String> e :  form.entrySet())
				if (e.getValue().equals("")) return true;
		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}
	
	

}


