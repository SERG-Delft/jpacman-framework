package nl.tudelft.jpacman;

import static org.junit.Assert.*;

import org.junit.Test;

public class MapDesignWithoutPlayerTest {

	
	@Test(expected=PacmanConfigurationException.class)
	public void mapDesignWithoutPlayerTest() {
		Launcher launcher = new LauncherWithOutPlayer();
		launcher.launch();
	}

}
