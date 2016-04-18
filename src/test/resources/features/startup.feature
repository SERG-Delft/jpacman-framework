Feature: 1. Startup
	As a player
	I want to start the game
	so that I can actually play

	Scenario: S1.1 Startup
		Given the user has launched the JPacman GUI
		When  the user presses the "Start" button
		Then  the game is running
