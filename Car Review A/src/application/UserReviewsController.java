package application;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserReviewsController extends HomePaneMaster {
	
	private static Account acct = LogInController.getInstance().getAccount();
	private static String table = "carreview.car_user_reviews";
	String sql;
	
	@FXML
	private Button addReviewsButton;
	
	@FXML
	private Button refreshButton;
	
	@FXML
	private TextField commentAddField;
	
	@FXML
	private TextField carRatingAddField;
	
	@FXML
	private TextField carIdAddField;
	
	public UserReviewsController() {
		super("SELECT car_id, review, comment  FROM " + table + "  WHERE user_id =" + acct.getId()
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
	
	public void addReview() {
		
		try {
			Review userReview = new Review(
					this.carIdAddField.getText(),
					Integer.valueOf(this.carRatingAddField.getText()),
					this.commentAddField.getText()
					);
			
			
			this.sql = 
					"INSERT INTO "  
					+ table 
					+ " (`car_id`, `user_id`, `review`, `comment`)"
					+ " VALUES (\' "+ userReview.getCarId()	+ " \',"
								+ " \' " + acct.getId() +"\',"
								+ " \' " + userReview.getCarReview() + "\',"
								+ " \' " + userReview.getComment() + "\' "
								+ ")";
			
			if (super.runQueryUpdate(sql) < 0)
				return;
			this.refreshButton.fire();
			
		}
		catch(Exception e) {
			/*
			 TODO:
			Review value is invalid:
				Not a number
				0 > x > 5
			
			CardId is invalid
				Not a number
				Not found(sql)
			
			Comment section is greater than
			
			SQL errors	
				Input multiple review for the same car
		 	
			  */
			 
			e.printStackTrace();
			return;
		}
			
	}
	
	
	
}
 class Review{
	String carId;
	int carReview;
	String comment;
	
	public Review(String carId, int carReview, String comment) throws Exception {
		
		this.carReview = carReview;
		this.carId = carId;
		this.comment = comment;
	}
	
	
	public String getCarId() {
		return carId;
	}


	public void setCarId(String carId) {
		this.carId = carId;
	}


	public int getCarReview() {
		return carReview;
	}


	public void setCarReview(int carReview) {
		this.carReview = carReview;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	
	
}