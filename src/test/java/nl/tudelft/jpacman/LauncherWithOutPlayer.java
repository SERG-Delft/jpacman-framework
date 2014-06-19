package nl.tudelft.jpacman;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.MapParser;

/**
 * Helper class for MapDesignWithoutPlayerTest.
 * @author Ike Rijsdijk, Sven Boor
 *
 */
public class LauncherWithOutPlayer extends Launcher {
	
	/**
	 * Creates a mapdesign without player.  
	 */
	@Override
	public Level makeLevel() {
		MapParser parser = getMapParser();
		List<String> mapDesign = new ArrayList<String>();
		mapDesign.add(".. .. #");
		mapDesign.add(".. ..  ");
		
		return parser.parseMap(mapDesign);
		
	}
	
}
