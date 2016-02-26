package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.PlayerFactory;

/**
 * Factory that provides Game objects.
 * 
 * @author Jeroen Roosen 
 */
public class GameFactory {

	/**
	 * The factory providing the player objects.
	 */
	private final PlayerFactory playerFact;

	/**
	 * Creates a new game factory.
	 * 
	 * @param playerFactory
	 *            The factory providing the player objects.
	 */
	public GameFactory(PlayerFactory playerFactory) {
		this.playerFact = playerFactory;
	}

	/**
	 * Creates a game for a single level with one player.
	 *
	 * @param level
	 *            The level to create a game for.
	 * @return A new single player game.
	 */
	public Game createSinglePlayerGame(Level level) {
		return new SinglePlayerGame(playerFact.createPacMan(), level);
	}

	/**
	 * Creates a game for a single level with up to four players.
	 *
	 * @param level The level to create a game for.
	 * @param numberOfPlayers the number of players that are in the game.
	 * @return A new multi player game.
	 */
	public Game createMultiGhostPlayerGame(Level level, int numberOfPlayers) {
		return new MultiGhostPlayerGame(playerFact.createGhostPlayers(numberOfPlayers), level);
	}

	/**
	 * Returns the player factory associated with this game factory.
	 * @return the player factory associated with this game factory.
	 */
	protected PlayerFactory getPlayerFactory() {
		return playerFact;
	}
}
