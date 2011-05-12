Simple JSSE based app interacting with Fusion Tables through the Fusion Tables API.
Uses ClientLogin for authentication.
Change com.ecs.fusiontables.sample.Constants by providing a valid Google username/password
before running the sample.

Sample does the following

 * Show the Fusion Tables associated with the user account.
 * Create a new Fusion Table
 * Show the Fusion Tables associated with the user account. (new table is added)
 * Performs a select on the new table (empty)
 * Insert a record in the new table.
 * Performs a select on the new table (new record is shown).
 * Drops the table.

The Sample app outputs the following :

 +++ Begin Show Tables
No tables found.

 +++ Create Table
Table with ID = 833177 created

 +++ Begin Show Tables
Found table : TEST_TABLE(833177)

 +++ Select from Tables
No records found in table 833177

 +++ Insert into Tables
Record inserted with rowID : 1

 +++ Select from Tables
Found record : the name

 +++ Drop Tables

