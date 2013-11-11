package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;

/**
 * A strategy that determines the next move of a non-player unit.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Strategy {

	/**
	 * Calculates the direction of the next move for the unit.
	 * 
	 * @param unit
	 *            The unit to calculate the next move for.
	 * @return The direction in which to move next, or <code>null</code> if no move could be calculated.
	 */
	Direction nextMove(NPC unit);

	/**
	 * Returns the delay between moves.
	 * 
	 * @return The delay between moves in milliseconds.
	 */
	long getInterval();

}
