package application;

public class Account {
	private int id;
	private String name;
	private String lastName;
	
	
	public Account(int id, String name, String lastName) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
	
	}
	
	public Account() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String toString() {
		return id + " " +  name  + " "+ lastName;
	}
	
}
/* 
 	Why:
 		All of the information retrieved from the resultset on log-in is related to each other and belongs together. 
 		Represents user data as one type instead of discrete variables. 
 		
 		Used by the "My reviews section" to retrieve table of user reviews.
 		
 		Read Details.txt for more information.
 		
 		 */
