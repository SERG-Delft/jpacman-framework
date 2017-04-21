package nl.tudelft.jpacman.e2e.framework;

import static org.assertj.core.api.Assertions.assertThat;

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
@SuppressWarnings("initialization.fields.uninitialized")
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
		launcher.getGame().start();
	}	

	/**
	 * Verify that the play is actually running.
	 */
	@Then("^the game is running$")
	public void theGameShouldStart() {
		assertThat(launcher.getGame().isInProgress()).isTrue();
	}

	/**
	 * Close the UI after all tests are finished.
	 */
	@After("@framework")
	public void tearDownUI() {
		launcher.dispose();
	}
}
