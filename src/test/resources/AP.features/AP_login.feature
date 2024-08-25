Feature: Accounts Production

Background: Land into AP module home page
Given openApplication
And enter username and password click login
And redirect to Accounts production home page 


Scenario: create client
When enter required data to create client
And close