Feature: User information 

@ws 
Scenario: Verify information about the logged in user 
	Given I am logged reservation api using "teachervamikemarcus@gmail.com" and "mikemarcus" 
	When I get the current user information using the me endpoint 
	Then the information about current user should be returned 
	
	
@ws 
Scenario: Verify user by id 
	Given  I am logged reservation api as teacher 
	When I get the user information by id 40 using the student endpoint 
	Then the correct user information should be returned by the student endpoint 
		|id		  |40				|
		|firstName|Angie				|
		|lastName |Coatham			|
		|role	  |student-team-member|
		
		
@ws @db
Scenario: Verify information about the logged in user with the database 
	Given I am logged reservation api using "teachervamikemarcus@gmail.com" and "mikemarcus" 
	When I get the current user information using the me endpoint 
	Then the information about current user should match the user table 
	
@ws @db 
Scenario Outline: Verify students by id 
	Given  I am logged reservation api as teacher 
	When I get the user information by id <id> using the student endpoint 
	Then the information about current user should match the user table 
	Examples: 
		|id|
		|39|
		|40|
		|41|
		|42|
		|44|
		|45|
		|46|
		|47|
		|49|
		|50|
		|51|
		|52|
		|54| 
		
		
		
@ws @db 
Scenario: Verify all students by id 
	Given  I am logged reservation api as teacher 
	When I get all students from users table 
	Then student service should return same students 
			
			