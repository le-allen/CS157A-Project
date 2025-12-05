# CS157A-Project: Pet Adoption Management System
A simple terminal based pet adoption managment system that allows the user to manage multiple shelters, foster homes, and pets that are in the system.

> *Created by [Sarah Khadder](https://github.com/skhadder), [Chance Kissel](https://github.com/Kissel13), [Allen Le](https://github.com/le-allen), [Grace Li](https://github.com/Kagehina1218)*

## Tables
Use as a reference for modifying data.
|Pet                    |Type       |
|-                      |-          |
|PetID                  |int        |
|ShelterID              |int        |
|Name                   |String     |
|Species                |String     |
|Age                    |int        |
|Status                 |ENUM('Available', 'Adopted', 'Fostered', 'Unavailable')|

|MedicalHistory|Type|
|-|-|
|PetID| int|
|HealthStatus| ENUM('Healthy', 'Sick', 'Injured', 'Chronic Condition')|
|VaccinationStatus| boolean|

|Adoption|Type|
|-|-|
|PetID|int|
|AdopterID|int|
|AdoptionDate|Date (yyyy-mm-dd)|

|Adopter|Type|
|-|-|
|AdopterID|int|
|Name|String|
|Number|String (no spaces)|

|Shelter| Type|
|-|-|
|ShelterID|int|
|Address|String|

|FosterAssignment|Type|
|-|-|
|FosterID|int|
|PetID|int|
|StartDate|Date (yyyy-mm-dd)|
|EndDate|Date (yyyy-mm-dd)|

|FosterHome|Type|
|-|-|
|FosterID|int|
|Address|String|
|Number|String (no spaces)|

## Usage
Download MySQL Connector/J 9.5.0 Platform Independent and put the .jar file into a folder named `lib`.  
The file should be named `mysql-connector.jar`.  

Compile:
```
javac main.java
```  

Run the program:
```
java -cp .:lib/mysql-connector.jar main
```

### View Data
Choose a number from the list or type in the name of the table to be viewed.
### Insert
In one line, write the table name followed by the arguments exactly matching the schema of the table that is being inserted into.  
See [Tables](#Tables)
### Update
Select a table
Enter the ID of the row to be updated  
Update the value.
### Delete
Select a table  
Enter the ID of the row to be deleted.