# PoisePMS

This is a project management application that was developed for a strutural engineering company called Poised. The program is used to track projects that they work on. The program allows the user to add details of projects, customers and service providers. All of the project related data gets stored on a SQL database called PoisePMS. Once a project has been loaded on to the system, the user is able to update the deadline, the amount paid by the customer, any of the role players details or finalise the project.

Steps on how to use the following features are covered below:

1. Add a  new project
2. Search/View/Update/Finalize a project
3. Display a list of incomplete projects
4. Display a list of overdue projects


## 1. Add a Project

When a new project is loaded on to the system, the program will first request the customers details. The following details are required:

* Name
* Surname
* Telephone Number
* Email Address
* Physical Address

The program then requests the following information for the project:

* Project Number
* Project Name
* Project Type (e.g. House, Apartment Block, Store, etc)
* Project Address
* ERF Number
* Project Cost
* Amount Paid
* Deadline

NB. If no project name is provided, the program will create a project name by joining the project type and customers surname.

The next step is to input the service providers details. For every project, there is an architect, contractor, structural engineer and project manager working on the project. The program will first ask for the service providers details in the order of architect, contractor, structural engineer and project manager. The same details entered for the customer will be requested for the service providers.

Once all details are captured, the project will be added to the database.

## 2. Search/View/Update/Finalize a project

The program will display a project search/selection menu. The user can choose whether to search for a project by number or name or by selecting a project from a list of all saved projects. Once a project has been selected, the project update menu will be displayed. Below each option of the project update menu is explained.

### 1. Change the due date of the project

Select option '1' from the project update menu to update the due date. Enter the new deadline in the format YYYY-MM-DD and press "ENTER". The project details will be displayed to show that the deadline has been updated.

### 2. Update the Amount Paid

Select option '2' from the project update menu to update the amount paid by the customer. Enter the amount that has been paid by the customer to date and press "ENTER". The project details will be displayed to show that the amount paid has been updated.

### 3. Update a Role Players Contact Details

Select option '3' from the project update menu to update a role players contact details. The program will then ask the user to select which role players details need to be updated. Once a role player has been selected, the program will ask which details the user wants to update (i.e. Contact Number or Email Address). Select the type of contact details to be updated, enter it and press "ENTER". The program will display the role players details showing that the contact details have been updated.

### 4. Finalise a project

Select option '4' from the project update menu to finalise a project. When a project is finalised, the status of the project will change from "incomplete" to "finalised on YYYY-MM-DD", where YYYY-MM-DD is the date on which the project was finalized. . If the customer has not fully paid for the project, an invoice will be generated containing the customers details and amount outstanding. The details of the project will also be saved to a text file "Completed Project - Project Name".

### 5. Return to main menu

Select option '5' from the project update menu to return to the main menu.

## 3. Display a list of incomplete projects

The program searches the database for projects where the status is 'Incomplete'. The project number, name and deadline of each of these projects are displayed.

## 4. Display a list of overdue projects

The program searches the database for projects where the deadline has passed. The project number, name, deadline and status of each of these projects are displayed.
