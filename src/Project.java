import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import java.util.Scanner;

/**
 * The Project class contains all methods related to project information.
 * @author Minal
 * @version 2.0
 */
public class Project {
	
	// Method to update deadline
	/**
	 * Method to update a projects deadline.
	 * Requests new deadline in the format DD/MM/YYYY.
	 * Deadline for the project is updated in the database.
	 * 
	 * @param projectNum 			is the project number of the project that will be updated
	 * @param statement 			is for sending queries to the database
	 * @exception 						if date format is incorrect
	 * @throws SQLException
	 */
	public static void deadlineUpdate(int projectNum, Statement statement) throws SQLException{
		Date newDeadline = null;
		// While loop to ensure date input is correct
		while (true) {
			System.out.println("\nEnter the new deadline (Format: YYYY-MM-DD):");
			Scanner input = new Scanner(System.in);
			String dueDate = input.nextLine();
			
			try {
				// Convert project deadline to date format
				newDeadline = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
				break;
			} catch (ParseException e) {
				System.out.println("Date format incorrect. Enter it again.");
			}	
		}
		// Date constructor
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Update the database
		int rowsAffected = statement.executeUpdate(
				"UPDATE projects SET deadline='" + dateFormat.format(newDeadline) + "' WHERE proj_num="
				+ projectNum);
		System.out.println("Query complete, " + rowsAffected + " rows updated.");
		System.out.println("\nDeadline Updated.");
		System.out.println(newDeadline);
	}
	
