package nl.tudelft.jpacman.game;

import java.util.List;

/**
 * A game of Pac-Man.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Game {

	/**
	 * Starts or resumes the game.
	 */
	void start();

	/**
	 * Pauses the game.
	 */
	void stop();

	/**
	 * @return <code>true</code> iff the game is started and in progress.
	 */
	boolean isInProgress();

	/**
	 * @return An immutable list of the participants of this game.
	 */
	List<Player> getPlayers();

	/**
	 * @return The level currently being played.
	 */
	Level getLevel();
}