# __JavaFX Appointment Scheduler__

*Author:* Zachary Durso  
*Contact Information:* zdurso@wgu.edu  
*Application Version:* 1.0.0  
*Date:* 6-17-2022

## __Purpose__

The purpose of this application is to fulfill business needs for a consulting company.
This company conducts business in multiple languages and multiple time zones.
So this was of major concern during the planning and development phases, so to sompensate these needs, measures have been taken to ensure anything involving time is translated to UTC within the MySQL database, and for the login activity tracker. All areas where appointments are displayed, have been translated to the user's default timezone as dictated by their machine.

## __IDE and Driver Information__

*__IDE:__* IntelliJ Community 2021.1.3  
*__JDK:__* Java SE 17.0.1  
*__JFX:__* javafx-sdk-11.0.2  
*__MySQL Connector Driver:__* mysql-connector-java-8.0.25

## __How To Run__

Open project in IntelliJ, update path variables for JavaFX, JDK, and JDBC.  
Then press Shift+F10.  
Alternatively, you could click run, and a dropdown menu should appear with Run'C195_Appointments' as the first option.  
Click that and it should open a new window.

## __Reports__

This program generates several reports. To see these reports, there is a button on the main page, just below the label for the appointments tabll. This button says "View Reports". If clicked, it will take the user to a page with one table that displays all available data about each appointment. These reports also include a line of text near the bottom of the page that says the total number of appointments that fit within the selected filter parameters. These reports can filter appointments by the current month, the next seven days (by week), by customer, by any month, by type, and by contact. There is a dropdown menu for each of these categories, each with a list of available options. Once an option is selected from the dropdown menu, the appointments table will repopulate with only appointments that fit within the selected parameter. There are also two radio buttons that may be selected to view any appointments within the current month, or to view any appointments within the next 7 days.  
__NOTE:__ From this page, a user may also edit or delete appointments.