package nl.tudelft.jpacman.cucumber;

import static org.junit.Assert.assertTrue;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;

/**
 * Step definitions for the Cucumber tests.
 * 
 * The steps also support setting up a {@link Game} object
 * which other tests can use for further testing the game.
 *
 * @author Jan-Willem Gmelig Meyling, Arie van Deursen
 */
public class StateNavigationSteps {
	
	private static Game theGame;
	
	private Launcher launcher;
	
	/**
	 * The Game created by the tests.
	 * 
	 * @return Game created when starting up the game. Null if game has not been launched.
	 */
	public static Game getGame() {
		return theGame;
	}

	private static void setGame(Game game) {
		theGame = game;
	}

	/**
	 * Launch the game. This makes the game available via
	 * the {@link #getGame()} method.
	 */
	@Given("^the user has launched the JPacman GUI$")
	public void theUserHasLaunchedTheJPacmanGUI() {
		launcher = new Launcher();
		launcher.launch();

		setGame(launcher.getGame());
	}

	/**
	 * Start playing the game.
	 */
	@When("^the user presses the \"Start\" button$")
	public void theUserPressesStart() {
		getGame().start();
	}	

	/**
	 * Verify that the play is actually running.
	 */
	@Then("^the game is running$")
	public void theGameShouldStart() {
		assertTrue(getGame().isInProgress());
	}

	/**
	 * Close the UI after all tests are finished.
	 */
	@After
	public void tearDownUI() {
		launcher.dispose();
	}
}
