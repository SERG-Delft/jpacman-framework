package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.MoveStrategy;

/**
 * A non-player unit.
 * 
 * @author Jeroen Roosen 
 */
public abstract class NPC extends Unit {

	/**
	 * The time that should be taken between moves.
	 * 
	 * @return The suggested delay between moves in milliseconds.
	 */
	public abstract long getInterval();

	/**
	 * Calculates the next move for this unit and returns the direction to move
	 * in.
	 * 
	 * @return The direction to move in, or <code>null</code> if no move could
	 *         be devised.
	 */
	public abstract Direction nextMove();

	/**
	 * Change the move strategy.
	 *
	 * @return The new move strategy between Dispersion and Pursuit to follow.
     */
	public abstract MoveStrategy changeMove();

	/**
	 * Test if it is in its corner.
	 *
	 * @return True if it is in its corner.
     */
	public abstract boolean isInMyCorner();

	/**
	 * Test if it is in pursuit move.
	 *
	 * @return True if it is in pursuit move.
     */
	public abstract boolean inPursuitMove();
}
