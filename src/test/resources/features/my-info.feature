Feature: Information about me 

@db 
Scenario: my self 
	Given user logs in using "efewtrell8c@craigslist.org" "jamesmay" 
	When the user is on the my self page 
	Then user info should match the db records using "efewtrell8c@craigslist.org" 
	
@db 
Scenario: my team 
	Given user logs in using "efewtrell8c@craigslist.org" "jamesmay" 
	When the user is on the my team page 
	Then team info should match the db records using "efewtrell8c@craigslist.org" 
	
@db 
Scenario: batches 
	Given I retrieve the batches information 
	Then following batches should be displayed 
		| 7	| true |
		| 8	| false|
		
@db  @UI 
Scenario Outline: campust test 
	Given user logs in using "<email>" "<password>" 
	Then correct campus should be displayed for "<email>"
	Examples: 
		|   email                         | password  |
		|	efewtrell8c@craigslist.org	  |jamesmay   |
		|	teachervamikemarcus@gmail.com | mikemarcus|
		|	htwinbrowb4@blogspot.com	      |kanyabang  |
		
		
		
		
		
		
		
		
Scenario Outline: login as <user> type
	Given the user logs in as <user>
	And there are available spots for scheduling
	When the user hunts for a spot
	Then book button <expected> be displayed

	Examples:
	| user        | expected    | 
	| team member | should not  | 
	| team lead   | should      |
	| teacher     | should      |

		