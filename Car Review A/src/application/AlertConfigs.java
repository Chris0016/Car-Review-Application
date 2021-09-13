package application;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class AlertConfigs {

	final Alert underAge = //new Alert(AlertType.WARNING, "Invalid Age: Must be over 18", ButtonType.OK);
		makeAlert("Invalid Age", "Invalid Age: Must Be Over 18" , "You must be over 18 to sign up for this App",AlertType.WARNING);
	
	final Alert notANumber = 
		makeAlert("Invalid Age", "Invalid Age: Not A Number", "You must input a number (> 18 )", AlertType.WARNING);
	
	final Alert incompleteForm = 
		makeAlert("Form Not Complete", "Please Complete all the Blanks", "To proceed all blanks must be filled", AlertType.WARNING);
	
	final Alert passwordMismatch = 
		makeAlert("Passwords Do Not Match", "The Passwords You Have Entered Do Not Match", "To proceed the passwords must match", AlertType.WARNING);
	
	final Alert weakPassword =
		 makeAlert("Weak Password", "Your pasword does not match the required criteria", 
		"\n Contains at least 8 characters and at most 20 characters"
		+"\n Contains at least one digit."
		+"\n Contains at least one upper case alphabet."
		+"\n Contains at least one lower case alphabet."
		+"\n Contains at least one special character which includes !@#$%&*()-+=^"
		+"\n Doesn’t contain any white space.", AlertType.WARNING);


	final Alert usernameTaken = 
		makeAlert("Username Taken", "The Username you have Chosen is Already Taken", "Please try another username", AlertType.WARNING);

	public final Alert connectionError = 
		makeAlert("Unable to Connect","Unable to Connect to External Server", "Please Check your Wifi Connection and try again", AlertType.ERROR ); 

	final Alert unknownError = 
		makeAlert("Unknown Error", "An Unknown Error Has Occured", "Please try again or restart if necessary", AlertType.ERROR);

	final Alert accountCreated = 
		makeAlert("Account Created", "Your account Has Been Successfully Created", "You can now log in using your credentials", AlertType.INFORMATION);
	
	final Alert invalidCredentials = 
		makeAlert("Invalid Credentials", "The Username or Password is Incorrect", "Please try a different username or password combination", AlertType.ERROR);
		
	
	public void showUnknownError() {
		System.out.println("An error has Occured: ");
		this.unknownError.showAndWait();	
	}
	
	private Alert makeAlert(String title, String headerText,  String contentText, Alert.AlertType alertType){
		Alert genericAlert = new Alert(alertType);
		genericAlert.setTitle(title);
		genericAlert.setHeaderText(headerText);
		genericAlert.setContentText(contentText);

		return genericAlert;

	}

}