import java.sql.*;
import java.util.*;

/**
 * Poised Project Management Software Tool
 * This program was developed to assist Poised with managing their projects
 * The program allows the user to add projects, edit project details as well as view incomplete 
 * projects and overdue projects. When a project is completed, the project is marked as finalized 
 * and the details of the project are store in a text file. All information relating to project is
 * stored in a database called PoisePMS.
 * 
 * @author Minal
 * @version 2.0
 */
public class ProjectManagement {

	public static void main(String[] args) {
		
		// Declare Scanner
		Scanner input = new Scanner(System.in);
		
		// Looping structure to keep program running
		while(true) {
			// Try-catch block used to connect to the database
			try {
				// Connect to the poisepms database, 
				Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
					"otheruser",
					"swordfish"
					);
				
				// Create a direct line to the database for running our queries
				Statement statement = connection.createStatement();
				
				// Display Intro Message
				System.out.println("****************************************************************");
				System.out.println("\nPOISED PROJECT MANAGEMENT SYSTEM\n");
		
				// Main Menu		
				int mainMenuSelection = 0;
				System.out.println("****************************************************************");
				
				mainMenuSelection = integerInput("\nMAIN MENU\n\n"
						+ "Enter the relevant number to select an option from the list below:\n\n"
						+ "1 - Add a new project\n"
						+ "2 - Search/View/Update/Finalise a project\n"
						+ "3 - Display a list of incomplete projects\n"
						+ "4 - Display a list of overdue projects\n"
						+ "5 - Quit\n", 5);
				
				// Add a project			
				if (mainMenuSelection == 1) {
					System.out.println("****************************************************************");
					// Calls method to add a new project to the database
					Project.AddProject(statement); 
					
				// View/Update/Finalise Project					
				} else if (mainMenuSelection == 2) {
					System.out.println("****************************************************************");
					int projectSearchMethod = 0;
					// Display menu to select a method to search or select a project
					projectSearchMethod = integerInput("\nPROJECT SEARCH/SELECTION MENU\n\n"
							+ "Enter the relevant number to select an option from the list below:\n\n"
							+ "1 - Search for a project by project number\n"
							+ "2 - Search for a project by project name\n"
							+ "3 - Select a project from a list of all projects saved\n", 3);
					
					// If and else if statements used to determine which search method to use
					// The corresponding method is then called
					int projectUpdate = 0;
					if (projectSearchMethod == 1) { 
						projectUpdate = Project.projectNumSearch(statement);
					} else if (projectSearchMethod == 2) {
						projectUpdate = Project.projectNameSearch(statement);
					} else if (projectSearchMethod == 3) {
						projectUpdate = Project.projectSelect(statement);
					}	
	
					System.out.println("****************************************************************");
					// Display selected project
					System.out.println(Project.displayProject(projectUpdate, statement));
					
					// Project Update Menu				
					while(true) {
						System.out.println("****************************************************************");
						
						int option = 0;
						
						// Display menu
						option = integerInput("\nPROJECT UPDATE MENU\n\n"
								+ "Enter the relevant number to select an option from the list below:\n\n"
								+ "1 - Change the due date of a project\n"
								+ "2 - Update the amount paid for a project\n"
								+ "3 - Update a role players contact details\n"
								+ "4 - Finalise a project\n"
						  	+ "5 - Return to main menu\n",5);
					
						// Change due date
						if(option == 1) {
							System.out.println("****************************************************************");
							// Call update deadline method from Project class
							Project.deadlineUpdate(projectUpdate, statement); 
							// Show the updated project	
							System.out.println(Project.displayProject(projectUpdate, statement)); 
						
						// Update the amount paid
						} else if(option == 2) {
							System.out.println("****************************************************************");
							// Call update total paid method from Project class
							Project.totalPaidUpdate(projectUpdate, statement); 
							// Show the updated project
							System.out.println(Project.displayProject(projectUpdate, statement)); 
						
						// Update Role Players contact details					
						} else if(option == 3) {
							System.out.println("****************************************************************");
							// Call update details method from Person class
							Person.detailsUpdate(projectUpdate, statement); 				
						
						// Mark project as finalised					
					  } else if(option == 4) {
					  	System.out.println("****************************************************************");
					  	// Call finalize project method from Project class
					  	Project.projectFinalized(projectUpdate, statement); // Call finalize project method
							
						} else if (option == 5) {
							// Break Project update menu while loop and return to main menu
							break; 
						}
					}
					
				// Display incomplete projects
				} else if(mainMenuSelection == 3) {
					System.out.println("****************************************************************");
					// Call method to show incomplete projects from Project class
					Project.incompleteProjects(statement); 
					
				// Display overdue projects
				} else if(mainMenuSelection == 4) {
					System.out.println("****************************************************************");
					// Call method to show overdue projects from Project class
					Project.delayedProjects(statement); 
				
				// Quit the program			
				} else if(mainMenuSelection == 5) {
					System.out.println("****************************************************************");
					System.out.println("\nProject Manager Closed.");
					// Close Scanner and database connections
					input.close(); 
					statement.close();
					connection.close();
					// Break Main menu while loop to end program	
				  break; 
				}
			// Catch block in case of database errors
			} catch (SQLException e) {
				System.out.println("An error with the database has occured.");
				continue;
			}
		}
	}
	 	
	/**
	 * Integer Input Method
	 * Method to input an integer to select an item from a list
	 * This method is used to display a list of items for a user to choose from and
	 * take in the users input.
	 * 
	 * @param request 		is a message showing the request and list asking the users to select an option 
	 * 										from the list
	 * @param maxNumber 	is the amount of items in the list
	 * @return 						the integer that was selected by the user
	 */
	public static int integerInput(String request, int maxNumber) {
		while (true) {
		  System.out.print(request);
		
		  // Read in users choice
		  try {
				Scanner input = new Scanner(System.in);  
				// User input integer
				int output = input.nextInt(); 
				// If integer is 0 or greater than items listed
				if (output == 0 || output > maxNumber) { 
				  System.out.println("Error - Invalid input.");
				  continue;
				// Else user input is valid
				} else {  
					return output;
				}
			// If user input is not integer
		  } catch (Exception e) { 
				System.out.println("Error - Invalid input.");
				continue;
		  }
		}
	}


}
