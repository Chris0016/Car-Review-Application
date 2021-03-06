package application;

import javafx.fxml.FXML;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserReviewsController extends HomePaneMaster {
	
	private  Account acct = LogInController.getInstance().getAccount();
	private static String table1 = "carreview.car_user_reviews";
	private static String table2 = "carreview.car_temp";
	private String sqlHolder;
	
	
	private Integer userId = acct.getId();
	private String carId; //String for error throwing purposes
	
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
	
	@FXML
	private TextField carIdDelField;
	
	public UserReviewsController()  {
		super(4);
		super.setSQL(
				"SELECT "  + table1 + ".car_id, car_model, review, comment "
				+ " FROM "+  table1  + "," +  table2 
				+ " WHERE " +  table1 +  ".user_id = " + userId 
				+ " And  "+table1+".car_id = "+table2+".car_id"
				
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
		super.setStatusMessage("");
		super.gridPane.getChildren().clear();
		super.setGridPane();
		System.out.println("Refreshing...");
		super.closeDB();	
		super.setStatusMessage("Success Refreshing");
	}
	
	public void addReview() {
		super.setStatusMessage("");
		String carRevHolder = "";
		String carIdHolder = "";
		try {
			carRevHolder = this.carRatingAddField.getText().trim();
			carIdHolder = this.carIdAddField.getText().trim();
			Review userReview = new Review(
					Integer.valueOf(carIdHolder),
					Integer.valueOf(carRevHolder), //May return a non integer
					this.commentAddField.getText().trim()
					);
			
			
			this.sqlHolder = 
					"INSERT INTO "  
					+ table1 
					+ " (`car_id`, `user_id`, `review`, `comment`)"
					+ " VALUES (\' "+ userReview.getCarId()	+ " \',"
								+ " \' " +  userId +"\',"
								+ " \' " + userReview.getCarReview() + "\',"
								+ " \' " + userReview.getComment() + "\' "
								+ ")";
			
			//If update is successful
			if (super.runQueryUpdate(sqlHolder)) {
				this.refreshButton.fire();
				super.setStatusMessage("Review Added Successfully ");
			} 
			
			return;
			
		} catch(Exception e) {
			super.setStatusMessage("Errror Adding Review");
			System.out.println(e);
			String message = e.getMessage();
			if (e instanceof IllegalStateException) {
				
				if (message.contentEquals("Review Must be in range of [0-5] Inclusive")) 
					super.alertConfigs.carReviewOutOfRange.showAndWait();
				
				else if (message.contentEquals("Comment Cannot be longer than 200 Characters")) 
					super.alertConfigs.carCommentInvalid.showAndWait();
				
				else if (message.contentEquals("Car Id must be greater than 0"))
					super.alertConfigs.carIdLessThanZero.showAndWait();
					
			}
			
			else if (e instanceof NumberFormatException) 
			{
				if ( (isDecimal(carIdHolder)))
					super.alertConfigs.decimalCarId.showAndWait();
				else
					super.alertConfigs.notANumber.showAndWait();
					
			}		
			
			else if (e instanceof SQLIntegrityConstraintViolationException ) {
				if (message.startsWith("Duplicate")) 
					super.alertConfigs.duplicateReview.showAndWait();
				else
					super.alertConfigs.carIdNotFound.showAndWait();
				
			}				
			else {			
				super.alertConfigs.unknownError.showAndWait();
				e.printStackTrace();
			}
			
			return;
		}
			
	}
	private boolean isDecimal(String in) {
		try {
			Double.parseDouble(in);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public void deleteReview() {
		
		this.carId = carIdDelField.getText(); // carId must be initialized dynamically. 
		int holderCarId;
		try {
		 holderCarId = Integer.valueOf(carId) ;
		
		if (holderCarId < 0) {
			super.alertConfigs.carIdLessThanZero.showAndWait();
			return;
		}
				
		this.sqlHolder = "DELETE FROM "
				+ table1
				+ " WHERE (`user_id` =\' "+ userId + "\') "
				+ "and (`car_id` =\' "+carId+ "\');";
		
		super.runQueryUpdate(sqlHolder);
		this.refreshButton.fire();
		
		} catch(Exception e) {
			if (e instanceof NumberFormatException)
				super.alertConfigs.notANumber.showAndWait();
			
			else if (e instanceof SQLIntegrityConstraintViolationException)
				super.alertConfigs.carIdNotFound.showAndWait();
			
			else {
				super.alertConfigs.unknownError.showAndWait();
				e.printStackTrace();
				
			}	
		}			
	}
	
}

 class Review{
	int carId;
	int carReview;
	String comment;
	
	public Review(int carId, int carReview, String comment) throws IllegalArgumentException {
		
		this.carReview = validateCarReview(carReview);
		this.carId = validateCarId(carId); //If id is not an int it will get handled in the addReview()
		this.comment = validateComment(comment);
	}
	
	private int validateCarReview(int in) throws IllegalStateException {
		if (in < 0 || in > 5)
			throw new IllegalStateException("Review Must be in range of [0-5] Inclusive");
		return in;
	}
	
	private String validateComment(String in) throws IllegalStateException {
		if (in.length() > 200)
				throw new IllegalStateException("Comment Cannot be longer than 200 Characters");
		return in;
	}
	
	private int validateCarId(int in) {
		if (in < 0)
			throw new IllegalStateException("Car Id must be greater than 0");
		return in;
	}
	
	public int getCarId() {
		return carId;
	}

	public int getCarReview() {
		return carReview;
	}

	public String getComment() {
		return comment;
	}


}
 
