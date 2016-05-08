package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;


/**
 * A non-player unit.
 * 
 * @author Jeroen Roosen 
 */
public abstract class NPC extends Unit {

	protected NPC() {
	}

	/**
	 * The time that should be taken between moves.
	 * 
	 * @return The suggested delay between moves in milliseconds.
	 */
	public abstract long getInterval();
	
	protected String strategy;
	
	public NPC(String strategy){
		this.strategy=strategy;
	}

	/**
	 * Calculates the next move for this unit and returns the direction to move
	 * in.
	 * 
	 * @return The direction to move in, or <code>null</code> if no move could
	 *         be devised.
	 */
	public abstract Direction nextMove();

	public abstract String getStrategy();
	
	
	public abstract void setStrategy(String strategy);
}
