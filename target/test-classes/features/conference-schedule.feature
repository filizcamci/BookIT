Feature: Conference scheduling

@temp
Scenario: Schedule a conference as a team leader
Given the user logs in using "apobred@hotmailmail.com" and "bredpenhalurick"
When the user is on the map page
Then user schedules a conference


Scenario: Try to schedule a conference as a team member
Given the user logs in using "ewrist7i@livejournal.com" and "marianndewi"
When the user is on the map page 
Then user tries to schedule a conference


Scenario: Schedule a conference for prevoius time period
Given the user logs in using "daldie7l@seattletimes.com" and "ruthannjohnes"
When the user is on the map page
Then user schedules a conference for prevoius time period 