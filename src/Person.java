import java.util.Scanner;
import java.sql.*;
/**
 * The Person class creates a person object that stores all the details of each role player related to
 * a project.
 * @author Minal
 * @version 2.0
 */
public class Person {
	
	// Attributes
	/**
	 * String value for person identifier
	 */
	String id;
	/**
	 * String value for person name
	 */
	String name;
	/**
	 * String value for person surname
	 */
	String surname;
	/**
	 * String value for person contact number
	 */
	String contactNumber;
	/**
	 * String value for person email
	 */
	String email;
	/**
	 * String value for person address
	 */
	String address;
	
	// Methods
	// Constructor
	public Person(String id, String name, String surname, String contactNumber, String email, String address) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.contactNumber = contactNumber;
		this.email = email;
		this.address = address;
	}
	
	// toString Method
	/**
	 * To String method creates a string to display the person details
	 * @return String value listing all details
	 */
	public String toString() {
	      String output = "\nID = " + id;
	      output += "\nName: " + name;
	      output += "\nSurname: " + surname;
	      output += "\nContact Number: " + contactNumber;
	      output += "\nEmail Address: " + email;
	      output += "\nAddress: " + address;
	      	   
	      return output;
	}
	
	//Method to retrieve roleplayers name
	/**
	 * Getter for person identifier
	 * @return String id of person
	 */
	public String getID() {
		return id;
	}
	
	// Method to retrieve roleplayers surname
	/**
	 * Getter for person surname
	 * @return String surname of person
	 */
	public String getSurname() {
		return surname;
	}
	
	// Method to add role players
	/**
	 * This method is used to create a new person object and add the person to the database.
	 * The user is requested to enter the details of the person to create a new person. 
	 * @param tableName name of the table to which the person will be added
	 * @param identifier used to identify different role players (e.g. customers, architects, etc.)
	 * @param statement is for sending queries to the database
	 * @return a new person object containing the details inputed.
	 * @throws SQLException
	 */
	public static Person AddPerson(String tableName, String identifier, Statement statement) throws SQLException {
		
		// Declare Scanner
		Scanner input = new Scanner(System.in);
		
		String id = Project.idNum(tableName, identifier, statement);
		System.out.println("ID Number Generated:\n" + id);
		
		// Role Players information input	
		System.out.println("Name:");
		input = new Scanner(System.in);
		String personName = input.nextLine();
		
		System.out.println("Surname:");
		input = new Scanner(System.in);
		String personSurname = input.nextLine();
		
		System.out.println("Contact Number:");
		input = new Scanner(System.in);
		String contactNumber = input.nextLine();
		
		System.out.println("Email:");
		input = new Scanner(System.in);
		String email = input.nextLine();
		
		System.out.println("Address:");
		input = new Scanner(System.in);
		String personAddress = input.nextLine();
		
		// Add new person to the database
		int rowsAffected = statement.executeUpdate(
				"INSERT INTO " + tableName +" VALUES ('" 
						+ id + "', '" 
						+ personName + "', '" 
						+ personSurname + "', '" 
						+ contactNumber + "', '" 
						+ email + "', '" 
						+ personAddress + "')"
						);
		System.out.println("Query complete, " + rowsAffected + " rows added.");
		// Create a new person object using information obtained
		Person NewPerson = new Person(id, personName, personSurname, contactNumber, email, personAddress);
		
		// Method returns a person type
		return NewPerson;
	}
	
	// Method to update person details
	/**
	 * Updates the contact details of a person
	 * The user selects which person and then which details are going to be updated. The user then 
	 * enters the new details which are saved to the database.
	 * @param projectNum is the project number of the project that will be updated
	 * @param statement is for sending queries to the database
	 * @throws SQLException
	 */
	public static void detailsUpdate(int projectNum, Statement statement) throws SQLException {
		
		int rolePlayerSelection = 0;
		int detailsSelection = 0;
		ResultSet results;
		int rowsAffected;
		// Choose which role players details to update
		rolePlayerSelection = ProjectManagement.integerInput("\nSelect which role players details you"
				+ " would like to update.\n"
				+ "1 - Customer\n"
				+ "2 - Architect\n"
				+ "3 - Contractor\n"
				+ "4 - Structural Engineer\n"
				+ "5 - Project Manager\n", 5);
		
		// Choose which type of contact details to update
		detailsSelection = ProjectManagement.integerInput("\nSelect which which contact details you"
				+ " would like to update.\n"
				+ "1 - Contact Number\n"
				+ "2 - Email Address\n", 2);
		
		// Obtain person ID numbers
		results = statement.executeQuery("SELECT * FROM projects WHERE proj_num=" + projectNum);
		results.next(); 
		String custID = results.getString("cust_id");
		String archID = results.getString("arch_id");
		String contID = results.getString("cont_id");
		String sEngID = results.getString("s_eng_id");
		String pManID = results.getString("proj_man_id");
		
		// If contact number is chosen
		if (detailsSelection == 1) {
			
			// Read in new contact number
			System.out.println("\nEnter the contact number:");
			Scanner input = new Scanner(System.in);
			String newContactNumber = input.nextLine();
			
			// Update customers contact number
			if (rolePlayerSelection == 1) {
				// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE customers SET contact_num='" + newContactNumber + "' WHERE cust_id='"
								+ custID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nContact Number Updated.\n");
				System.out.println(displayPerson("customers", "cust_id", custID, statement));
						
			// Update architects contact number
			} else if (rolePlayerSelection == 2) {
				// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE architects SET contact_num='" + newContactNumber + "' WHERE arch_id='"
								+ archID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nContact Number Updated.\n");
				System.out.println(displayPerson("architects", "arch_id", archID, statement));
						
			// Update contractors contact number
		  } else if (rolePlayerSelection == 3) {
		  	// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE contractors SET contact_num='" + newContactNumber + "' WHERE cont_id='"
								+ contID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nContact Number Updated.\n");
				System.out.println(displayPerson("contractors", "cont_id", contID, statement));
			
			// Update structural engineers contact number
		  } else if (rolePlayerSelection == 4) {
		  	// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE structural_engineers SET contact_num='" + newContactNumber + "' WHERE s_eng_id='"
								+ sEngID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nContact Number Updated.\n");
				System.out.println(displayPerson("structural_engineers", "s_eng_id", sEngID, statement));
		  	
		  // Update project managers contact number
		  } else if (rolePlayerSelection == 5) {
		  	// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE project_managers SET contact_num='" + newContactNumber + "' WHERE proj_man_id='"
								+ pManID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nContact Number Updated.\n");
				System.out.println(displayPerson("project_managers", "proj_man_id", pManID, statement));
		  }
		
		// If email address is chosen
		} else if (detailsSelection == 2) {
			
			// Read in new email address
			System.out.println("\nEnter the email address:");
			Scanner input = new Scanner(System.in);
			String newEmail = input.nextLine();
			
			// Update customers email
			if (rolePlayerSelection == 1) {
				// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE customers SET email='" + newEmail + "' WHERE cust_id='"
								+ custID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nEmail Address Updated.\n");
				System.out.println(displayPerson("customers", "cust_id", custID, statement));
						
			// Update architects email
			} else if (rolePlayerSelection == 2) {
				// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE architects SET email='" + newEmail + "' WHERE arch_id='"
								+ archID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nEmail Address Updated.\n");
				System.out.println(displayPerson("architects", "arch_id", archID, statement));
						
			// Update contractor email
			} else if (rolePlayerSelection == 3) {
		  	// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE contractors SET email='" + newEmail + "' WHERE cont_id='"
								+ contID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nEmail Address Updated.\n");
				System.out.println(displayPerson("contractors", "cont_id", contID, statement));
			
			// Update structural engineers email
			} else if (rolePlayerSelection == 4) {
		  	// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE structural_engineers SET email='" + newEmail + "' WHERE s_eng_id='"
								+ sEngID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nEmail Address Updated.\n");
				System.out.println(displayPerson("structural_engineers", "s_eng_id", sEngID, statement));
				
			// Update project managers email
			} else if (rolePlayerSelection == 5) {
				// Update number
				rowsAffected = statement.executeUpdate(
						"UPDATE project_managers SET email='" + newEmail + "' WHERE proj_man_id='"
								+ pManID + "'");
				System.out.println("Query complete, " + rowsAffected + " rows updated.");
				System.out.println("\nEmail Address Updated.\n");
				System.out.println(displayPerson("project_managers", "proj_man_id", pManID, statement));
			}
		}
	}
	
	// Method to display role players	
	/**
	 * Creates a string to displays a persons details.
	 * The method queries the database to obtain the persons information. The information is then 
	 * saved to a string variable.
	 * @param tableName name of the table 
	 * @param columnName name of the ID column in the table
	 * @param id identity number of the person whose information is to be displayed
	 * @param statement is for sending queries to the database
	 * @return a string containing the persons information
	 * @throws SQLException
	 */
	public static String displayPerson(String tableName, String columnName, String id, Statement statement) throws SQLException {
		ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName + " WHERE "
				+ columnName + "='" + id + "'");
		String output = "";
		while (rs.next()) {
			output = "\nID: " + rs.getString(columnName);
			output += "\nName: " + rs.getString("name");
			output += "\nSurname: " + rs.getString("surname");
			output += "\nContact Number: " + rs.getString("contact_num");
			output += "\nEmail Address: " + rs.getString("email");
			output += "\nAddress: " + rs.getString("address");
			return output;
		}
		return output;
	}
}
