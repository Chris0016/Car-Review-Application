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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePageController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	private Account acct;
		
	@FXML
	private Button signOutButton;
		
	@FXML
	private Label statusLabel;
	
	@FXML	
	private Label hiMessage;
	
	@FXML
	private Label myReviewsButton;
	
	@FXML 
	private AnchorPane holderPane;
	
	AnchorPane home;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		home();
		
		acct = LogInController.getInstance().getAccount();
		hiMessage.setText("Welcome " + acct.getName());
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void myReviews() {
		createPage("/fxml/USERREVIEWS.fxml");
	}
	public void home() {
		createPage("/fxml/HOME.fxml");
	}
	
	public void printAcct() {
		System.out.println(acct.toString());
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

