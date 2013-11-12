package nl.tudelft.jpacman.npc;

import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An antagonist in the game of Pac-Man, a ghost.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public abstract class Ghost extends NPC {

	/**
	 * The sprite map, one sprite for each direction.
	 */
	private Map<Direction, Sprite> sprites;
	
	/**
	 * Creates a new ghost.
	 * @param spriteMap The sprites for every direction.
	 */
	protected Ghost(Map<Direction, Sprite> spriteMap) {
		this.sprites = spriteMap;
	}
	
	@Override
	public Sprite getSprite() {
		return sprites.get(getDirection());
	}
}
