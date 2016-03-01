package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.util.ArrayList;

/**
 * Factory that creates Players.
 * 
 * @author Jeroen Roosen 
 */
public class PlayerFactory {

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

	public ArrayList<HunterGhostPlayer> createGhostPlayers(int numberOfPlayers) {
		ArrayList<HunterGhostPlayer> players = new ArrayList<>();
		switch (numberOfPlayers){
            case 4:
                players.add(new HunterGhostPlayer(sprites, GhostColor.RED));
            case 3:
                players.add(new HunterGhostPlayer(sprites, GhostColor.PINK));
			case 2:
				players.add(new HunterGhostPlayer(sprites, GhostColor.CYAN));
                players.add(new HunterGhostPlayer(sprites, GhostColor.ORANGE));
                break;
            default:
                throw new RuntimeException("Wrong number of players (must be 2,3 or 4 got "+numberOfPlayers+")");
		}
		return players;
	}
}
