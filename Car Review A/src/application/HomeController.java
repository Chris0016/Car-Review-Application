package application;

/*
 	The HomePaneMaster was not made the controller itself because separating it
 	into two children classes allows for a cleaner elasticity. Meaning, that I can add
 	things like button to the child classes that are individual to them. If I were to make 
 	the super the controller also, then whenever I'd like to add elements to it that 
 	are individual to that controller then the children would inherit those, which doesn't make 
 	much sense to do. 
 */
public class HomeController extends HomePaneMaster{
	
	public HomeController() {
		super("SELECT car_id, car_model, `avg(review)` FROM  car_rating_avg", 3);
	}
	
	
}
