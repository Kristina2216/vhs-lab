# VHS Rental Application
## Blast From The Past

This project simulates a simple VHS rental service.

## Set Up

- Run the create-vhs-db to create the local database tables
- Edit database connection in the application.properties file
- Run the application and go to localhost : http://localhost:8080/

## Log In

There are two types of users: Customers and Admins. To log in, use one of the accounts stored in the created database:
- User 1 (authorization role: CUSTOMER):
    - username: john 
    - password: john123
- User 2 (authorization role: CUSTOMER):
     - username: mary 
     - password: mary123
- User 3 (authorization role: ADMIN):
     - username: susan
    - password: susan123

Customers can create a rental, cancel/return a rental and view their own rentals.
Admins can view all vhs, users and rentals, view selected user rentals, add or delete a vhs.
##  Rental settings
By default, each rental is created on a one day period (the due date is set as the rental date + 1). The rental fee and the daily delay fee can be edited in the application.properties file.

## Create a Rental
To create a rental, you must be logged in as a customer. Select RENT option from home screen, then select the desired movie by clicking the Rent button next to it. Select the desired start date and click Rent to comfirm. The selected rental date cannot be in the past. If the selected vhs is available for the selected period (rental date and the next one), a new rental will be created and displayed.

## Return/Cancel/Edit a Rental
To return/cancel a rental, you must be logged in as a customer. Select MY RENTALS option from home screen.
Customer can return a rental that is currently active (rental date is in the past or today) by clicking the Return button next to it.
Customer can cancel/edit a rental if it's not yet active (rental date is in the future) by clicking the cancel/edit reservation button next to it. If the users attempts to edit the reservation, but the newly selected dates aren't available, the reservation will not be changed.

## View all users/vhs/rentals
To view all users/vhs/rentals you must be logged in as admin. Select ALL USERS/ALL VHS/ALL RENTALS option from the home screen.
To view only particular user's rentals, first select ALL USERS option, then click on the details button next to the desired user's username.

## Add a vhs
To add a vhs, you must be looged in as admin. Select ALL VHS option from the home screen, then click on the "Add a VHS" button. Fill out the title and genre fields. The title field must be unique. Both fields are required (non null). Once you've entered the necessary fields press Save to confirm creation. 

## Edit/delete a vhs
To edit/delete a vhs, you must be looged in as admin. Select ALL VHS option from the home screen.
Admin can edit a vhs by selecting the edit option next to the desired vhs. The title field must be unique. Both fields are required (non null). Once the desired properties have been edited, confirm edit by clicking the save option.
Admin can delete a vhs by selecting the delete option next to the desired vhs. All of the rentals containing the selected vhs will be deleted too.