	// Method to update total paid
	/**
	 * Method to update the total paid.
	 * The user is requested to enter the new total paid.
	 * If 0 is entered, the request is cancelled.
	 * If the amount is less than the total paid or greater than the total cost, the program will 
	 * request the user to enter it again.
	 * The projects total paid will be update in the database if the input is valid.
	 * 
	 * @param projectNum 			is the project number of the project that will be updated
	 * @param statement 			is for sending queries to the database
	 * @exception 						if the user enter an invalid character for type double
	 * @throws SQLException 
	 */
	public static void totalPaidUpdate(int projectNum, Statement statement) throws SQLException {
		while (true) {
		  try {	
				// Request the amount paid
				System.out.println("\nEnter the new total that has been paid to date or enter 0 to cancel:");
				Scanner input = new Scanner(System.in);
				float newTotalPaid = input.nextFloat();
				// Get existing total cost and total paid values
				ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE proj_num="
						+ projectNum);
				results.next(); 
				float totalCost = results.getFloat("total_cost");
				float totalPaid = results.getFloat("total_paid");
				
				/* If and Else If statements to determine if
				 * request is cancelled
				 * if amount entered is greater than project cost
				 * if amount entered is smaller than amount paid
				 * else total paid is updated in the data */
			  if (newTotalPaid == 0) {
			  	System.out.println("\nRequest cancelled.");
			  	break;
			  } else if (newTotalPaid > totalCost) {
					System.out.println("\nAmount entered is greater than total cost. Try again.");
					continue;
				} else if (newTotalPaid <= totalPaid) {
					System.out.println("\nAmount entered is less than total paid. Try again.");
					continue;
				} else {
					int rowsAffected = statement.executeUpdate(
							"UPDATE projects SET total_paid=" + newTotalPaid + " WHERE proj_num="
							+ projectNum);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					System.out.println("\nTotal Paid Updated.\n");
					break;
				}
		  } catch (Exception e) {
			System.out.println("Error - Input is incorrect type.");
			continue;
		  }
		}
	}
	
	// Method to add projects
	/**
	 * This method is used to add a new project to the database.
	 * The user is requested to enter the details of the project to create a new project.
	 * 
	 * @param statement 			is for sending queries to the database
	 * @return 								a new project object containing the details inputed.
	 * @throws SQLException
	 */
	public static void AddProject(Statement statement) throws SQLException {

		int rowsAffected;
		
		// Declare Project Variables		
		int number;
		String projectName;
		String type;
		String projectAddress;
		String erfNumber;
		float totalCost;
		float totalPaid;
		Date deadline;
		String status;
		Person customer;
		Person architect;
		Person contractor;
		Person structuralEng;
		Person projManager;
		
		Scanner input = new Scanner(System.in); // Declare Scanner
		
		// Create project object
		// Display instructions
		System.out.println("To add a new project, please enter the CUSTOMER'S details.\n");
		
		// Call addperson method from Person class
		customer = Person.AddPerson("customers", "CS", statement);

		System.out.println("\nNow enter the PROJECT details.\n");
		
		// Project Information input
		// Project number generated automatically using the idNum method
		number = Integer.parseInt(idNum("projects", "", statement)); 
		System.out.println("Project Number Generated:\n" + number);
		
		System.out.println("Project Name:");
		input = new Scanner(System.in);
		projectName = input.nextLine();
				
		System.out.println("Project Type (e.g. House, Apartment Block, Store, etc):");
		input = new Scanner(System.in);
		type = input.nextLine();
		
		System.out.println("Project Address:");
		input = new Scanner(System.in);
		projectAddress = input.nextLine();
		
		System.out.println("ERF Number:");
		input = new Scanner(System.in);
		erfNumber = input.nextLine();
		
		System.out.println("Project Cost:");
		input = new Scanner(System.in);
		totalCost = input.nextFloat();
		
		System.out.println("Amount Paid:");
		input = new Scanner(System.in);
		totalPaid = input.nextFloat();
		
		// While loop to ensure date input is correct
		while (true) { 
			System.out.println("Deadline (Format: YYYY-MM-DD):");
			input = new Scanner(System.in);
			String dueDate = input.nextLine();
			
			try {
				// Convert project deadline to date format
				deadline = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
				break;
			} catch (ParseException e) {
				System.out.println("Date format incorrect. Enter it again.");
			}	
		}
		// For new project, status automatically set to "Incomplete"
		status = "Incomplete";	
		
		// If no project name assigned, use type and customers surname
		if(projectName == "") {
			projectName = type + " " + customer.getSurname();
		}
		
		// Add architect, contractor, structural engineer and project manager using addperson
		System.out.println("\nNow enter the ARCHITECT'S details.\n");
		architect = Person.AddPerson("architects", "A", statement);
		
		System.out.println("\nNow enter the CONTRACTOR'S details.\n");
		contractor = Person.AddPerson("contractors", "CT", statement); 
		
		System.out.println("\nNow enter the STRUCTURAL ENGINEER'S details.\n");
		structuralEng = Person.AddPerson("structural_engineers", "SE", statement); 
		
		System.out.println("\nNow enter the PROJECT MANAGER'S details.\n");
		projManager = Person.AddPerson("project_managers", "PM", statement); 
		
		// Date formatter		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		// Add project to database
		rowsAffected = statement.executeUpdate(
				"INSERT INTO projects VALUES ('" 
							+ number + "', '" 
							+ projectName + "', '"
							+ type + "', '" 
							+ projectAddress + "', '" 
							+ erfNumber + "', " 
							+ totalCost + ", "
							+ totalPaid + ", '" 
							+ dateFormat.format(deadline) + "', '" 
							+ status + "', '"
							+ customer.getID() + "', '" 
							+ architect.getID() + "', '" 
							+ contractor.getID() + "', '" 
							+ structuralEng.getID() + "', '" 
							+ projManager.getID() + "')" 
							);
		System.out.println("Query complete, " + rowsAffected + " rows added.");	
	}
	
	// Method to retrieve list of incomplete projects
	/**
	 * This method displays a list of all incomplete projects.
	 * The method queries the projects table in the database and retrieves projects where the status 
	 * is 'Incomplete'. The project number, name and deadline of each of these projects are displayed.
	 * 
	 * @param statement 			is for sending queries to the database
	 * @throws SQLException
	 */
	public static void incompleteProjects(Statement statement) throws SQLException {
				
		// Display list title and format
		System.out.println("\nList of incomplete projects and their due date:\n"
				+ "\nProject No. - Project Name - Due Date\n");
		// Get project details for incomplete projects and display them
		ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE status ='Incomplete'");
		while(results.next()) {
			System.out.println(
					results.getInt("proj_num") + " - "
					+ results.getString("proj_name") + " - "
					+ results.getDate("deadline"));
		}	
	}
	
	// Method to retrieve list of projects past due date
	/**
	 * This method displays a list of all projects that are past their due dates.
	 * The method queries the projects table in the database and retrieves projects where the deadline 
	 * has passed. The project number, name, deadline and status of each of these projects are 
	 * displayed.
	 * 
	 * @param statement 			is for sending queries to the database
	 * @throws SQLException
	 */
	public static void delayedProjects (Statement statement) throws SQLException {
		
		// Obtain current date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		int i = 0; // Counter of how many projects overdue
		
		// Display list title and format
		System.out.println("\nList of projects past their due date:\n"
				+ "\nProject No. - Project Name - Due Date - Status\n");
		
		// Get project details for delayed projects and display them
		ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE deadline <'"
				+ dateFormat.format(date) +"'");
		while(results.next()) {
			System.out.println(
					results.getInt("proj_num") + " - "
					+ results.getString("proj_name") + " - "
					+ results.getDate("deadline") + " - "
					+ results.getString("status"));
					i++;
		}
		
		// If counter stays 0, no projects overdue
		if (i == 0) { 
			System.out.println("There are no projects past their due date.");
		}
	}
	
	// Method to mark a project as finalized
	/** 
	 * This method finalizes a project.
	 * The method will change the status of the project to "Finalized on YYYY-MM-DD", where 
	 * YYYY-MM-DD is the date on which it was finalized. If the project has not been fully paid for,
	 * an invoice will be generated showing the customers details and the amount outstanding. 
	 * The details of the project will also be saved to a text file "Completed Project - Project Name".
	 * 
	 * @param projectNum 			is the project number of the project that will be updated
	 * @param statement 			is for sending queries to the database
	 * @exception 						thrown if an error occurs in writing to the text file.
	 * @throws SQLException 
	 */
	public static void projectFinalized(int projectNum, Statement statement) throws SQLException {
		
		// Get project information
		ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE proj_num =" + projectNum);
		results.next();
		String projectName = results.getString("proj_name");
		float totalCost = results.getFloat("total_cost");
		float totalPaid = results.getFloat("total_paid");
		String custID = results.getString("cust_id");
		
		// Get customer information
		results = statement.executeQuery("SELECT * FROM customers WHERE cust_id ='" + custID + "'");
		results.next();
		String name = results.getString("name");
		String surname = results.getString("surname");
		String contactNumber = results.getString("contact_num");
		String email = results.getString("email");
		String address = results.getString("address");
		
		// Obtain current date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		// New status
		String statusUpdate = "'Finalized on " + dateFormat.format(date) + "'"; 
		// Update status
		int rowsAffected = statement.executeUpdate(
				"UPDATE projects SET status=" + statusUpdate + " WHERE proj_num="
						+ projectNum);
		System.out.println("Query complete, " + rowsAffected + " rows updated.");
		// If totalCost and totalPaid are equal, no invoice needed
		// If not than create an invoice
		if(totalCost != totalPaid) {
			
			// Create Invoice
			System.out.println("\nCUSTOMER INVOICE\n"
					+ "\nCustomer Name: " + name + " " + surname
					+ "\nContact Number: " + contactNumber
					+ "\nEmail Address: " + email
					+ "\nAddress: " + address
					+ "\n"
					+ "\nTotal Cost:\t\tR" + totalCost
					+ "\nTotal Paid:\t\tR" + totalPaid
					+ "\nTotal Outstanding:\tR" + (totalCost - totalPaid));
			
		// If invoice not needed
		} else {
			System.out.println("\nProject paid in full and marked as finalised.\n");
		}
		
		//Try to open or create output file 
		try {
			//Use formatter to declare new output file in the location 
			Formatter outputFile = new Formatter("Completed Project - " + projectName + ".txt");
			outputFile.format("%s", "\nCOMPLETED PROJECT");
			outputFile.format("%s", displayProject(projectNum, statement));
			outputFile.close();
			// Catch block in case of error writing to file
		} catch (Exception e) {
			System.out.println("Error while trying to write to output file.");
		}
	}
	
	// Method to search a project by number
	/**
	 * Searches for a project by project number.
	 * User is requested to enter a project number. The method queries the projects table in the 
	 * database to find the matching project number. If it does not find the project, it displays a 
	 * message requesting the number again.
	 *   
	 * @param statement 			is for sending queries to the database
	 * @return 								a project number
	 * @exception 						thrown when an invalid character is inputed for type integer
	 * @throws SQLException
	 */
	public static int	projectNumSearch(Statement statement) throws SQLException {
			
		Scanner input = new Scanner(System.in); 
		// While loop to ensure valid input is received
		while (true) {
			// Try block checks for integer
			try { 
				System.out.println("Enter the project number:");
				input = new Scanner(System.in);
				int projectNum = input.nextInt();

				// Search for project, if found query table to display the project 
				ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE proj_num="
						+ projectNum);
				if (results.next() == true) {
					int projectFound = results.getInt("proj_num");
					results = statement.executeQuery("SELECT * FROM projects WHERE proj_num=" + projectNum);
					while (results.next()) {
						System.out.println(
						"\nProject Number: " + results.getInt("proj_num")
						+ "\nProject Name: " + results.getString("proj_name")
						+ "\nProject Type: " + results.getString("proj_type")
						+ "\nProject Address: " + results.getString("proj_add")
						+ "\nEFR Number: " + results.getString("erf_num")
						+ "\nTotal Cost: " + results.getFloat("total_cost")
						+ "\nTotal Paid: " + results.getFloat("total_paid")
						+ "\nDeadline: " + results.getDate("deadline")
						+ "\nStatus: " + results.getString("status")
						);
					}
					return projectFound;
				} else {
					System.out.println("\nProject number does not exist.");
				}	
			// Catch block if input is not an integer
			} catch (Exception e) { 
				System.out.println("Input is not a number. Enter a valid project number.");
			}
		}
	}
	
	// Method to search a project by name
	/**
	 * Searches for a project by project name.
	 * User is requested to enter a project name. The method queries the projects table in the 
	 * database to find the matching project name. If it does not find the project, it displays a 
	 * message requesting the name again.
	 *   
	 * @param statement 			is for sending queries to the database
	 * @return 								a project number
	 * @throws SQLException
	 */
	public static int projectNameSearch(Statement statement) throws SQLException {

		Scanner input = new Scanner(System.in);
		// While loop to ensure valid input is received
		while (true) {
			System.out.println("Enter the project name:");
			input = new Scanner(System.in);
			String projectName = "'" + input.nextLine() + "'";

			// Search for project, if found query table to display the project 
			ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE proj_name=" 
					+ projectName);
			if (results.next() == true) {
				int projectFound = results.getInt("proj_num");
				results = statement.executeQuery("SELECT * FROM projects WHERE proj_name=" + projectName);
				while (results.next()) {
					System.out.println(
					"\nProject Number: " + results.getInt("proj_num")
					+ "\nProject Name: " + results.getString("proj_name")
					+ "\nProject Type: " + results.getString("proj_type")
					+ "\nProject Address: " + results.getString("proj_add")
					+ "\nEFR Number: " + results.getString("erf_num")
					+ "\nTotal Cost: " + results.getFloat("total_cost")
					+ "\nTotal Paid: " + results.getFloat("total_paid")
					+ "\nDeadline: " + results.getDate("deadline")
					+ "\nStatus: " + results.getString("status")
					);
				}
				return projectFound;
			// If project not found	
			} else {
				System.out.println("\nProject name does not exist.");
			}
		}
	}
		
	// Method to select a project from list of all projects
	/**
	 * Creates a list of all the project allowing the user to select a project.
	 * A numbered list of all the projects is created and using the integerInput() method, the user 
	 * selects a project.
	 * 
	 * @param statement 			is for sending queries to the database
	 * @return 								a project number
	 * @throws SQLException
	 * @see integerInput()
	 */
	public static int projectSelect(Statement statement) throws SQLException{
		// String builder
		String string = "\nPROJECT SELECTION MENU\n\n"
				+ "Enter the relevant number to select the project to be updated from the list below:"
				+ "\n\n"; 
		
		// Get project numbers and names from database and create string for integerInput method
		ResultSet results = statement.executeQuery("SELECT * FROM projects");
		while (results.next()) {
			string = string 
				 + results.getInt("proj_num") + " :\t" + results.getString("proj_name") + "\n";
		}
		// Get number of projects to input into integerInput method
		results = statement.executeQuery("SELECT COUNT(1) AS count FROM projects");
		results.next();
		
		// Request which project to update
		int projectSelection = ProjectManagement.integerInput(string, results.getInt("count")); 
		
		// Show project name of selected project
		results = statement.executeQuery("SELECT * FROM projects WHERE proj_num =" + projectSelection); 
		results.next();
		System.out.println(results.getString("proj_name") + " Selected.");
		return projectSelection;
	}
	
	// Method to create ID numbers for projects, customers, etc.
	/**
	 * Creates ID numbers for projects and role players.
	 * The ID numbers are unique identifiers to be used in the tables.
	 * The method checks how many non null rays exist to determine the next number. The number is then
	 * attached to the role players identifier (for example CS for customer, A for architect, etc.)
	 * 
	 * @param tableName 			name of the table for which a new ID is created
	 * @param identifier 			used to identify different role players
	 * @param statement 			is for sending queries to the database
	 * @return 								string ID number for the new project or role player
	 * @throws SQLException
	 */
	public static String idNum(String tableName, String identifier, Statement statement) throws SQLException {
		// Get the number of non null rows in the affected table	
		ResultSet result = statement.executeQuery("SELECT COUNT(1) AS count FROM " + tableName);
		result.next();
		// Create ID number by joining the identifier to the number of rows plus 1
		String id = identifier + (result.getInt("count") + 1);
		return id;
	}
	
	// Method to display projects
	/**
	 * Creates a string to displays a projects details including all role players information.
	 * The method queries the database to obtain the project information and then uses the
	 * displayPerson method to obtain each role players information. The information is then saved to 
	 * a string variable.
	 * 
	 * @param projNum 				project number of the project that will be displayed.
	 * @param statement 			statement is for sending queries to the database
	 * @return 								a string containing the project information
	 * @throws SQLException
	 * @see displayPerson()
	 */
	public static String displayProject(int projNum, Statement statement) throws SQLException {
		// Get project information
		ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE proj_num =" + projNum);
		String output = "";
		String custID = "";
    String archID = "";
    String contID = "";
    String sEngID = "";
    String pManID = "";
    
		while (results.next()) {
			output = "\nProject Details:";
	    output += "\n\nProject Number: " + results.getInt("proj_num");
	    output += "\nProject Name: " + results.getString("proj_name");
	    output += "\nProject Type: " + results.getString("proj_type");
	    output += "\nProject Address: " + results.getString("proj_add");
	    output += "\nERF Number: " + results.getString("erf_num");
	    output += "\nTotal Cost: R" + results.getFloat("total_cost");
	    output += "\nTotal Paid: R" + results.getFloat("total_paid");
	    output += "\nDeadline: " + results.getDate("deadline");
	    output += "\nStatus: " + results.getString("status");
	    // Get role players ID numbers
	    custID = results.getString("cust_id");
	    archID = results.getString("arch_id");
	    contID = results.getString("cont_id");
	    sEngID = results.getString("s_eng_id");
	    pManID = results.getString("proj_man_id");
		}
		// Call displayPerson method to add role players information to the string
		output += "\n\nCustomer Details:\n"
    	+ Person.displayPerson("customers", "cust_id", custID, statement);
    output += "\n\nArchitect Details:\n" 
    	+ Person.displayPerson("architects", "arch_id", archID, statement);
    output += "\n\nContractor Details:\n" 
    	+ Person.displayPerson("contractors", "cont_id", contID, statement);
    output += "\n\nStructural Engineer Details:\n" 
    	+ Person.displayPerson("structural_engineers", "s_eng_id", sEngID, statement);
    output += "\n\nProject Manager Details:\n" 
    	+ Person.displayPerson("project_managers", "proj_man_id", pManID, statement);
		return output;
	}
}

