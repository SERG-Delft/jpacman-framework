package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Factory that creates ghosts.
 * 
 * @author Jeroen Roosen 
 */
public class GhostFactory {

	/**
	 * The sprite store containing the ghost sprites.
	 */
	private final PacManSprites sprites;

	/**
	 * Creates a new ghost factory.
	 * 
	 * @param spriteStore The sprite provider.
	 */
	
	private Direction[] cheminC= {Direction.EAST, Direction.EAST,Direction.EAST,
			Direction.EAST,Direction.EAST,Direction.EAST,Direction.EAST,Direction.EAST,
			Direction.EAST,Direction.NORTH,Direction.NORTH,Direction.WEST,Direction.WEST,
			Direction.WEST,Direction.NORTH,Direction.NORTH,Direction.WEST,Direction.WEST,
			Direction.SOUTH,Direction.SOUTH,Direction.WEST,Direction.WEST,Direction.WEST,
			Direction.WEST,Direction.SOUTH,Direction.SOUTH};
	
	public Direction[] cheminB= {Direction.SOUTH, Direction.SOUTH,Direction.WEST,
			Direction.WEST,Direction.WEST,Direction.WEST,Direction.NORTH,
			Direction.NORTH,Direction.EAST,Direction.EAST,Direction.EAST,Direction.EAST};
	
	private Direction[] cheminI= {Direction.WEST, Direction.WEST,Direction.WEST,
			Direction.WEST,Direction.WEST,Direction.WEST,Direction.WEST,Direction.WEST,
			Direction.WEST,Direction.NORTH,Direction.NORTH,Direction.EAST,Direction.EAST,
			Direction.EAST,Direction.NORTH,Direction.NORTH,Direction.EAST,Direction.EAST,
			Direction.SOUTH,Direction.SOUTH,Direction.EAST,Direction.EAST,Direction.EAST,
			Direction.EAST,Direction.SOUTH,Direction.SOUTH}; 
	
	private Direction[] cheminP= {Direction.SOUTH, Direction.SOUTH, Direction.EAST,Direction.EAST,
			Direction.EAST,Direction.EAST,Direction.NORTH,Direction.NORTH,
			Direction.WEST,Direction.WEST,Direction.WEST,Direction.WEST};
	
	public GhostFactory(PacManSprites spriteStore) {
		this.sprites = spriteStore;
	}

	/**
	 * Creates a new Blinky / Shadow, the red Ghost.
	 * 
	 * @see Blinky
	 * @return A new Blinky.
	 */
	public Ghost createBlinky() {
		return new Blinky(sprites.getGhostSprite(GhostColor.RED), false,"modeDispersion", cheminB);
	}

	/**
	 * Creates a new Pinky / Speedy, the pink Ghost.
	 * 
	 * @see Pinky
	 * @return A new Pinky.
	 */
	public Ghost createPinky() {
		return new Pinky(sprites.getGhostSprite(GhostColor.PINK), false,"modeDispersion", cheminP);
	}

	/**
	 * Creates a new Inky / Bashful, the cyan Ghost.
	 * 
	 * @see Inky
	 * @return A new Inky.
	 */
	public Ghost createInky() {
		return new Inky(sprites.getGhostSprite(GhostColor.CYAN), false,"modeDispersion", cheminI);
	}

	/**
	 * Creates a new Clyde / Pokey, the orange Ghost.
	 * 
	 * @see Clyde
	 * @return A new Clyde.
	 */
	public Ghost createClyde() {
		return new Clyde(sprites.getGhostSprite(GhostColor.ORANGE), false,"modeDispersion",cheminC);
	}
}
