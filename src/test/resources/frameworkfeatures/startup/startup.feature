@S1 @framework
Feature: Start to play
	As a player
	I want to start the game
	so that I can actually play

	@S1.1
	Scenario: Press start button
		Given the user has launched the JPacman GUI
		When  the user presses the "Start" button
		Then  the game is running
