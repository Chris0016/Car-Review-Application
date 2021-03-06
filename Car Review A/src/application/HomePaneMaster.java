package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import DBConnection.DBHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HomePaneMaster   implements Initializable {
	private Connection connection;
	private DBHandler handler;
	private Statement stmt;
	
	private ResultSet rs;
	String sql;
	
	int rowSize;
	int colSize; 
	
	/*
		Useful for  scaling purposes:
		
		The number of columns is hard-coded(3). 
		This super class is intended to be used for the Home and UserReviews controller
		 , so I already know that the number of columns will not change. 
		 
		 To make this class scalable to multiple classes that have different number of 
		 columns. Then simply create constructor to take in colSize number. 
		
		In the case that it becomes dynamic use rs.getColumnCount()
		to initialize variable. place in 
	*/
	
	private Node[][] gridPaneArray = null;
	
	protected AlertConfigs alertConfigs;
	
	// Cannot create a getter() because child class(UserReviewsControl) needs to manipulate the actual gridpane
	@FXML
	 protected GridPane gridPane; 
	
	@FXML
	protected Label statusMessage;
	
	public  HomePaneMaster(String sql,int colSize) {
		this.sql = sql;
		this.colSize =colSize; 
		
	}

	public HomePaneMaster(int colSize) {
		this.sql = "";
		this.colSize =colSize; 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBHandler();
		alertConfigs = new AlertConfigs();

		setGridPane();		
		initializeGridPaneArray();
		 setStatusMessage("");
			
	}
	
	void setSQL(String in) {
		this.sql = in;
	}
	
	/* Explain these two methods */
	void start() {
		handler = new DBHandler();
		alertConfigs = new AlertConfigs();

		run();
	}
	void run() {
		setGridPane();		
		initializeGridPaneArray();
	}
	
   private void initializeGridPaneArray() {
		gridPaneArray = new Node[rowSize][colSize];
		
		System.out.println("Rows: " + rowSize);
		
		ObservableList<Node> gridPaneNodes = this.gridPane.getChildren();
		System.out.println("Gridpane nodes: "+ gridPaneNodes.toString());
		
	       for(Node node : gridPaneNodes)
	       {
	          this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
	       }
	       
	       closeDB();
    }

   	
	protected void setGridPane() {
		if (retrieveResultSet() == false )	{
			return;
		}
		rowSize = calcRowSize();
		try {

			rs.first();
			
			for(int row = 0; row < rowSize; row++) {
				for(int col = 0; col < colSize; col++) {
					gridPane.add(new Label(rs.getString(col+1)), col, row); 				
				}
				rs.next();
			}
	
		} catch (SQLException e) {
			System.out.println(e);
			this.handleExceptions(e);
			return;
		}
	} 
		
	public void refresh(ActionEvent event) {
		 setStatusMessage("");
		retrieveResultSet();	
		try {
			
			rs.first();
			Label node;
			for(int row = 0; row < rowSize; row++ ) {	 
				for (int col = 0; col < colSize; col++) {
					node =(Label) gridPaneArray[row][col];
					node.setText( rs.getString(col+1) );
					
				}
				System.out.println();
				rs.next();
			}
			closeDB();
			 setStatusMessage("Refresh Successfull");
				
		} catch (SQLException e) {
			handleExceptions(e);
			e.printStackTrace();
			 setStatusMessage("Error Refreshing");
		}	
	}
	
	private boolean retrieveResultSet() {
		try {
		System.out.println("SQL value: " + sql);
		this.rs = runQuery(sql);
		
		return rs.next();
		
		} catch(Exception e) {			
			System.out.print(e);
			e.printStackTrace();
			handleExceptions(e);
			 setStatusMessage("Error Retrieving Data");

		}
		return false;
	}
	
	protected ResultSet runQuery(String sqlIn) throws SQLException {
		connection = handler.getConnection();
		System.out.println("Connecting to database...");	
		stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_READ_ONLY);

		return stmt.executeQuery(sqlIn);
       						
	}
	//Returns false (< 0 ) means operation unsuccessful or no updates needed. 
	protected boolean runQueryUpdate(String sqlIn) throws SQLException {
		connection = handler.getConnection();
		System.out.println("Connecting to database...");	
		stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_READ_ONLY);

		return  stmt.executeUpdate(sqlIn) > -1;
       						
	}
	
	protected void closeDB()  {
		try {
			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error in closeDB():\n" + e);	
		}
		System.out.println("Connection Closed...");
	}
	
	
	 int calcRowSize() {
		try {
	           if (rs.last()) 
	        	   return rs.getRow();
	           
		} catch (SQLException e) {
			e.printStackTrace();
			handleExceptions(e);
		}
		return 0; 
	}
	
	 protected void setColSize(int colSize) {
		 this.colSize = colSize;
	 }
	 
	 protected void setStatusMessage(String in) {
		 this.statusMessage.setText(in);
	 }
    private void handleExceptions(Exception e) {
		if (e instanceof SQLException) {
			alertConfigs.connectionError.showAndWait();
			System.out.println("Error SQL Exception");
			e.printStackTrace();

		}
		else {
			alertConfigs.showUnknownError();
			System.out.println("Error in handle exceptions: ");
			 e.printStackTrace();
		}
	}
	
	//For testing purposes only
	
	//-----------------------
	/*
		private void setDummyGridPane() {
		int i = 0;
		while(i < 5) {
			gridPane.add(new Label(String.valueOf(i)), 0, i); 
			gridPane.add(new Label("Hello"), 1, i);
			i++;
		}
	}
	private void printGridPaneArray() {
		for (int i = 0; i < this.gridPaneArray.length; i++) {
			for (int j = 0 ; j < 2; j++)
				System.out.println(gridPaneArray[i][j].toString());
		}
	}
	*/
	//-----------------------
}
