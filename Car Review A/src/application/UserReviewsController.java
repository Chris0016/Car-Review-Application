package application;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class UserReviewsController extends HomePaneMaster {
	
	private static Account acct = LogInController.getInstance().getAccount();
	
	@FXML
	private Button addReviewsButton;
	
	@FXML
	private Button refreshButton;
	
	public UserReviewsController() {
		super("SELECT car_id, review, comment  FROM carreview.car_user_reviews WHERE user_id =" + acct.getId()
		);
	}
	
	/*
	 	The table for the user reviews is subject to be much more dynamic in comparison to
		 the table for the car models(cars aren't going to be added/deleted as often). 
		 Therefore, the refresh class needs to be reloaded from the ground up because is not just about
		 changing the contents, but the number of contents. 
	 */
	
	@Override
	public void refresh(ActionEvent event) {
		super.gridPane.getChildren().clear();
		super.setGridPane();
		System.out.println("Refreshing...");
		super.closeDB();		
	}
	
}
