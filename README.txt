Simple JSSE based app interacting with Fusion Tables through the Fusion Tables API.
Uses ClientLogin for authentication.

Sample does the following

Show the Fusion Tables associated with the user account.
Create a new Fusion Table
Show the Fusion Tables associated with the user account. (new table is added)
Performs a select on the new table (empty)
Insert a record in the new table.
Performs a select on the new table (new record is shown).
Drops the table.


Sample output :

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

