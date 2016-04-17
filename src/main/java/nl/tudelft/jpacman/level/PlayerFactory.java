package nl.tudelft.jpacman.level;

import java.awt.Color;

import CraeyeMathieu.Joueur;
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
				sprites.getPacManDeathAnimation(),"Pac-Man");
	}
	/**
	 * Creates a new player with the ghost sprites.
	 * @param nom du ghost
	 * @return A new player.
	 */
	public Player createGhost(Joueur name)
	{
		if(name.getName()=="pinky")
		return new Player(sprites.getGhostSprite(GhostColor.PINK),sprites.getPacManDeathAnimation(),"pinky");
		if(name.getName()=="clyde")
			return new Player(sprites.getGhostSprite(GhostColor.ORANGE),sprites.getPacManDeathAnimation(),"clyde");
		if(name.getName()=="inky")
			return new Player(sprites.getGhostSprite(GhostColor.CYAN),sprites.getPacManDeathAnimation(),"inky");
		if(name.getName()=="blinky")
			return new Player(sprites.getGhostSprite(GhostColor.RED),sprites.getPacManDeathAnimation(),"blinky");
		return new Player(sprites.getGhostSprite(GhostColor.RED),sprites.getPacManDeathAnimation(),"blinky");
	}
}
