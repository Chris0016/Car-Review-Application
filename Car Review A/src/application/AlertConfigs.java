package application;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class AlertConfigs {

	final Alert underAge = //new Alert(AlertType.WARNING, "Invalid Age: Must be over 18", ButtonType.OK);
		makeAlert("Invalid Age", "Invalid Age: Must Be Over 18" , "You must be over 18 to sign up for this App.",AlertType.WARNING);
	
	final Alert invalidAge  = 
		makeAlert("Invalid Age", "Invalid Age: Not A Valid Number", "You must input a number (> 18 ).", AlertType.WARNING);
	
	final Alert incompleteForm = 
		makeAlert("Form Not Complete", "Please Complete all the Blanks", "To proceed all blanks must be filled.", AlertType.WARNING);
	
	final Alert passwordMismatch = 
		makeAlert("Passwords Do Not Match", "The Passwords You Have Entered Do Not Match", "To proceed the passwords must match.", AlertType.WARNING);
	
	final Alert weakPassword =
		 makeAlert("Weak Password", "Your pasword does not match the required criteria", 
		"\n Contains at least 8 characters and at most 20 characters"
		+"\n Contains at least one digit."
		+"\n Contains at least one upper case alphabet."
		+"\n Contains at least one lower case alphabet."
		+"\n Contains at least one special character which includes !@#$%&*()-+=^"
		+"\n Doesn’t contain any white space.", AlertType.WARNING);


	final Alert usernameTaken = 
		makeAlert("Username Taken", "The Username you have Chosen is Already Taken", "Please try another username.", AlertType.WARNING);

	public final Alert connectionError = 
		makeAlert("Unable to Connect","Unable to Connect to External Server", "Please Check your Wifi Connection and try again.", AlertType.ERROR ); 

	final Alert unknownError = 
		makeAlert("Unknown Error", "An Unknown Error Has Occured", "Please try again or restart if necessary.", AlertType.ERROR);

	final Alert accountCreated = 
		makeAlert("Account Created", "Your account Has Been Successfully Created", "You can now log in using your credentials.", AlertType.INFORMATION);
	
	final Alert invalidCredentials = 
		makeAlert("Invalid Credentials", "The Username or Password is Incorrect", "Please try a different username or password combination.", AlertType.ERROR);
	
	final Alert notANumber=
			makeAlert("Invalid Input", "The Input Is Not a Number", "The Input you have submitted contains characters that are not numbers.", AlertType.WARNING);
	
	final Alert carReviewOutOfRange = 
			makeAlert("Invalid Car Review", "Car Review Value not valid", "Value must be between 0 and 5.", AlertType.WARNING);
	
	final Alert carCommentInvalid =
			makeAlert("Invalid Car Comment", "Car comment must be less than 200 characters ", "The number of characters in the comment cannot exceed 200, please edit your comment.", AlertType.WARNING);
	
	final Alert duplicateReview =
			makeAlert("Invalid Review" , "Review is a duplicate",  "You have already submitted a review for this car. Only one review is allowed per car for a single user.", AlertType.WARNING);
	
	final Alert decimalCarId = 
			makeAlert("Invalid Review", "Car Review Value Must Be A Whole Number", "Decimal values are not accepted, please enter a whole number between 0 and 5. ", AlertType.WARNING);
	
	final Alert carIdNotFound = 
			makeAlert("Invalid Car Id", "Car Id Submitted Was Not Found", "The car Id you have chosen cannot be found, please try again with a different car Id.", AlertType.WARNING);
			
	final Alert carIdLessThanZero = 
			makeAlert("Invalid Car Id", "Car Id Must Be Greater Than Zero", "Please input a value greater than zero for the car Id.", AlertType.WARNING);
	
	
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
