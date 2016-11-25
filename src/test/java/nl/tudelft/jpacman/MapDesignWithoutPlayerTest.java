package nl.tudelft.jpacman;


import org.junit.Test;

/**
 * MapDesignWithOutPlayerTestcase.
 * @author Ike Rijsdijk, Sven Boor
 *
 */
public class MapDesignWithoutPlayerTest {

	/**
	 * Test if PacmanConfigurationException is thrown as user tries
	 * to launch a map without player.
	 */
	@Test(expected = PacmanConfigurationException.class)
	public void mapDesignWithoutPlayerTest() {
		Launcher launcher = new LauncherWithOutPlayer();
		launcher.launch();
	}
	
	

}
