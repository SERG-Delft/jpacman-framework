package nl.tudelft.jpacman.level;

import java.awt.Color;

import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Factory that creates Players.
 * 
 * @author Jeroen Roosen 
 */
public class PlayerFactory {
	
	private GhostColor color;

	/**
	 * The sprite store containing the Pac-Man sprites.
	 */
	private final PacManSprites sprites;

	/**
	 * Creates a new player factory.
	 * 
	 * @param spriteStore
	 *            The sprite store containing the Pac-Man sprites.
	 */
	public PlayerFactory(PacManSprites spriteStore) {
		this.sprites = spriteStore;
	}

	/**
	 * Creates a new player with the classic Pac-Man sprites.
	 * 
	 * @return A new player.
	 */
	public Player createPacMan() {
		return new Player(sprites.getPacmanSprites(),
				sprites.getPacManDeathAnimation());
	}
	public Player createGhost(GhostColor c)
	{
		return new Player(sprites.getGhostSprite(c),sprites.getPacManDeathAnimation());
	}
}
