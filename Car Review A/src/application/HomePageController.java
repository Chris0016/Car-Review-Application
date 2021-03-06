package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePageController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private AlertConfigs alertConfigs;
	
	private Account acct;	
	
	protected AnchorPane home;
	
	@FXML
	private Button signOutButton;
		
	@FXML
	private Button myReviewsButton;
	
	@FXML 
	private AnchorPane holderPane;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		acct = LogInController.getInstance().getAccount();
		alertConfigs = new AlertConfigs();
		home();
		System.out.println("Account ID in HOMEPAGE: " + acct.getId());
	}
	
	public void setNode(Node node) {
		holderPane.getChildren().clear();
		holderPane.getChildren().add((Node)node);
	}
	
	public void createPage(String path) {
		try {
			home = FXMLLoader.load(getClass().getResource(path));
			setNode(home);
		} catch (IOException e) {
			alertConfigs.unknownError.showAndWait();
			e.printStackTrace();
		}
	}
	
	public void myReviews() {
		createPage("/fxml/USERREVIEWS.fxml");
	}
	
	public void home() {
		createPage("/fxml/HOME.fxml");
	}
	
	public void signOut(ActionEvent event){
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
	
}

/*
 	Why:
 	
 		There is another scene within the main Home scene. The sub-scene is the center display and gets controlled by this class. 
 		From this class you can switch between ''Home" and "My Reviews" (forward and back) sections of the program. 
 		
 		Doing it this way simplifies the program and also reduces its size. A different implementation would be to have each section("Home" and "My Reviews") be their own
 		main scene and not a sub-scene. This would mean that each of them would need to have their own buttons to switch and refresh.  In essence, a lot of code would be
 		repeated; Not good design. 
 		
 		The method implemented had this class act as a "Frame" that controls the switching between sub-scenes.
 		
 
 */ 
