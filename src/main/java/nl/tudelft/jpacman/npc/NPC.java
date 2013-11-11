package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;

/**
 * A non-player unit.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public abstract class NPC implements Unit {

	/**
	 * The strategy to figure out where to go next.
	 */
	private final Strategy script;

	/**
	 * Creates a new NPC that will move at fixed intervals.
	 * 
	 * @param strategy
	 *            The
	 */
	public NPC(Strategy strategy) {
		this.script = strategy;
	}

	/**
	 * The time that should be taken between moves.
	 * 
	 * @return The suggested delay between moves in milliseconds.
	 */
	public long getInterval() {
		return script.getInterval();
	}

	/**
	 * Calculates the next move for this unit and returns the direction to move
	 * in.
	 * 
	 * @return The direction to move in, or <code>null</code> if no move could
	 *         be devised.
	 */
	public Direction nextMove() {
		return script.nextMove(this);
	}

}
