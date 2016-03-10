package nl.tudelft.jpacman.npc.ghost;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A list of supported ghost colors.
 * 
 * @author Jeroen Roosen 
 */
public enum GhostColor {

	/**
	 * Shadow, a.k.a. Blinky.
	 */
	RED,

	/**
	 * Bashful, a.k.a. Inky.
	 */
	CYAN,

	/**
	 * Speedy, a.k.a. Pinky.
	 */
	PINK,

	/**
	 * Pokey, a.k.a. Clyde.
	 */
	ORANGE;
	
	public static ArrayList<GhostColor> getOtherColors(ArrayList<GhostColor> colors){
		ArrayList<GhostColor> result = new ArrayList<>();
		//						   RED  ORANGE CYAN  PINK
		boolean[] colorsPresent = {false,false,false,false};
		for (int i = 0; i < colors.size(); i++) {
			switch (colors.get(i)){
				case RED:
					colorsPresent[0] = true;
					break;
				case ORANGE:
					colorsPresent[1] = true;
					break;
				case CYAN:
					colorsPresent[2] = true;
					break;
				case PINK:
					colorsPresent[3] = true;
					break;
			}
		}
		if (!colorsPresent[0])
			result.add(GhostColor.RED);
		if (!colorsPresent[1])
			result.add(GhostColor.ORANGE);
		if (!colorsPresent[2])
			result.add(GhostColor.CYAN);
		if (!colorsPresent[3])
			result.add(GhostColor.PINK);
		return result;
	}
}
